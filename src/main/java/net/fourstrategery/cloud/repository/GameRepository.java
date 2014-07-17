package net.fourstrategery.cloud.repository;

import net.fourstrategery.cloud.entity.GameEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Integer> {

}
