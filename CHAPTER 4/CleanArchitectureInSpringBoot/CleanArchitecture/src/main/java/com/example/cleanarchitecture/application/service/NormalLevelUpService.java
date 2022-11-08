package com.example.cleanarchitecture.application.service;

import com.example.cleanarchitecture.application.ports.input.NormalLevelUpUseCase;
import com.example.cleanarchitecture.application.ports.input.dto.LevelUpRequest;
import com.example.cleanarchitecture.application.ports.output.LevelUpOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NormalLevelUpService implements NormalLevelUpUseCase {

    private final LevelUpOutputPort levelUpOutputport;

    @Override
    public long normalLevelUp(LevelUpRequest levelUpRequest) {
        return levelUpOutputport.normalLevelUp(levelUpRequest);
    }
}
