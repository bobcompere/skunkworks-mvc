package net.fourstrategery.cloud.repository;

import net.fourstrategery.cloud.entity.GamePlayerEntity;
import net.fourstrategery.cloud.entity.GamePlayerKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayerRepository extends JpaRepository<GamePlayerEntity, GamePlayerKey> {

}
