package net.fourstrategery.cloud.gameplay;

import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.VenueEntity;

public interface ActivityService {

	public void simpleUnitVenueActivity(UnitEntity unit, VenueEntity venue, String action);
	public void unitMergeMessage(UnitEntity absorbedUnit, UnitEntity enlargedUnit, VenueEntity venue);
	public void reportOnBattle(UnitEntity attacker, UnitEntity defender, int origAttackerStrength, int origDefenderStrength, VenueEntity venue);
	public void newUnit(UnitEntity unit);
}
