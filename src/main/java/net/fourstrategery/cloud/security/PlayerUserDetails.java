package net.fourstrategery.cloud.security;

import java.util.Collection;
import java.util.HashSet;

import net.fourstrategery.cloud.entity.PlayerEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class PlayerUserDetails extends User implements UserDetails {

	
	private PlayerEntity player;
	private  Collection<GrantedAuthority> authorities;
	
	public PlayerUserDetails(PlayerEntity player, Collection<GrantedAuthority> auths) {
		super(player.getScreenName(),player.getPassword(),auths);
		this.player = player;
		this.authorities = auths;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return player.getPassword();
	}

	@Override
	public String getUsername() {
		return player.getScreenName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
