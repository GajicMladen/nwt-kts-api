package com.example.nwtktsapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nwtktsapi.dto.RegistrationDTO;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User getUserById(Long userId){
		Optional<User> user =  userRepository.findById(userId);
		return user.orElse(null);
	}

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", username));
		} else {
			return user;
		}
	}
    
	public User findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public User save(RegistrationDTO registrationDTO) {
		
		User newUser = new User();
		
		newUser.setEmail(registrationDTO.getEmail());
		newUser.setName(registrationDTO.getName());
		newUser.setLastName(registrationDTO.getLastName());
		newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
		newUser.setPhone(registrationDTO.getPhoneNumber());
		newUser.setActive(false);
		newUser.setBlocked(false);
		newUser.setProfilePhoto("");
		
		return this.userRepository.save(newUser);
	}

}
