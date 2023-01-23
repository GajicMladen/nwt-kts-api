package com.example.nwtktsapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nwtktsapi.dto.AdminRegistrationDTO;
import com.example.nwtktsapi.dto.RegistrationDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.DriverStatus;
import com.example.nwtktsapi.model.Role;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.model.Vehicle;
import com.example.nwtktsapi.model.VehicleType;
import com.example.nwtktsapi.repository.UserRepository;
import com.example.nwtktsapi.repository.VehicleRepository;

@Service
public class UserService implements UserDetailsService{
	
    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	private final String DEFAULT_PHOTO_URL = "https://i.ibb.co/VCfhmKQ/image.jpg";
	
	public User getUserById(Long userId){
		Optional<User> user =  userRepository.findById(userId);
		return user.orElse(null);
	}

	
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

	public List<User> getAllDrivers(){
		return userRepository.findByRoles_Id(roleService.findByName("ROLE_DRIVER").getId());
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
	
	@Transactional
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
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
		newUser.setProfilePhoto(DEFAULT_PHOTO_URL);
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleService.findByName("ROLE_USER"));
		newUser.setRoles(roles);
		
		return this.userRepository.save(newUser);
	}
	
	public void setEncryptedPassword(User user, String rawPassword) {
		user.setPassword(passwordEncoder.encode(rawPassword));
	}
	
	@Transactional
	public User save(User user) {
		return this.userRepository.save(user);
	}
	
	//Returns error string, or null if no errors.
	public String validateRegistrationDTO(RegistrationDTO dto) {
		if(!dto.validate())
			return "Uneti podaci su neispravni!";
		
		User existingUser = findByEmail(dto.getEmail());
		
		if (existingUser != null)
			return "E-mail je već upotrebljen!";
		return null;
		
	}
	
	public Driver createDriverFromDto(AdminRegistrationDTO dto) {
		Driver driver = new Driver();
		driver.setEmail(dto.getEmail());
		driver.setName(dto.getName());
		driver.setLastName(dto.getLastName());
		driver.setPassword(passwordEncoder.encode(dto.getPassword()));
		driver.setPhone(dto.getPhoneNumber());
		driver.setTown(dto.getTown());
		driver.setActive(true);
		driver.setBlocked(false);
		driver.setProfilePhoto(DEFAULT_PHOTO_URL);
		driver.setDriverStatus(DriverStatus.UNAVAILABLE);
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleService.findByName("ROLE_DRIVER"));
		driver.setRoles(roles);
		
		Vehicle vehicle = new Vehicle();
		vehicle.setName(dto.getVehicleName());
		vehicle.setPlateNumber(dto.getPlateNumber());
		vehicle.setCapacity(dto.getCapacity());
		vehicle.setType(getVehicleTypeFromString(dto.getVehicleType()));
		
		vehicle.setDriver(driver);
		driver.setVehicle(vehicle);
		
		userRepository.save(driver);
		
		return driver;
	}
	
	private VehicleType getVehicleTypeFromString(String str) {
		switch(str) {
			case "Basic": return VehicleType.BASIC;
			case "Lux": return VehicleType.LUX;
			case "Jumbo": return VehicleType.BIG;
			case "Baby Friendly": return VehicleType.BABY_SEAT;
			case "Pet Friendly": return VehicleType.PET_FRIENDLY;
			default: return VehicleType.BASIC;
		}
	}

	public List<User> getAllClientsPagination(int page, int size) {
		return userRepository.findUsersByTypePagination(1L, PageRequest.of(page, size));
	}

	public List<User> getAllDriversPagination(int page, int size) {
		return userRepository.findUsersByTypePagination(3L, PageRequest.of(page, size));
	}

    public User blockUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			User u = user.get();
			u.setBlocked(true);
			userRepository.save(u);
			return u;
		}
		return null;
    }

	public int getClientsCount() {
		return userRepository.getUserByTypeCount(1L);
	}

	public int getDriversCount() {
		return userRepository.getUserByTypeCount(3L);
	}
}
