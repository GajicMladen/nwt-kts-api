package com.example.nwtktsapi.constants;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.nwtktsapi.dto.RideDTO;
import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Fare;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.model.Vehicle;
import com.example.nwtktsapi.model.VehicleType;

public class Constants {

    public static Long VALID_DRIVER_ID = 1L ;
    public static Long VALID_CLIENT_ID = 2L ;
    public static Long VALID_ADMIN_ID = 3L ;
    public static Long INVALID_DRIVER_ID = -1L ;
    public static Long INVALID_CLIENT_ID = -1L ;
    public static Long INVALID_ADMIN_ID = -1L ;

    public static Long USER_ROLE_ID = 1L;
    public static Long ADMIN_ROLE_ID = 2L;
    public static Long DRIVER_ROLE_ID = 3L;
    
    public static Integer NUM_OF_USERS = 4;
    public static Integer NUM_OF_DRIVERS = 3;
    public static Integer NUM_OF_ADMINS = 1;
    
    public static String VALID_EMAIL = "test@gmail.com";
    public static String INVALID_EMAIL = "invalid.test@gmail.com";

    public static Long VALID_FARE_ID = 5L;
    public static Long INVALID_FARE_ID = 5L;
    public static Long INVALID_FARE_ID_2 = 99L;

    public static Driver testDriver = new Driver(
            9L,
            "pass",
            "test@gmail.com",
            "Test",
            "Teskovic",
            "TestTown",
            "0654598",
            "",
            true,
            false,
            0,
            null,
            null,
            null,
            false
    );

    public static Client testClient = new Client(
            1L,
            "pass",
            "c",
            "Test",
            "Teskovic",
            "TestTown",
            "065459821",
            "",
            true,
            false,
            0,
            null,
            null,
            null,
            false);
    
    public static Client testClient2 = new Client(
            2L,
            "pass",
            "aaa2@mail.com",
            "Testadin",
            "Testarevic",
            "TestTown",
            "065459821",
            "",
            true,
            false,
            0,
            null,
            null,
            null,
            false);

    public static User testUser = new User(
            1L,
            "pass",
            "test@gmail.com",
            "Test",
            "Teskovic",
            "TestTown",
            "065459821",
            "",
            true,
            false,
            0,
            null,
            null,
            null,
            false);

    public static Driver testDriver2 = new Driver(
            2L,
            "pass",
            "test@gmail.com",
            "Test",
            "Teskovic",
            "TestTown",
            "0654598",
            "",
            true,
            false,
            0,
            null,
            null,
            null,
            false
    );

    public static Driver testDriver3 = new Driver(
            3L,
            "pass",
            "test@gmail.com",
            "Test",
            "Teskovic",
            "TestTown",
            "0654598",
            "",
            true,
            false,
            0,
            null,
            null,
            null,
            false
    );

    public static Driver testDriver4 = new Driver(
            4L,
            "pass",
            "test@gmail.com",
            "Test",
            "Teskovic",
            "TestTown",
            "0654598",
            "",
            true,
            false,
            0,
            null,
            null,
            null,
            false
    );

    public static Vehicle testVehicle = new Vehicle(
            "jeep",
            "MZ007",
            4,
            VehicleType.BASIC
    );
    public static Vehicle testVehicle2 = new Vehicle(
            "jeep",
            "MZ0071",
            4,
            VehicleType.BASIC
    );
    public static Vehicle testVehicle3 = new Vehicle(
            "jeep",
            "MZ0027",
            4,
            VehicleType.BASIC
    );
    public static Vehicle testVehicle4 = new Vehicle(
            "jeep",
            "MZ0073",
            4,
            VehicleType.BASIC
    );

    public static Fare testFare1 = new Fare(
            1L,
            null,
            "start1",
            "end1",
            testDriver,
            null,
            null,
            null,
            null,
            123,
            LocalDateTime.now().minusMinutes(15),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(5),
            true,
            false,
            false,
            450,
            5f,
            true,
            false
    );
    public static Fare testFare2 = new Fare(
            2L,
            null,
            "start2",
            "end2",
            testDriver2,
            null,
            null,
            null,
            null,
            123,
            LocalDateTime.now().minusMinutes(15),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(5),
            true,
            false,
            false,
            450,
            5f,
            true,
            false
    );

    public static Fare testFare3 = new Fare(
            3L,
            null,
            "start3",
            "end3",
            testDriver3,
            null,
            null,
            null,
            null,
            123,
            LocalDateTime.now().minusMinutes(15),
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(5),
            true,
            false,
            false,
            450,
            5f,
            true,
            false
    );

    public static LocalDateTime VALID_START_TIME = LocalDateTime.now();
    public static LocalDateTime VALID_END_TIME = LocalDateTime.now().plusMinutes(15);

    public static LocalDateTime INVALID_START_TIME = LocalDateTime.now().plusDays(15);
    public static LocalDateTime INVALID_END_TIME = LocalDateTime.now().plusDays(15).plusMinutes(15);


    public static RideDTO RIDE_DTO = new RideDTO(
            null,
            null,
            null,
            "bla,13,14;dsa,13,14.2",
            new String[]{},
            0,
            123,
            7,
            435,
            false,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(7),
            new ArrayList<>(),
            "[[13,14],[13,14.2]]",
            "0"
    );
}
