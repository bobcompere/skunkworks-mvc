package net.fourstrategery.cloud.gameplay;

import net.fourstrategery.cloud.entity.PlayerEntity;

public interface MoveService {

	public String moveUnit(PlayerEntity player, int unitId, String venueId);
}
