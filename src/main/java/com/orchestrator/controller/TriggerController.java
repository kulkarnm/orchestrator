package com.orchestrator.controller;

import com.orchestrator.processors.GenericProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TriggerController {
    @Autowired
    GenericProcess process ;
    @PostMapping( value = "/trigger",consumes ="application/json",produces = "application/json" )
    public ResponseEntity<Object> updateOperatingExpensePaid(){
        List factRegistry = new ArrayList<String>();
        Map input = new HashMap();
        input.put("FACTS", factRegistry);
        process.execute(input);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
