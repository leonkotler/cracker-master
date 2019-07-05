package com.leon.cracker.crackermaster.models;

public class FoundPasswordRequest {
    private String requestId;
    private String hash;
    private String password;

    public FoundPasswordRequest() {
    }

    public FoundPasswordRequest(String requestId, String hash, String password) {
        this.requestId = requestId;
        this.hash = hash;
        this.password = password;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "FoundPasswordRequest{" +
                "requestId='" + requestId + '\'' +
                ", hash='" + hash + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
