package com.leon.cracker.crackermaster.services.cracking;

import com.leon.cracker.crackermaster.models.FoundPasswordRequest;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class FileWritingService implements IFileWritingService {

    private static final Logger logger = LoggerFactory.getLogger(FileWritingService.class);
    private BlockingQueue<FoundPasswordRequest> writingRequestsQueue = new ArrayBlockingQueue<>(100);

    @Override
    public void writeToFile(FoundPasswordRequest foundPasswordRequest) {
        try {
            writingRequestsQueue.put(foundPasswordRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void postConstruct() {
        new Thread(() -> {
            while (true) {
                try {
                    FoundPasswordRequest foundPasswordRequest = writingRequestsQueue.take();
                    actuallyWriteToFile(foundPasswordRequest);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void actuallyWriteToFile(FoundPasswordRequest foundPasswordRequest) {

        String passwordsFile = "passwords-" + foundPasswordRequest.getRequestId() + ".txt";
        String passwordAndHash = foundPasswordRequest.getPassword() + " -> " + foundPasswordRequest.getHash() + "\n";

        try {

            logger.info("Writing {} to {}", passwordAndHash, passwordsFile);

            FileUtils.writeStringToFile(new File(passwordsFile), passwordAndHash, "UTF-8", true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
