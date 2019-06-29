package com.leon.cracker.crackermaster.api;

import com.leon.cracker.crackermaster.models.SlaveInfo;
import com.leon.cracker.crackermaster.services.slaves.SlaveManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/master")
public class MasterApi {

    private static final Logger logger = LoggerFactory.getLogger(MasterApi.class);

    private SlaveManagerService slaveManagerService;

    @Autowired
    public void setSlaveManagerService(SlaveManagerService slaveManagerService) {
        this.slaveManagerService = slaveManagerService;
    }

    @PostMapping("/create-slaves/{numOfSlaves}")
    public ResponseEntity<String> createSlaves(@PathVariable int numOfSlaves){
        logger.info("Received createSlaves request with {} slaves", numOfSlaves);

        slaveManagerService.launchSlaves(numOfSlaves);
        return ResponseEntity.ok("Slaves are being created...");
    }

    @PostMapping("/register-slave")
    public ResponseEntity<SlaveInfo> registerSlave(@RequestBody @Valid SlaveInfo slaveInfo){
        logger.info("Received registerSlave request from: {}", slaveInfo);

        slaveManagerService.registerSlave(slaveInfo);
        return ResponseEntity.ok(slaveInfo);
    }
}
