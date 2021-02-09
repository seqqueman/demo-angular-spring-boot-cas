package com.carm.base.modelo.servicio;

import org.springframework.stereotype.Service;

import com.carm.base.modelo.entidad.UserEntity;

@Service
public class UserServiceImpl implements IUserService {
	
	@Override
	public UserEntity findUserByEmail(String email) {
		UserEntity userEntity = new UserEntity();
		userEntity.setActive(true);
		userEntity.setEmail("miguel-angel.sequedo@getronics.com");
		userEntity.setFirstName("Miguel");
		userEntity.setLastName("Sequedo");
		userEntity.setId(1L);
		return userEntity;
	}

}