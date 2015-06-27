package net.fourstrategery.cloud.gameplay;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.meta.GameStatusModel;

public interface GameStatusModelService {

	public GameStatusModel getGameStatusModel(GameEntity game, PlayerEntity player);
}
