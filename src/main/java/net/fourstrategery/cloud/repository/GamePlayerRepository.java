package net.fourstrategery.cloud.repository;

import java.util.List;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.GamePlayerKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GamePlayerRepository extends JpaRepository<GamePlayerEntity, GamePlayerKey> {

	@Query("select gp from GamePlayerEntity gp where gp.game = ?1 order by gp.score desc,gp.createdOn")
	List<GamePlayerEntity> getPlayersForGame(GameEntity game);
}
