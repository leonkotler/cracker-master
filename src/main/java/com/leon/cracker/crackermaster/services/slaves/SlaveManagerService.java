package com.leon.cracker.crackermaster.services.slaves;

import com.leon.cracker.crackermaster.externalapi.ISlaveAPI;
import com.leon.cracker.crackermaster.models.SlaveCrackingRequest;
import com.leon.cracker.crackermaster.models.SlaveInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SlaveManagerService implements ISlaveManagerService {

    private static final Logger logger = LoggerFactory.getLogger(SlaveManagerService.class);

    private Set<SlaveInfo> registeredSlaves = ConcurrentHashMap.newKeySet();

    private ISlaveAPI slaveAPI;

    @Value("${slave.executable.location}")
    private String slaveExecutable;

    @Value("${server.port}")
    private int port;

    @Autowired
    public void setSlaveAPI(ISlaveAPI slaveAPI) {
        this.slaveAPI = slaveAPI;
    }

    @Override
    public void launchSlaves(int numOfSlaves) {

        String host = getLocalHost();

        for (int i = 0; i < numOfSlaves; i++) {

            Runtime rt = Runtime.getRuntime();
            try {
                logger.info("Starting slave");
                rt.exec("cmd /c start java -jar -Dslave.name=slave-" + new Date().getTime() + " " + slaveExecutable + " -master " + host + ":" + port);
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
        return slaveAPI.isSlaveUp(slaveUri);
    }

    @Override
    public void removeSlave(SlaveInfo slaveInfo) {
        this.getRegisteredSlaves().remove(slaveInfo);
    }

    @Override
    public void sendForCracking(List<String> hashes, String requestId) {

        int startOfRange = 500000000;
        int jump = calculateJump(getRegisteredSlaves().size());

        for (SlaveInfo slaveInfo : this.getRegisteredSlaves()) {
            SlaveCrackingRequest cr = new SlaveCrackingRequest(hashes, requestId, startOfRange, startOfRange + jump);
            slaveAPI.sendCrackingRequestToSlave(slaveInfo, cr);
            startOfRange += jump;
        }

    }

    private int calculateJump(int numOfSlaves) {
        // We divide the total number range and add 1 if number of slaves is even
        return 99999999 / numOfSlaves + (numOfSlaves % 2 == 0 ? 1 : 0);
    }



}
