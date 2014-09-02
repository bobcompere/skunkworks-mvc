package net.fourstrategery.cloud.repository;

import java.util.List;

import net.fourstrategery.cloud.entity.ActivityEntity;
import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Integer> {

	@Query("Select a from ActivityEntity a where a.game = ?1 and (a.specificAudience = ?2 or a.specificAudience is null) order by a.id desc")
	public List<ActivityEntity> getActivitiesForGameAndPlayer(GameEntity game, PlayerEntity playerEntity);
}
