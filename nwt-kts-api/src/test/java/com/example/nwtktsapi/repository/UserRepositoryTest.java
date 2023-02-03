package com.example.nwtktsapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        User user = Constants.testDriver;
        user = userRepository.save(user);

        User foundUser = userRepository.findByEmail(user.getEmail());
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void testClientInRide() {
        User user = Constants.testClient;
        user.setInRide(true);
        user = userRepository.save(user);

        User foundUser = userRepository.clientInRide(user.getId());
        assertEquals(user.getId(), foundUser.getId());
    }
    
    @Test
    void testClientNotInRide() {
    	User user = Constants.testClient;
    	user.setInRide(false);
    	userRepository.save(user);
    	
    	User foundUser = userRepository.clientInRide(user.getId());
    	assertNull(foundUser);
    }
    
    @Test
    void testUserByTypeCount() {
    	int numOfUsers = userRepository.getUserByTypeCount(Constants.USER_ROLE_ID);
    	int numOfAdmins = userRepository.getUserByTypeCount(Constants.ADMIN_ROLE_ID);
    	int numOfDrivers = userRepository.getUserByTypeCount(Constants.DRIVER_ROLE_ID);
    	assertEquals(numOfUsers, Constants.NUM_OF_USERS);
    	assertEquals(numOfAdmins, Constants.NUM_OF_ADMINS);
    	assertEquals(numOfDrivers, Constants.NUM_OF_DRIVERS);
    }
    
    @Test
    void testGetClientsAndDriver() {
    	List<User> usersAndDrivers = userRepository.getClientsAndDrivers();
    	assertEquals(usersAndDrivers.size(), Constants.NUM_OF_DRIVERS + Constants.NUM_OF_USERS);
    }
    
    @Test
    void testFindByRoleId_User() {
    	List<User> users = userRepository.findByRoles_Id(Constants.USER_ROLE_ID);
    	assertEquals(users.size(), Constants.NUM_OF_USERS);
    }
    
    @Test
    void testFindByRoleId_Driver() {
    	List<User> users = userRepository.findByRoles_Id(Constants.DRIVER_ROLE_ID);
    	assertEquals(users.size(), Constants.NUM_OF_DRIVERS);
    }
}
