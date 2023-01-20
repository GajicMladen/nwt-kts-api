package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.DriverDTO;
import com.example.nwtktsapi.dto.MessageDTO;
import com.example.nwtktsapi.dto.UserDTO;
import com.example.nwtktsapi.model.Driver;
import com.example.nwtktsapi.model.Message;
import com.example.nwtktsapi.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransformToDTOService {

    public <T> List<Object> transformToDTOList(List<T> regularList){
        List<Object> ret= new ArrayList<>();
        for (T item: regularList) {
            if( item instanceof Driver)
                ret.add( new DriverDTO((Driver) item));
            else if( item instanceof User)
                ret.add( new UserDTO((User)item));
            else if( item instanceof Message)
                ret.add( new MessageDTO((Message) item));
            //for other types
        }
        return ret;
    }

}
