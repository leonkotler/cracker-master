package com.leon.cracker.crackermaster.models;

public class SlaveDoneRequest {

    private SlaveInfo slaveInfo;
    private SlaveCrackingRequest slaveCrackingRequest;

    public SlaveDoneRequest() {
    }

    public SlaveDoneRequest(SlaveInfo slaveInfo, SlaveCrackingRequest slaveCrackingRequest) {
        this.slaveInfo = slaveInfo;
        this.slaveCrackingRequest = slaveCrackingRequest;
    }

    public SlaveInfo getSlaveInfo() {
        return slaveInfo;
    }

    public void setSlaveInfo(SlaveInfo slaveInfo) {
        this.slaveInfo = slaveInfo;
    }

    public SlaveCrackingRequest getSlaveCrackingRequest() {
        return slaveCrackingRequest;
    }

    public void setSlaveCrackingRequest(SlaveCrackingRequest slaveCrackingRequest) {
        this.slaveCrackingRequest = slaveCrackingRequest;
    }

    @Override
    public String toString() {
        return "SlaveDoneRequest{" +
                "slaveInfo=" + slaveInfo +
                ", slaveCrackingRequest=" + slaveCrackingRequest +
                '}';
    }
}
