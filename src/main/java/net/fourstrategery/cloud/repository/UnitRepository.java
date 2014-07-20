package net.fourstrategery.cloud.repository;

import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.entity.UnitEntity;
import net.fourstrategery.cloud.entity.UnitStatus;
import net.fourstrategery.cloud.entity.VenueEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UnitRepository extends JpaRepository<UnitEntity, Integer> {

	@Query("Select u from UnitEntity u where u.game = ?1 and u.player = ?2 and u.location = ?3 and u.status = net.fourstrategery.cloud.entity.UnitStatus.GARRISONED)")
	//@Query("Select u from UnitEntity u where u.game = ?1 and u.player = ?2 and u.location = ?3")
	public UnitEntity getUnitAtVenue(GameEntity game,PlayerEntity player,VenueEntity venue);
	
	@Query("Select u from UnitEntity u where u.game = ?1 and u.name = ?2")
	public UnitEntity findUnitByNameAndGame(GameEntity game, String name);
}
