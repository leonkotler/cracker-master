package com.leon.cracker.crackermaster.services.cracking;

import com.leon.cracker.crackermaster.services.slaves.ISlaveManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

@Component
public class CrackingService implements ICrackingService {

    private ISlaveManagerService slaveManagerService;

    @Autowired
    public void setSlaveManagerService(ISlaveManagerService slaveManagerService) {
        this.slaveManagerService = slaveManagerService;
    }

    @Override
    public void crackHashes(String fileLocation, String requestId) {
        slaveManagerService.sendForCracking(extractHashesFromFile(fileLocation), requestId);
    }

    private List<String> extractHashesFromFile(String fileLocation){
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
