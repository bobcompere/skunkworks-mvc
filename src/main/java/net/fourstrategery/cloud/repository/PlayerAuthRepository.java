package net.fourstrategery.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.fourstrategery.cloud.entity.PlayerAuthEntity;

public interface PlayerAuthRepository extends JpaRepository<PlayerAuthEntity, Integer> {

}
