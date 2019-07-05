package com.leon.cracker.crackermaster.services.cracking;

import com.leon.cracker.crackermaster.models.FoundPasswordRequest;

public interface IFileWritingService  {
    void writeToFile(FoundPasswordRequest foundPasswordRequest);
}
