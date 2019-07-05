package com.leon.cracker.crackermaster.api;

import com.leon.cracker.crackermaster.models.FoundPasswordRequest;
import com.leon.cracker.crackermaster.models.MasterCrackingRequest;
import com.leon.cracker.crackermaster.models.SlaveInfo;
import com.leon.cracker.crackermaster.services.cracking.ICrackingService;
import com.leon.cracker.crackermaster.services.slaves.ISlaveManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/master")
public class MasterApi {

    private static final Logger logger = LoggerFactory.getLogger(MasterApi.class);

    private ISlaveManagerService slaveManagerService;
    private ICrackingService crackingService;

    @Autowired
    public void setSlaveManagerService(ISlaveManagerService slaveManagerService) {
        this.slaveManagerService = slaveManagerService;
    }

    @Autowired
    public void setCrackingService(ICrackingService crackingService) {
        this.crackingService = crackingService;
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

    @PostMapping("/crack")
    public ResponseEntity<String> crack(@RequestBody @Valid MasterCrackingRequest masterCrackingRequest){
        String requestId = UUID.randomUUID().toString();

        logger.info("Received cracking request {} and id {}", masterCrackingRequest, requestId);
        crackingService.crackHashes(masterCrackingRequest.getFileLocation(), requestId);

        return ResponseEntity.ok("Your hashes are being calculated, please check response at: " + requestId);
    }

    @PostMapping("/found-password")
    public void foundPassword(@RequestBody FoundPasswordRequest foundPasswordRequest){
        logger.info("Password Found! {}", foundPasswordRequest);
    }
}
