package com.leon.cracker.crackermaster.services.slaves;

import com.leon.cracker.crackermaster.models.SlaveInfo;

import java.net.URI;
import java.util.Set;

public interface SlaveManagerService {
    void launchSlaves(int numOfSlaves);
    void registerSlave(SlaveInfo slaveInfo);
    Set<SlaveInfo> getRegisteredSlaves();
    boolean isSlaveUp(URI slaveUri);
}
