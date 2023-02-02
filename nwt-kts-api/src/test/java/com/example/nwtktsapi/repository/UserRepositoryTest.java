package com.example.nwtktsapi.repository;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        User user = Constants.testDriver;
        userRepository.save(user);

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

}
