package net.fourstrategery.cloud.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.UnitStatus;
import net.fourstrategery.cloud.entity.VenueEntity;
import net.fourstrategery.cloud.repository.UnitRepository;
import net.fourstrategery.cloud.repository.VenueRepository;
import net.fourstrategery.cloud.utility.VenueUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MoveServiceImpl implements MoveService {
	
	private static final int MPH = 50;
	private static final long MILLI_PER_HOUR = 60 * 60 * 1000;
	private static final long  MOVE_DELAY = 1000 * 60; // 60 sec

	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	VenueRepository venueRepository;
	
	@Autowired
	ActivityService activityService;
	
	@Override
	public String moveUnit(PlayerEntity player, int unitId, String venueId) {
		
		UnitEntity unit = unitRepository.findOne(unitId);
		if (unit == null) {
			return "Invalid unit - not found";
		}
		
		if (unit.getPlayer().getId() != player.getId()) {
			return "Invalid unit - wrong player";
		}
		
		Date now = new Date();
		if (unit.getGame().getEnds().before(now)) {
			return "Game is over";
		}
		
		if (unit.getStatus() != UnitStatus.GARRISONED) {
			return "Invalid Status for Move";
		}
		
		VenueEntity currentLocation = unit.getLocation();
		if (currentLocation == null) {
			return "Error - current location";
		}
		
		VenueEntity moveToLocation = venueRepository.findOne(venueId);
		if (moveToLocation == null) {
			return "Error - move to location";
		}
		
		int miles = VenueUtility.distanceBetween(currentLocation,moveToLocation);
		
		unit.setStatus(UnitStatus.INTRANSIT);
		unit.setLocation(moveToLocation);
		unit.setNextMoveTime(new Date(now.getTime() + (long)((float)((float)miles/(float)MPH) * (float)MILLI_PER_HOUR)));
		
		unitRepository.save(unit);
		
		activityService.simpleUnitVenueActivity(unit,currentLocation, "has departed from");
		
		return "Your unit will arrive at " + unit.getNextMoveTime();
	}

	@Override
	@Transactional
	@Scheduled(fixedDelay = MOVE_DELAY)
	public void processCompletedMoves() {
		List<UnitEntity> unitsToMove = unitRepository.getUnitsThatHaveArrived(new Date());
		
		for (UnitEntity unit : unitsToMove) {
			completeMove(unit);
		}
	}
	
	
	private void completeMove(UnitEntity unit) {
		//
		// check that game is still going
		//
		Date now = new Date();
		if (unit.getGame().getEnds().before(now)) {
			//
			// change status and update
			//
			unit.setStatus(UnitStatus.GAME_ENDED_INTRANSIT);
			unitRepository.save(unit);
			return;
		}
		
		UnitEntity occupingUnit = unitRepository.getUnitOccupingVenue(unit.getGame(), unit.getLocation());
		//
		// if not occupied, then move in
		//
		if (occupingUnit == null) {
			unit.setStatus(UnitStatus.GARRISONED);
			unitRepository.save(unit);
			activityService.simpleUnitVenueActivity(unit, unit.getLocation(),"arrived and has occupied");
			return;
		}
		
		//
		// if occupied by a unit from current player the  merge the units.
		//
		if (occupingUnit.getPlayer().getId() == unit.getPlayer().getId()) {
			activityService.simpleUnitVenueActivity(unit, unit.getLocation(),"arrived");
			if (occupingUnit.getTroops() > unit.getTroops()) {
				occupingUnit.setTroops(occupingUnit.getTroops() + unit.getTroops());
				unit.setStatus(UnitStatus.ABSORBED);
				activityService.unitMergeMessage(unit,occupingUnit,occupingUnit.getLocation());
			}
			else {
				unit.setTroops(occupingUnit.getTroops() + unit.getTroops());
				occupingUnit.setStatus(UnitStatus.ABSORBED);
				unit.setStatus(UnitStatus.GARRISONED);
				activityService.unitMergeMessage(occupingUnit,unit,occupingUnit.getLocation());
			}
			unitRepository.save(unit);
			unitRepository.save(occupingUnit);
			return;
		}
		//
		// time for a battle.....
		//
		Random dice = new Random(new Date().getTime());
		
		int startingOccupingTroops = occupingUnit.getTroops();
		int startingAttackingTroops = unit.getTroops();
		
		int[] attackDice = null;
		int[] defenseDice = null;
		
		int diceRounds = 0;
		
		while (occupingUnit.getTroops() > 0 && unit.getTroops() > 0) {
			diceRounds++;
			if (occupingUnit.getTroops() > 1) {
				defenseDice = roll(2,dice);
			}
			else {
				defenseDice = roll(1,dice);
			}
			if (unit.getTroops() > 2) {
				attackDice = roll(3,dice);
			}
			else {
				attackDice = roll(unit.getTroops(),dice);
			}
			
			if (attackDice[0] > defenseDice[0]) {
				occupingUnit.setTroops(occupingUnit.getTroops() -1);
			}
			else {
				unit.setTroops(unit.getTroops() -1);
			}
			
			if (defenseDice.length == 2) {
				if (attackDice[1] > defenseDice[1]) {
					occupingUnit.setTroops(occupingUnit.getTroops() -1);
				}
				else {
					unit.setTroops(unit.getTroops() -1);
				}
			}
			System.out.println(unit.getTroops() + " " + occupingUnit.getTroops());
		}
		
		if (occupingUnit.getTroops() > 0) {
			//
			// defense won!
			//
			unit.setStatus(UnitStatus.DESTROYED);
		} else {
			//
			// Attacker won!
			//
			occupingUnit.setStatus(UnitStatus.DESTROYED);
			unit.setStatus(UnitStatus.GARRISONED);
		}
		
		unitRepository.save(occupingUnit);
		unitRepository.save(unit);
		
		activityService.reportOnBattle(unit,occupingUnit,startingAttackingTroops, startingOccupingTroops, unit.getLocation());
	}


	private static int[] roll(int di, Random diceRnd) {
		int[] retval = new int[di];
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i1 = 0;i1<retval.length;i1++) {
			list.add(new Integer(diceRnd.nextInt(6) + 1)); 
		}
		
		Collections.sort(list);
		Collections.reverse(list);
		for (int i1=0;i1 <retval.length;i1++) {
			retval[i1] = list.get(i1);
			
			System.out.print(retval[i1] + " ");
		}
		System.out.println();
		
		return retval;
	}
	
	
}
