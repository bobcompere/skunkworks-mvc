package net.fourstrategery.cloud.repository;

import java.util.List;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.GameVenueEntity;
import net.fourstrategery.cloud.entity.GameVenueKey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameVenueRepository extends JpaRepository<GameVenueEntity, GameVenueKey> {

	@Query("select gv from GameVenueEntity gv where gv.game = ?1 order by gv.name,gv.state,gv.state")
	public List<GameVenueEntity> getVenuesForGame(GameEntity game);
}
