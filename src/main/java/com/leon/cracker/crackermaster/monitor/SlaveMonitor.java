package com.leon.cracker.crackermaster.monitor;

import com.leon.cracker.crackermaster.services.slaves.SlaveManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SlaveMonitor {

    private static final Logger logger = LoggerFactory.getLogger(SlaveMonitor.class);
    private SlaveManagerService slaveManagerService;

    @Autowired
    public void setSlaveManagerService(SlaveManagerService slaveManagerService) {
        this.slaveManagerService = slaveManagerService;
    }

    @Scheduled(fixedRate = 5000)
    public void checkSlaves() {

        slaveManagerService.getRegisteredSlaves()
                .forEach(slave -> {

                    if (slaveManagerService.isSlaveUp(slave.getURI()))
                        logger.info("Slave {} is UP!", slave.getName());
                    else
                        logger.info("Slave {} is DOWN!", slave.getName());

                });
    }
}
