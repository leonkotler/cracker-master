package com.leon.cracker.crackermaster.services.cracking;

import com.leon.cracker.crackermaster.models.FoundPasswordRequest;
import com.leon.cracker.crackermaster.models.SlaveCrackingRequest;
import com.leon.cracker.crackermaster.services.slaves.ISlaveManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CrackingService implements ICrackingService {

    private ISlaveManagerService slaveManagerService;
    private IFileWritingService fileWritingService;
    private final int defaultStartOfRange = 500000000;
    private final int defaultEndOfRange =   599999999;

    @Autowired
    public void setSlaveManagerService(ISlaveManagerService slaveManagerService) {
        this.slaveManagerService = slaveManagerService;
    }

    @Autowired
    public void setFileWritingService(IFileWritingService fileWritingService) {
        this.fileWritingService = fileWritingService;
    }

    @Override
    public void crackHashes(String fileLocation, String requestId) {
        SlaveCrackingRequest slaveCrackingRequest =
                new SlaveCrackingRequest(extractHashesFromFile(fileLocation), requestId, defaultStartOfRange, defaultEndOfRange);
        slaveManagerService.sendForCracking(slaveCrackingRequest);
    }

    @Override
    public void handleFoundPassword(FoundPasswordRequest foundPasswordRequest) {
        fileWritingService.writeToFile(foundPasswordRequest);
    }

    private List<String> extractHashesFromFile(String fileLocation) {
        List<String> hashes = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            String hash;

            while ((hash = reader.readLine()) != null) {
                hashes.add(hash);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return hashes;
    }
}
