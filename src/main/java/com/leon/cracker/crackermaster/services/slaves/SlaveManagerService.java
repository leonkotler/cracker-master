package com.leon.cracker.crackermaster.services.slaves;

import com.leon.cracker.crackermaster.externalapi.ISlaveAPI;
import com.leon.cracker.crackermaster.models.SlaveCrackingRequest;
import com.leon.cracker.crackermaster.models.SlaveDoneRequest;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SlaveManagerService implements ISlaveManagerService {

    private static final Logger logger = LoggerFactory.getLogger(SlaveManagerService.class);

    private Map<SlaveInfo, List<SlaveCrackingRequest>> registeredSlaves = new ConcurrentHashMap<>();

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
        registeredSlaves.put(slaveInfo, new ArrayList<>());
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
    public Map<SlaveInfo, List<SlaveCrackingRequest>> getRegisteredSlaves() {
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
    public void sendForCracking(SlaveCrackingRequest slaveCrackingRequest) {

        int jump = calculateJump(slaveCrackingRequest.getStart(), slaveCrackingRequest.getEnd(), getRegisteredSlaves().size());

        sendCrackingRequestToEachSlave(
                slaveCrackingRequest.getHashes(),
                slaveCrackingRequest.getRequestId(),
                slaveCrackingRequest.getStart(),
                jump);

    }

    @Override
    public void handleSlaveDoneRequest(SlaveDoneRequest slaveDoneRequest) {
        logger.info("Slave {} is done with {}", slaveDoneRequest.getSlaveInfo(), slaveDoneRequest.getSlaveCrackingRequest());
        logger.info("Removing request from slave");
        registeredSlaves.get(slaveDoneRequest.getSlaveInfo()).remove(slaveDoneRequest.getSlaveCrackingRequest());
    }

    private int calculateJump(int startOfRange, int endOfRange, int numOfSlaves) {
        return (endOfRange - startOfRange) / numOfSlaves;
    }

    private void sendCrackingRequestToEachSlave(List<String> hashes, String requestId, int startOfRange, int jump) {

        for (Map.Entry<SlaveInfo, List<SlaveCrackingRequest>> entry : registeredSlaves.entrySet()) {

            SlaveCrackingRequest cr = new SlaveCrackingRequest(hashes, requestId, startOfRange, startOfRange + jump);
            slaveAPI.sendCrackingRequestToSlave(entry.getKey(), cr);
            addRequestToSlave(entry.getKey(), cr);
            startOfRange += jump;

        }
    }

    private void addRequestToSlave(SlaveInfo slaveInfo, SlaveCrackingRequest cr) {
        this.getRegisteredSlaves().get(slaveInfo).add(cr);
    }


}
