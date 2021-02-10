package com.carm.base.security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetails propio de la aplicación DEXEL.
 */
public class AppUserDetails implements UserDetails {

	public AppUserDetails(String sNif, List<GrantedAuthority> lstPerfiles, String sUsername, String sPassword,
			boolean bAccountNonExpired, boolean bAccountNonLocked, boolean bCredentialsNonExpired, boolean bEnabled) {
		super();
		this.sNif = sNif;
		this.lstPerfiles = lstPerfiles;
		this.sUsername = sUsername;
		this.sPassword = sPassword;
		this.bAccountNonExpired = bAccountNonExpired;
		this.bAccountNonLocked = bAccountNonLocked;
		this.bCredentialsNonExpired = bCredentialsNonExpired;
		this.bEnabled = bEnabled;
	}

	public AppUserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 5191334756549680229L;

	private String sNif;

	/**
	 * Lista de roles asignados al usuario.
	 * 
	 */
	private List<GrantedAuthority> lstPerfiles;

	/**
	 * Lista de perfiles responsable tramitador.
	 */

	/**
	 * Nombre de usuario.
	 */
	private String sUsername;

	/**
	 * Contrase�a del usuario.
	 */
	private String sPassword;

	/**
	 * Indica si la cuenta del usuario ha expirado.
	 */
	private boolean bAccountNonExpired;

	/**
	 * Indica si la cuenta del usuario está bloqueada.
	 */
	private boolean bAccountNonLocked;

	/**
	 * Indica si las credenciales del usuario han expirado.
	 */
	private boolean bCredentialsNonExpired;

	/**
	 * Indica si el usuario está activo.
	 */
	private boolean bEnabled;

	/**
	 * Devuelve la lista de roles asignados al usuario.
	 *
	 * @return Lista de roles asignados al usuario
	 */
	public final List<GrantedAuthority> getPerfiles() {
		return lstPerfiles;
	}

	/**
	 * Establece la lista de roles asignados al usuario.
	 *
	 * @param pPerfiles Lista de perfiles a establecer
	 */
	public final void setPerfiles(final List<GrantedAuthority> pPerfiles) {
		this.lstPerfiles = pPerfiles;
	}

	/**
	 * Devuelve el nombre de usuario.
	 *
	 * @return Nombre de usuario.
	 */
	public final String getUsername() {
		return sUsername;
	}

	/**
	 * Establece el nombre de usuario.
	 *
	 * @param pUsername Nombre de usuario a establecer
	 */
	public final void setUsername(final String pUsername) {
		this.sUsername = pUsername;
	}

	/**
	 * Devuelve la contrase�a del usuario.
	 *
	 * @return Contraseña del usuario.
	 */
	public final String getPassword() {
		return sPassword;
	}

	/**
	 * Establece la contrase�a del usuario.
	 *
	 * @param pPassword Contraseña a establecer
	 */
	public final void setPassword(final String pPassword) {
		this.sPassword = pPassword;
	}

	/**
	 * Indica si la cuenta del usuario ha expirado.
	 *
	 * @return true si la cuenta no ha expirado, false en caso contrario.
	 */
	public final boolean getAccountNonExpired() {
		return bAccountNonExpired;
	}

	/**
	 * Establece si la cuenta del usuario ha expirado.
	 *
	 * @param pAccountNonExpired true si la cuenta no ha expirado, false en caso
	 *                           contrario.
	 */
	public final void setAccountNonExpired(final boolean pAccountNonExpired) {
		this.bAccountNonExpired = pAccountNonExpired;
	}

	/**
	 * Indica si la cuenta del usuario está bloqueada.
	 *
	 * @return true si la cuenta no está bloqueada, false en caso contrario.
	 */
	public final boolean getAccountNonLocked() {
		return bAccountNonLocked;
	}

	/**
	 * Establece si la cuenta del usuario est� bloqueada.
	 *
	 * @param pAccountNonLocked true si la cuenta no está bloqueada, false en caso
	 *                          contrario.
	 */
	public final void setAccountNonLocked(final boolean pAccountNonLocked) {
		this.bAccountNonLocked = pAccountNonLocked;
	}

	/**
	 * Indica si las credenciales del usuario han expirado.
	 *
	 * @return true si las credenciales no han expirado, false en caso contrario.
	 */
	public final boolean getCredentialsNonExpired() {
		return bCredentialsNonExpired;
	}

	/**
	 * Establece si las credenciales del usuario han expirado.
	 *
	 * @param pCredentialsNonExpired true si las credenciales no han expirado, false
	 *                               en caso contrario.
	 */
	public final void setCredentialsNonExpired(final boolean pCredentialsNonExpired) {
		this.bCredentialsNonExpired = pCredentialsNonExpired;
	}

	/**
	 * Indica si el usuario est� activo.
	 *
	 * @return true si el usuario está habilitado, false en caso contrario.
	 */
	public final boolean getEnabled() {
		return bEnabled;
	}

	/**
	 * Establece si el usuario est� activo.
	 *
	 * @param pEnabled true si el usuario está habilitado, false en caso contrario.
	 */
	public final void setEnabled(final boolean pEnabled) {
		this.bEnabled = pEnabled;
	}

	/**
	 * Devuelve los permisos del usuario.
	 *
	 * @return lstPerfiles Lista con los permisos.
	 */
	@Override
	public final Collection<? extends GrantedAuthority> getAuthorities() {
		return this.lstPerfiles;
	}

	@Override
	public final boolean isAccountNonExpired() {
		return bAccountNonExpired;
	}

	@Override
	public final boolean isAccountNonLocked() {
		return bAccountNonLocked;
	}

	@Override
	public final boolean isCredentialsNonExpired() {
		return bCredentialsNonExpired;
	}

	@Override
	public final boolean isEnabled() {
		return bEnabled;
	}

	public String getNif() {
		return sNif;
	}

	public void setNif(String pNif) {
		this.sNif = pNif;
	}

}
