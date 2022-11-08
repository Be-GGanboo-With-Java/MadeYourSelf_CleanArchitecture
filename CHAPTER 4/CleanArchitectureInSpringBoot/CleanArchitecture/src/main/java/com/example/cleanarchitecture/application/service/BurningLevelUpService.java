package com.example.cleanarchitecture.application.service;

import com.example.cleanarchitecture.application.ports.input.BurningLevelUpUseCase;
import com.example.cleanarchitecture.application.ports.input.dto.LevelUpRequest;
import com.example.cleanarchitecture.application.ports.output.LevelUpOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BurningLevelUpService implements BurningLevelUpUseCase {

    private final LevelUpOutputPort levelUpOutputport;

    @Override
    public long BurningLevelUp(LevelUpRequest levelUpRequest) {
        return levelUpOutputport.burningLevelUp(levelUpRequest);
    }
}
