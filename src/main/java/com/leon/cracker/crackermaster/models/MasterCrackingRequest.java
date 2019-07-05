package com.leon.cracker.crackermaster.models;

import io.micrometer.core.lang.NonNull;

public class MasterCrackingRequest {

    @NonNull
    private String fileLocation;

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public String toString() {
        return "MasterCrackingRequest{" +
                "fileLocation='" + fileLocation + '\'' +
                '}';
    }
}
