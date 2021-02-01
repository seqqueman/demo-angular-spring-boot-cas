package com.carm.base.modelo.servicio;

import java.util.List;

import com.carm.base.modelo.entidad.Prueba;

public interface IPruebaServicio {
	
	public List<Prueba> findAll();
	
	public Prueba findById(int id);
	
	public Prueba save(Prueba prueba);
	
	public boolean delete(int id);

}
