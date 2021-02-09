package com.carm.base.modelo.entidad;

import java.io.Serializable;
import java.util.List;

/*
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
*/
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/*
@Entity
@Table(name = "users")
*/
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	*/
	private Long id;

	@NotBlank
	//@Column(nullable = false)
	private String firstName;

	@NotBlank
	//@Column(nullable = false)
	private String lastName;

	@Email
	//@Column(nullable = false, unique = true)
	private String email;

	//@Column(nullable = false)
	private boolean isActive;
	
	/*
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	*/
	private List<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}