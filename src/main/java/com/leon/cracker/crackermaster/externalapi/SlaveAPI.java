package com.leon.cracker.crackermaster.externalapi;

import com.leon.cracker.crackermaster.externalapi.ISlaveAPI;
import com.leon.cracker.crackermaster.models.SlaveCrackingRequest;
import com.leon.cracker.crackermaster.models.SlaveInfo;
import com.leon.cracker.crackermaster.monitor.HealthStatus;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class SlaveAPI implements ISlaveAPI {

    @Override
    @Async
    public void sendCrackingRequestToSlave(SlaveInfo slaveInfo, SlaveCrackingRequest slaveCrackingRequest) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(slaveInfo.getURI() + "/slave/crack", slaveCrackingRequest, String.class);
    }

    @Override
    public boolean isSlaveUp(URI slaveUri) {
        RestTemplate restTemplate = new RestTemplate();

        try {

            return restTemplate
                    .getForEntity(slaveUri + "/actuator/health", HealthStatus.class)
                    .getStatusCode()
                    .equals(HttpStatus.OK);

        } catch (ResourceAccessException exception) {
            return false;
        }
    }
}
