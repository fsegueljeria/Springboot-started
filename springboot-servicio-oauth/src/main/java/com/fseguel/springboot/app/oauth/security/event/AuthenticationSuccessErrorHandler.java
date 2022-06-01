package com.fseguel.springboot.app.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.fseguel.springboot.app.commons.usuarios.models.entity.Usuario;
import com.fseguel.springboot.app.oauth.services.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {
	// Clase utilizada para manejar evento de error o de success en base a la autenticacipn
	private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private Tracer tracer;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		
		if(authentication.getDetails() instanceof WebAuthenticationDetails) // valida que autenticacon sea instancia de WebAuthenticationDetails para el filtro del username/password app
			return;
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		String mensaje = "Success Login: " + user.getUsername();
		log.info(mensaje);
		System.out.println(mensaje);
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
			 usuario.setIntentos(0);
			 usuarioService.update(usuario, usuario.getId());
		}
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		
		String mensaje = "Error Login: " + exception.getMessage();
		log.error(mensaje);
		System.out.println(mensaje);
		
		try {
			
			StringBuilder errors = new StringBuilder();
			errors.append(mensaje);
			
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			if(usuario.getIntentos() == null) 
				usuario.setIntentos(0);
			
			log.info("Intento actual es de: " + usuario.getIntentos());
			usuario.setIntentos(usuario.getIntentos()+1);
			log.info("Intento actual es de: " + usuario.getIntentos());
			
			errors.append(" Intentos del login: " + usuario.getIntentos());
			
			if(usuario.getIntentos() >= 3) {
				String errorMaxAttempt = String.format("El usuario %s des-habilitado por máximo intentos.", authentication.getName()); 
				log.info(errorMaxAttempt);
				errors.append(" - " + errorMaxAttempt);
				usuario.setEnabled(false);
			}
			usuarioService.update(usuario, usuario.getId());
			tracer.currentSpan().tag("error.mensaje", errors.toString());
		} catch (FeignException e) {
			log.error(String.format("El usuario %s no existe en el sistema", authentication.getName()));
		}
		
		
		
	}

}
