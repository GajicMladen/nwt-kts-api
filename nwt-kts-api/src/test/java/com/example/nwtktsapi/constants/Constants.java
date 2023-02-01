package com.example.nwtktsapi.constants;

import com.example.nwtktsapi.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class Constants {

    public static Long VALID_DRIVER_ID = 1L ;
    public static Long VALID_CLIENT_ID = 2L ;
    public static Long VALID_ADMIN_ID = 3L ;
    public static Long INVALID_DRIVER_ID = -1L ;
    public static Long INVALID_CLIENT_ID = -1L ;
    public static Long INVALID_ADMIN_ID = -1L ;

    public static String VALID_EMAIL = "test@gmail.com";
    public static String INVALID_EMAIL = "invalid.test@gmail.com";

    public static Driver testDriver = new Driver(
            1L,
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

}
