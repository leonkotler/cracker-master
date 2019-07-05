package com.leon.cracker.crackermaster.services.cracking;

import com.leon.cracker.crackermaster.models.FoundPasswordRequest;

public interface ICrackingService {
    void crackHashes(String fileLocation, String requestId);

    void handleFoundPassword(FoundPasswordRequest foundPasswordRequest);
}
