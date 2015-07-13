package net.fourstrategery.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.fourstrategery.cloud.entity.PlayerAuthEntity;
import net.fourstrategery.cloud.entity.PlayerEntity;

public interface PlayerAuthRepository extends JpaRepository<PlayerAuthEntity, Integer> {

	
	@Query("select pa.player from PlayerAuthEntity pa where pa.player.id = ?1 and pa.authenticationToken = ?2")
	public PlayerEntity getPlayerByIdAndToken(int player_id, String authToken);
}
