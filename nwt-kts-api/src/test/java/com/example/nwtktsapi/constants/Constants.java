package com.example.nwtktsapi.constants;

import com.example.nwtktsapi.model.Client;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Message;

import java.util.List;

public class Constants {

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
}
