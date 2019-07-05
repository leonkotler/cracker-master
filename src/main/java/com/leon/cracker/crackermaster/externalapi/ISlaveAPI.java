package com.leon.cracker.crackermaster.externalapi;

import com.leon.cracker.crackermaster.models.SlaveCrackingRequest;
import com.leon.cracker.crackermaster.models.SlaveInfo;

import java.net.URI;

public interface ISlaveAPI {

    void sendCrackingRequestToSlave(SlaveInfo slaveInfo, SlaveCrackingRequest slaveCrackingRequest);
    boolean isSlaveUp(URI slaveUri);

}
