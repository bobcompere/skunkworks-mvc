package net.fourstrategery.cloud.gameplay;

import java.util.Date;

import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.UnitStatus;
import net.fourstrategery.cloud.entity.VenueEntity;
import net.fourstrategery.cloud.repository.UnitRepository;
import net.fourstrategery.cloud.repository.VenueRepository;
import net.fourstrategery.cloud.utility.VenueUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveServiceImpl implements MoveService {
	
	private static final int MPH = 50;
	private static final long MILLI_PER_HOUR = 60 * 60 * 1000;

	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	VenueRepository venueRepository;
	
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
		unit.setNextMoveTime(new Date(now.getTime() + (long)((float)(miles/MPH) * (float)MILLI_PER_HOUR)));
		
		unitRepository.save(unit);
		
		return "Your unit will arrive at " + unit.getNextMoveTime();
	}

}
