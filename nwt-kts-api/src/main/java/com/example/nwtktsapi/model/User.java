package com.example.nwtktsapi.model;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
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

    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "active",columnDefinition = "boolean default false")
    private boolean active;

    @Column(name = "blocked",columnDefinition = "boolean default false")
    private boolean blocked;

    @OneToMany( mappedBy = "sender")
    private List<Message> messages;

    public User() {
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
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
}
