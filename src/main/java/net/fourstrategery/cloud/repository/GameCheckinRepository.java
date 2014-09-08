package net.fourstrategery.cloud.repository;

import java.util.Date;
import java.util.List;

import net.fourstrategery.cloud.entity.GameCheckinEntity;
import net.fourstrategery.cloud.entity.GameCheckinKey;
import net.fourstrategery.cloud.entity.GameEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameCheckinRepository extends JpaRepository<GameCheckinEntity,GameCheckinKey> {

	@Query("Select gc from GameCheckinEntity gc where gc.createdOn > ?1 and gc.player = ?2 and gc.game = ?3")
	List<GameCheckinEntity> getCheckinsSinceDateForGameAndPlayer(Date fromDate, PlayerEntity player, GameEntity game);
}
