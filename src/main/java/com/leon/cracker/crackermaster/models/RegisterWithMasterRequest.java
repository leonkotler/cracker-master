package com.leon.cracker.crackermaster.models;

import java.util.List;

public class RegisterWithMasterRequest {

    private SlaveInfo slaveInfo;
    private List<SlaveCrackingRequest> currentRequests;

    public RegisterWithMasterRequest() {
    }

    public RegisterWithMasterRequest(SlaveInfo slaveInfo, List<SlaveCrackingRequest> currentRequests) {
        this.slaveInfo = slaveInfo;
        this.currentRequests = currentRequests;
    }

    public SlaveInfo getSlaveInfo() {
        return slaveInfo;
    }

    public void setSlaveInfo(SlaveInfo slaveInfo) {
        this.slaveInfo = slaveInfo;
    }

    public List<SlaveCrackingRequest> getCurrentRequests() {
        return currentRequests;
    }

    public void setCurrentRequests(List<SlaveCrackingRequest> currentRequests) {
        this.currentRequests = currentRequests;
    }

    @Override
    public String toString() {
        return "RegisterWithMasterRequest{" +
                "slaveInfo=" + slaveInfo +
                ", currentRequests=" + currentRequests +
                '}';
    }
}
