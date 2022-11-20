package com.example.nwtktsapi.dto;

import com.example.nwtktsapi.model.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.nwtktsapi.model.User} entity
 */
public class UserDTO implements Serializable {
    private final Long id;
    private final String email;
    private final String name;
    private final String lastName;
    private final String phone;
    private final String profilePhoto;
    private final boolean active;

    private final boolean blocked;

    public UserDTO(Long id, String email, String name, String lastName, String phone, String profilePhoto, boolean active , boolean blocked) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.profilePhoto = profilePhoto;
        this.active = active;
        this.blocked = blocked;
    }
    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.profilePhoto = user.getProfilePhoto();
        this.active = user.isActive();
        this.blocked = user.isBlocked();
    }



    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public boolean getActive() {
        return active;
    }

    public boolean getBlocked() {
        return blocked;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO entity = (UserDTO) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.email, entity.email) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.phone, entity.phone) &&
                Objects.equals(this.profilePhoto, entity.profilePhoto) &&
                Objects.equals(this.active, entity.active) &&
                Objects.equals(this.blocked, entity.blocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, lastName, phone, profilePhoto, active);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "name = " + name + ", " +
                "lastName = " + lastName + ", " +
                "phone = " + phone + ", " +
                "profilePhoto = " + profilePhoto + ", " +
                "blocked = " + blocked + ", " +
                "active = " + active + ")";
    }
}