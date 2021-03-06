package net.fourstrategery.cloud.repository;

import java.util.List;

import net.fourstrategery.cloud.entity.PlayerEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
	
	@Query("Select p from PlayerEntity p where p.screenName = ?1")
	public PlayerEntity getPlayerByScreenName(String username);
	
	@Query("Select p from PlayerEntity p where p.fourSquareId = ?1")
	public PlayerEntity getPlayerByfourSquareId(String fsId);
	
	@Query("Select p from PlayerEntity  p where p.password is null or p.password = ''")
	public List<PlayerEntity> getPlayersWithoutPassword();

}
