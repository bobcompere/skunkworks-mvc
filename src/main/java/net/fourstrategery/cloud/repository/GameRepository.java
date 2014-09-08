package net.fourstrategery.cloud.repository;

import java.util.Date;
import java.util.List;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<GameEntity, Integer> {

	@Query("Select g from GameEntity g, GamePlayerEntity gp where gp.player = ?1 and gp.game = g and g.starts <= ?2 and g.ends >= ?2")
	public List<GameEntity> findActiveGamesForPlayer(PlayerEntity player, Date when);
	
	@Query("Select g from GameEntity g where g.starts <= ?1 and g.ends >= ?1")
	public List<GameEntity> findActiveGames(Date when);
}
