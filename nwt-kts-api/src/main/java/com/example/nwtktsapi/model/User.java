package com.example.nwtktsapi.model;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements UserDetails {
   
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @JsonIgnore
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name ="name",nullable = false)
    private String name;

    @Column(name = "lastname",nullable = false)
    private String lastName;

    @Column(name = "town", nullable = false)
    private String town;
    
    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "active",columnDefinition = "boolean default false")
    private boolean active;

    @Column(name = "blocked",columnDefinition = "boolean default false")
    private boolean blocked;

    @Column(name = "tokens" ,columnDefinition="Decimal(10,2) default '0.00'")
    private float tokens = 0;

    @OneToMany( mappedBy = "sender")
    private List<Note> notes;

    @OneToMany( mappedBy = "user")
    private List<Message> messages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    protected List<Role> roles;
    
    public User() {
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }
    
    public void setTown(String town) {
    	this.town = town;
    }
    
    public String getTown() {
    	return this.town;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    
	public List<Role> getRoles() {
        return roles;
     }
    
	public void setRoles(List<Role> roles) {
	  this.roles = roles;
	}
	
	public String getRoleString() {
	  return this.roles.get(0).getName();
	}

    public float getTokens() {
        return tokens;
    }

    public void setTokens(float tokens) {
        this.tokens = tokens;
    }

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return this.roles;
	}


	@Override
	public String getUsername() {
		return email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean hasRole(String roleName){
		for (Role role: roles) {
			if(role.getName().equals(roleName))
				return true;
		}
		return false;
	}
}
