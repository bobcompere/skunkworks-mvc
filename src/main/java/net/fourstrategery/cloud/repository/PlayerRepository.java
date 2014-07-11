package net.fourstrategery.cloud.repository;

import net.fourstrategery.cloud.entity.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
	
	@Query("Select p from Player p where p.screenName = ?1")
	public Player getPlayerByScreenName(String username);

}
