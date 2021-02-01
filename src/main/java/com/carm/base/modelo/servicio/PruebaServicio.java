package com.carm.base.modelo.servicio;

import java.util.LinkedList;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

//import com.carm.base.modelo.dao.IPruebaDAO;
import com.carm.base.modelo.entidad.Prueba;

@Service
public class PruebaServicio implements IPruebaServicio {
	
//	@Autowired private IPruebaDAO pruebaDAO;

	@Override
	public List<Prueba> findAll() {
		List<Prueba> lista = new LinkedList<>();
		Prueba prueba = null;
		for(int i = 0; i < 4; i++) {
			prueba = new Prueba();
			prueba.setNumero(i);
			prueba.setConcepto("concepto " + i);
			lista.add(prueba);
		}
		return lista;
	}
/*
	@Override
	@Transactional(readOnly = true)
	public List<Prueba> findAll() {
		return (List<Prueba>) pruebaDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Prueba findById(int id) {
		return pruebaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Prueba save(Prueba cliente) {
		return pruebaDAO.save(cliente);
	}

	@Override
	@Transactional
	public void delete(int id) {
		pruebaDAO.deleteById(id);		
	}
*/

	@Override
	public Prueba findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Prueba save(Prueba prueba) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
