package com.leon.cracker.crackermaster;

import com.leon.cracker.crackermaster.services.slaves.ISlaveManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SlaveRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SlaveRunner.class);

    private ISlaveManagerService slaveManagerService;


    @Autowired
    public void setSlaveManagerService(ISlaveManagerService slaveManagerService) {
        this.slaveManagerService = slaveManagerService;
    }

    @Override
    public void run(String... args) throws Exception {

        int numOfSlaves = Integer.parseInt(args[1]);
        slaveManagerService.launchSlaves(numOfSlaves);
    }
}
