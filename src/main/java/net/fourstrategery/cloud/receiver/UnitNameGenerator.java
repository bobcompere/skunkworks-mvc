package net.fourstrategery.cloud.receiver;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.VenueEntity;

public interface UnitNameGenerator {
	public String makeBSName(VenueEntity venue,PlayerEntity player, GameEntity game);
}
