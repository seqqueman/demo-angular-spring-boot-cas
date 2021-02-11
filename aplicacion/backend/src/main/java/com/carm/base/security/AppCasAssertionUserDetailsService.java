package com.carm.base.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppCasAssertionUserDetailsService extends AbstractCasAssertionUserDetailsService {

	private static final Logger LOGGER =
		      LoggerFactory.getLogger(AppCasAssertionUserDetailsService.class);
	 
	  public static final String PASE_ATRIBUTO_DNI = "dni";

	  
	  @Override
	  protected UserDetails loadUserDetails(Assertion assertion) {
	    AttributePrincipal attributePrincipal = assertion.getPrincipal();

	    LOGGER.info(
	        "Autenticación en PASE realizada correctamente: attributePrincipal.name={}, "
	            + "attributePrincipal.attributes={}",
	        attributePrincipal.getName(), attributePrincipal.getAttributes());

	    String nif = obtenerAtributoCas(attributePrincipal.getAttributes(), PASE_ATRIBUTO_DNI);


	    // Obtener el perfil de usuario del componente de persistencia
	    LOGGER.info("Obtener perfil de usuario para el NIF={}", nif);
	    
	    // Añadir el perfil (role en la terminología de Spring Security) del usuario a la lista de
	    // perfiles
	    List<GrantedAuthority> lstGrantedAuthority = new ArrayList<>();
	    
	    // Guardar los atributos del contexto en el usuario
	    AppUserDetails dexelUserDetails = new AppUserDetails();
	    dexelUserDetails.setUsername(nif);
	    dexelUserDetails.setPassword("");
	    dexelUserDetails.setAccountNonExpired(true);
	    dexelUserDetails.setAccountNonLocked(true);
	    dexelUserDetails.setCredentialsNonExpired(true);
	    dexelUserDetails.setEnabled(true);
	    SimpleGrantedAuthority simpleGrantedAuthority =
		          new SimpleGrantedAuthority("ROLE_USER");
		      lstGrantedAuthority.add(simpleGrantedAuthority);

	    

	    dexelUserDetails.setPerfiles(lstGrantedAuthority);

	    return dexelUserDetails;
	  }

	  private String obtenerAtributoCas(Map<String, Object> atributosCas, String atributo) {
	    return (String) atributosCas.get(atributo);
	  }

	  

	}
