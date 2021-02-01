package com.carm.base.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carm.base.modelo.entidad.Prueba;
import com.carm.base.modelo.servicio.IPruebaServicio;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/back")
public class PruebaRestControlador {
	
	private static final Logger logger = LogManager.getLogger(PruebaRestControlador.class);

	@Autowired
	private IPruebaServicio pruebaService;
	
	@GetMapping("/prueba")
	public ResponseEntity<?> inicio(){
		return ResponseEntity.ok().body(pruebaService.findAll());
	}
	
	@GetMapping("/prueba/{id}")
	public ResponseEntity<?> porIdentificador(@PathVariable int id){
		
		Prueba prueba = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			prueba = pruebaService.findById(id);
		} catch(Exception e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			logger.error(e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(prueba == null) {
			response.put("mensaje", "La prueba ID: " + id + " no existe en el sistema!!!!!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			
		}
		
		return new ResponseEntity<Prueba>(prueba, HttpStatus.OK);
		
	}
	
	@PostMapping("/prueba")
	public ResponseEntity<?> create(@Valid @RequestBody Prueba prueba, BindingResult result){
		Prueba pruebaNuevo = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			pruebaNuevo = pruebaService.save(prueba);
		}catch(Exception e) {
			response.put("mensaje", "Error al realizar el alta de la prueba en el sistema");
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La prueba se ha creado con exito en el sistema");
		response.put("prueba", pruebaNuevo);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/prueba/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Prueba prueba, BindingResult result, @PathVariable int id) {
		
		Map<String, Object> response = new HashMap<>();
		Prueba pruebaActual = null;
		Prueba pruebaUpdated = null;
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+err.getField()+"' "+err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			pruebaActual = pruebaService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			logger.error(e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(pruebaActual == null) {
			response.put("mensaje", "La prueba con ID: " + id + " no existe en el sistema!!!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			pruebaActual.setConcepto(prueba.getConcepto());
			pruebaUpdated = pruebaService.save(pruebaActual);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la modificacion de la prueba en el sistema");
			logger.error(e.getMessage());
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La prueba se ha modificado con exito");
		response.put("prueba", pruebaUpdated);
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		Map<String, Object> response = new HashMap<>();
		try {
			pruebaService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en el sistema");
			logger.error(e.getMessage());
						
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "La prueba se ha eliminado con exito");
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	}
	
}
