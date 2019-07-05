package com.leon.cracker.crackermaster.services.slaves;

import com.leon.cracker.crackermaster.models.SlaveInfo;

import java.net.URI;
import java.util.List;
import java.util.Set;

public interface ISlaveManagerService {
    void launchSlaves(int numOfSlaves);
    void registerSlave(SlaveInfo slaveInfo);
    Set<SlaveInfo> getRegisteredSlaves();
    boolean isSlaveUp(URI slaveUri);
    void removeSlave(SlaveInfo slaveInfo);
    void sendForCracking(List<String> hashes, String requestId);
}
