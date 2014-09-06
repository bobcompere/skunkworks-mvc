package net.fourstrategery.cloud.repository;

import java.util.List;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.VenueEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UnitRepository extends JpaRepository<UnitEntity, Integer> {

	@Query("Select u from UnitEntity u where u.game = ?1 and u.player = ?2 and u.location = ?3 and u.status = net.fourstrategery.cloud.entity.UnitStatus.GARRISONED)")
	//@Query("Select u from UnitEntity u where u.game = ?1 and u.player = ?2 and u.location = ?3")
	public UnitEntity getUnitAtVenue(GameEntity game,PlayerEntity player,VenueEntity venue);
	
	@Query("Select u from UnitEntity u where u.game = ?1 and u.name = ?2")
	public UnitEntity findUnitByNameAndGame(GameEntity game, String name);
	
	@Query("Select u from UnitEntity u where u.status in (net.fourstrategery.cloud.entity.UnitStatus.GARRISONED, "
			+ "net.fourstrategery.cloud.entity.UnitStatus.INTRANSIT) and u.game.id = ?2 and u.player.id = ?1 order by u.createdOn")
	public List<UnitEntity> getCurrentUnitsForPlayerAndGame(int player_id,int game_id);
	
	@Query("Select u from UnitEntity u where u.status in (net.fourstrategery.cloud.entity.UnitStatus.GARRISONED, "
			+ "net.fourstrategery.cloud.entity.UnitStatus.INTRANSIT) and u.game.id = ?1")
	public List<UnitEntity> getCurrentUnitsForGame(int game_id);
	
	@Query("Select u from UnitEntity u where u.status = net.fourstrategery.cloud.entity.UnitStatus.INTRANSIT and u.nextMoveTime < CURRENT_DATE order by u.nextMoveTime")
	public List<UnitEntity> getUnitsThatHaveArrived();
	
	@Query("Select u from UnitEntity u where u.status =  net.fourstrategery.cloud.entity.UnitStatus.GARRISONED and u.game = ?1 and u.location=?2")
	public UnitEntity getUnitOccupingVenue(GameEntity game, VenueEntity venue);
}
