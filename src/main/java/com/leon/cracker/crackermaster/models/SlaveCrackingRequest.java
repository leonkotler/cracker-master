package com.leon.cracker.crackermaster.models;

import java.util.List;

public class SlaveCrackingRequest {

    private List<String> hashes;
    private String requestId;
    private int start;
    private int end;

    public SlaveCrackingRequest() {
    }

    public SlaveCrackingRequest(List<String> hashes, String requestId, int start, int end) {
        this.hashes = hashes;
        this.requestId = requestId;
        this.start = start;
        this.end = end;
    }

    public List<String> getHashes() {
        return hashes;
    }

    public void setHashes(List<String> hashes) {
        this.hashes = hashes;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "SlaveCrackingRequest{" +
                "hashes=" + hashes +
                ", requestId='" + requestId + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
