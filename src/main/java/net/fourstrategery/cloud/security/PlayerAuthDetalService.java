package net.fourstrategery.cloud.security;

import java.util.Collection;
import java.util.HashSet;

import net.fourstrategery.cloud.entity.PlayerEntity;
import net.fourstrategery.cloud.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Primary
@Component("playerAuthDetailService")
public class PlayerAuthDetalService implements UserDetailsService {

	public static final String ROLE_BASE_USER = "ROLE_BaseUser";
	@Autowired
	PlayerRepository playerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		PlayerEntity player = playerRepository.getPlayerByScreenName(username);
		
		return new PlayerUserDetails(player,determineAuthorities());
	}
	
	private Collection<GrantedAuthority> determineAuthorities() {
		HashSet<GrantedAuthority> returnVal = new HashSet<GrantedAuthority>();
		returnVal.add(new SimpleGrantedAuthority(ROLE_BASE_USER));
		return returnVal;
	}

}
