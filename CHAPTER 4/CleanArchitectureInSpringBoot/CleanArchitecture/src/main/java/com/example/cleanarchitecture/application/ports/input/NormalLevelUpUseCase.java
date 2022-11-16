package com.example.cleanarchitecture.application.ports.input;

import com.example.cleanarchitecture.application.ports.input.dto.LevelUpRequest;

public interface NormalLevelUpUseCase {
    long normalLevelUp(LevelUpRequest levelUpRequest);
}
