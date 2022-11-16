package com.example.cleanarchitecture.infrastructure.adapters.in;

import com.example.cleanarchitecture.application.ports.input.NormalLevelUpUseCase;
import com.example.cleanarchitecture.application.ports.input.dto.LevelUpRequest;
import com.example.cleanarchitecture.application.ports.input.dto.LevelUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/normal_levelUp")
public class NormalLevelUpController {

    private final NormalLevelUpUseCase normalLevelUpUseCase;

    @GetMapping
    public LevelUpResponse normalLevelUpIn(@RequestBody LevelUpRequest levelUpRequest) {
        LevelUpResponse response = new LevelUpResponse();
        response.setFinalLevel(normalLevelUpUseCase.normalLevelUp(levelUpRequest));
        return response;
    }
}
