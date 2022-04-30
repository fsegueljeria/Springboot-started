package com.fseguel.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fseguel.springboot.app.commons.usuarios.models.entity.Usuario;
import com.fseguel.springboot.app.oauth.clients.IUsuarioFeignClient;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

	private Logger log = LoggerFactory.getLogger(UsuarioService.class); 
	
	@Autowired
	private IUsuarioFeignClient client;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = null;
		try {
			usuario = client.findByUsername(username);
			
			List<GrantedAuthority> autorithies = usuario.getRoles()
					.stream()
					.map(role -> new SimpleGrantedAuthority(role.getNombre()))
					.peek(autority -> log.info("Role: " + autority.getAuthority()))
					.collect(Collectors.toList());
			
			log.info("Usuario autenticado: " + username);
			
			return new User(usuario.getUsername(), usuario.getPassword(),usuario.getEnabled(), true, true, true, autorithies);
			
		} catch (Exception e) {
			log.error("Error en Login, no existe el usuario " + username + " en el sistema");
			throw new UsernameNotFoundException("Error en Login, no existe el usuario " + username + " en el sistema");
		}
		
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

	@Override
	public Usuario update(Usuario usuario, Long id) {
		return client.update(usuario, id);
	}

}
