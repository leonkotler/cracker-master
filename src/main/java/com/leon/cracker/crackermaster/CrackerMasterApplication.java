package com.leon.cracker.crackermaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class CrackerMasterApplication {

    public static void main(String[] args) {

        validateArguments(args);

        SpringApplication.run(CrackerMasterApplication.class, args);
    }

    private static void validateArguments(String[] args) {
        if (args.length < 2 || !args[0].equals("-slaves")){
            System.out.println("Please provide number of slaves: -slave <n>");
            System.exit(1);
        }
        else {
            validateNumOfSlaves(args[1]);
        }
    }

    private static void validateNumOfSlaves(String arg) {

        try {
            Integer.parseInt(arg);

        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid number of slaves");
            System.exit(1);
        }
    }

}
