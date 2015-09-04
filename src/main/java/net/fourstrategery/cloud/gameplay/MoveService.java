package net.fourstrategery.cloud.gameplay;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.VenueEntity;

public interface MoveService {

	/**
	 * @param player player object that owns the unit
	 * @param unitId id of unit to move
	 * @param venueId Venue to move to
	 * @return message indicating results
	 */
	public String moveUnit(PlayerEntity player, int unitId, String venueId); 
	public void processCompletedMoves();
	
	
}
