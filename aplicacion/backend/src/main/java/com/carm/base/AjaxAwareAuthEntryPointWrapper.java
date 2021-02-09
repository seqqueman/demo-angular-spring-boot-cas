package com.carm.base;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Obtenido de: https://gist.github.com/mikeapr4/d76ed0f2ae9779975bf1
 * 
 * Wrapper for Spring Security AuthenticationEntryPoint interface, which will intercept 302
 * Redirects which are not supported generally in Browsers for Ajax Requests.
 * 
 * Convenient wrapper class which proxies the AuthenticationEntryPoint interface
 * (https://docs.spring.io/spring-security/site/docs/current/apidocs/org/springframework/security/web/AuthenticationEntryPoint.html).
 * 
 * It was designed with a CAS spring security configuration, but the proxy respects all the
 * interfaces involved and should be neatly abstracted. Within the class a further wrapper is used
 * around the response in order to intercept the 302 Redirect directly. Due to the nature of
 * HttpServletResponse this needs to be intercepted at the moment it's triggered, no later.
 * 
 * @author https://gist.github.com/mikeapr4
 *
 */

@Component
public class AjaxAwareAuthEntryPointWrapper implements AuthenticationEntryPoint {

  private static final Logger LOG = LoggerFactory.getLogger(AjaxAwareAuthEntryPointWrapper.class);

  // Status code para el timeout de sesión. Se usa el 440 a pesar de que sea una extensión de
  // Microsoft (más info en http://getstatuscode.com/440). Realmente no importa usar un status code
  // u otro siempre que backend y frontend estén coordinados.
  private static final int TIMEOUT_ERROR_CODE = 440;

  private AuthenticationEntryPoint realEntryPoint;

  public AjaxAwareAuthEntryPointWrapper(AuthenticationEntryPoint casEntryPoint) {
    this.realEntryPoint = casEntryPoint;
  }

  @Override
  public final void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }
  
}

