package com.carm.base.modelo.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carm.base.modelo.entidad.UserEntity;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	  private final IUserService userService;
	  
	  @Autowired
	  public CustomUserDetailsServiceImpl(IUserService userService) {
	    this.userService = userService;
	  }

	  @Override
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    UserEntity userEntity = this.userService.findUserByEmail(username);
	    if (userEntity == null)
	      throw new UsernameNotFoundException(username);
	    return new User(userEntity.getEmail(), "", userEntity.isActive(), true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER"));
	  }
	
}
