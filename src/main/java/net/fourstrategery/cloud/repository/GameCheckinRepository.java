package net.fourstrategery.cloud.repository;

import net.fourstrategery.cloud.entity.GameCheckinEntity;
import net.fourstrategery.cloud.entity.GameCheckinKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCheckinRepository extends JpaRepository<GameCheckinEntity,GameCheckinKey> {

}
