package net.fourstrategery.cloud.gameplay;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.VenueEntity;

public interface MoveService {

	public String moveUnit(PlayerEntity player, int unitId, String venueId);
	
	public void processCompletedMoves();
	
	
}
