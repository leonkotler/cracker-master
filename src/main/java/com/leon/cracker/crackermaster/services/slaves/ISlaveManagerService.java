package com.leon.cracker.crackermaster.services.slaves;

import com.leon.cracker.crackermaster.models.SlaveCrackingRequest;
import com.leon.cracker.crackermaster.models.SlaveDoneRequest;
import com.leon.cracker.crackermaster.models.SlaveInfo;

import java.net.URI;
import java.util.List;
import java.util.Map;

public interface ISlaveManagerService {
    void launchSlaves(int numOfSlaves);
    void registerSlave(SlaveInfo slaveInfo);
    Map<SlaveInfo, List<SlaveCrackingRequest>> getRegisteredSlaves();
    boolean isSlaveUp(URI slaveUri);
    void removeSlave(SlaveInfo slaveInfo);
    void sendForCracking(SlaveCrackingRequest slaveCrackingRequest);

    void handleSlaveDoneRequest(SlaveDoneRequest slaveDoneRequest);
}
