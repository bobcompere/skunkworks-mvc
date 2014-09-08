package net.fourstrategery.cloud.gameplay;

import net.fourstrategery.cloud.entity.ActivityEntity;
import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.VenueEntity;
import net.fourstrategery.cloud.repository.ActivityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityRepository activityRepository;

	@Override
	public void simpleUnitVenueActivity(UnitEntity unit, VenueEntity venue,
			String action) {
		
		ActivityEntity activity = new ActivityEntity();
		activity.setGame(unit.getGame());
		
		activity.setMessage(unit.getPlayer().getScreenName() + "'s unit " + unit.getName() + " " + action + " " + venue.getName() +
				" in " + venue.getCity() + " " + venue.getState()); 
		activityRepository.save(activity);
	}

	@Override
	public void unitMergeMessage(UnitEntity absorbedUnit,
			UnitEntity enlargedUnit, VenueEntity venue) {
		
		ActivityEntity activity = new ActivityEntity();
		activity.setGame(absorbedUnit.getGame());
		
		activity.setMessage(absorbedUnit.getPlayer().getScreenName() + " has merged " + absorbedUnit.getName() + " into " + 
				enlargedUnit.getName() + " @ " + venue.getName() +
				" in " + venue.getCity() + " " + venue.getState()); 
		activityRepository.save(activity);
		
		
		ActivityEntity activityStrengthMessage = new ActivityEntity();
		activityStrengthMessage.setGame(absorbedUnit.getGame());
		activityStrengthMessage.setSpecificAudience(absorbedUnit.getPlayer());
		activityStrengthMessage.setMessage(enlargedUnit.getName() + "'s new strength is " + enlargedUnit.getTroops());
		
		activityRepository.save(activityStrengthMessage);
	}

	@Override
	public void reportOnBattle(UnitEntity attacker, UnitEntity defender,
			int origAttackerStrength, int origDefenderStrength,
			VenueEntity venue) {
		//
		// public message
		//

		ActivityEntity activity = new ActivityEntity();
		activity.setGame(attacker.getGame());
		
		String result = "and was repulsed and destroyed by ";
		String attackerNote = null;
		String defenderNote = null;
		if (attacker.getTroops() > 0) {
			result = "and destroyed ";
			attackerNote = "Afteraction Intelligence report: The enemies " + origDefenderStrength + 
					" are annihilated and your looses were " + (origAttackerStrength - attacker.getTroops()) + " you now occupy " +
					venue.getName();
			defenderNote = "Afteraction Intelligence report: Your unit was destroyed, Your losses totaled : " + origAttackerStrength + 
					", and you inflicted losses on your enemy totalling " + (origAttackerStrength - attacker.getTroops());
		}
		else {
			defenderNote = "Afteraction Intelligence report: The enemies " + origAttackerStrength + 
					" troops are annihilated and your looses were " + (origDefenderStrength - defender.getTroops()) + " you now occupy " +
							venue.getName();
			attackerNote = "Afteraction Intelligence report: Your unit was destroyed, Your losses totaled : " + origDefenderStrength + 
					", and you inflicted losses on your enemy totalling " + (origAttackerStrength - attacker.getTroops());
		}
		
		activity.setMessage("EXTRA: Battle @ " + venue.getName() + ", " + venue.getCity() + " " + venue.getState() + ", " +
				attacker.getPlayer().getScreenName() + "'s unit " + attacker.getName() + " attacked " + result + " "
				+ defender.getPlayer().getScreenName() + "'s unit " + defender.getName());
		activityRepository.save(activity);
		
		//
		// note to attacker
		//
		ActivityEntity attackerActivity = new ActivityEntity();
		attackerActivity.setGame(attacker.getGame());
		attackerActivity.setSpecificAudience(attacker.getPlayer());
		attackerActivity.setMessage(attackerNote);
		
		activityRepository.save(attackerActivity);
		
		//
		// note to defender
		//
		ActivityEntity defenderActivity = new ActivityEntity();
		defenderActivity.setGame(defenderActivity.getGame());
		defenderActivity.setSpecificAudience(defender.getPlayer());
		defenderActivity.setMessage(defenderNote);
		
		activityRepository.save(defenderActivity);
		
	}

	@Override
	public void newUnit(UnitEntity unit) {
		ActivityEntity activity = new ActivityEntity();
		activity.setGame(unit.getGame());
		activity.setMessage("New unit for: " + unit.getPlayer().getScreenName() + " " + unit.getName() + " at " + 
		unit.getLocation().getName() + ", " + unit.getLocation().getCity() + " " + unit.getLocation().getState());
		activityRepository.save(activity);
		
		ActivityEntity activityPriv = new ActivityEntity();
		activityPriv.setGame(unit.getGame());
		activityPriv.setMessage(unit.getName() + " initial strength " + unit.getTroops() + " troops");
		activityPriv.setSpecificAudience(unit.getPlayer());
		activityRepository.save(activityPriv);
	}

	@Override
	public void simpleGameActivity(GameEntity game, String action) {
		ActivityEntity activity = new ActivityEntity();
		activity.setGame(game);
		activity.setMessage(action);
		activityRepository.save(activity);
	}
	
	

}
