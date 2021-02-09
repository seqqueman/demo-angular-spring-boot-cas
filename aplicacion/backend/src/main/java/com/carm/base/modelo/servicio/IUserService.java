package com.carm.base.modelo.servicio;

import com.carm.base.modelo.entidad.UserEntity;

public interface IUserService {

	public UserEntity findUserByEmail(String email);
	
}
