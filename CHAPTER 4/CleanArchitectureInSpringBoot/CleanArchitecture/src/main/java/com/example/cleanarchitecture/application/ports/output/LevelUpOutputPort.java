package com.example.cleanarchitecture.application.ports.output;

import com.example.cleanarchitecture.application.ports.input.dto.LevelUpRequest;

public interface LevelUpOutputPort {
    long normalLevelUp(LevelUpRequest levelUpRequest);

    long burningLevelUp(LevelUpRequest levelUpRequest);
}
