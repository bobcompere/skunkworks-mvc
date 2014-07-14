package net.fourstrategery.cloud.repository;

import net.fourstrategery.cloud.entity.PlayerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
	
	@Query("Select p from PlayerEntity p where p.screenName = ?1")
	public PlayerEntity getPlayerByScreenName(String username);

}
