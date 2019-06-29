package com.leon.cracker.crackermaster.services.slaves;

import com.leon.cracker.crackermaster.models.SlaveInfo;
import com.leon.cracker.crackermaster.monitor.HealthStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SlaveManager implements SlaveManagerService {

    private static final Logger logger = LoggerFactory.getLogger(SlaveManager.class);

    private Set<SlaveInfo> registeredSlaves = ConcurrentHashMap.newKeySet();

    private static volatile AtomicInteger slaveCounter = new AtomicInteger(0);

    @Value("${slave.executable.location}")
    private String slaveExecutable;

    @Value("${server.port}")
    private int port;


    @Override
    public void launchSlaves(int numOfSlaves) {

        String host = getLocalHost();

        for (int i = 0; i < numOfSlaves; i++) {

            Runtime rt = Runtime.getRuntime();
            try {
                logger.info("Starting slave");
                rt.exec("cmd /c start java -jar -Dslave.name=slave-" + slaveCounter.getAndIncrement() + " " + slaveExecutable + " -master " + host + ":" + port);
            } catch (IOException e) {
                logger.error("Couldn't start slave");
                e.printStackTrace();
            }
        }

    }

    @Override
    public void registerSlave(SlaveInfo slaveInfo) {
        registeredSlaves.add(slaveInfo);
        logger.info("A new slave has been registered {}", slaveInfo);
    }

    private String getLocalHost() {

        String host = null;
        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return host;
    }

    @Override
    public Set<SlaveInfo> getRegisteredSlaves() {
        return this.registeredSlaves;
    }

    @Override
    public boolean isSlaveUp(URI slaveUri) {
        RestTemplate restTemplate = new RestTemplate();

        try {

            return restTemplate
                    .getForEntity(slaveUri + "/actuator/health", HealthStatus.class)
                    .getStatusCode()
                    .equals(HttpStatus.OK);

        } catch (ResourceAccessException exception) {
            return false;
        }
    }
}
