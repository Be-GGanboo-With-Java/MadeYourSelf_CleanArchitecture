package com.example.cleanarchitecture.infrastructure.adapters.in;

import com.example.cleanarchitecture.application.ports.input.BurningLevelUpUseCase;
import com.example.cleanarchitecture.application.ports.input.dto.LevelUpRequest;
import com.example.cleanarchitecture.application.ports.input.dto.LevelUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/burning_levelUp")
public class BurningLevelUpController {

    private final BurningLevelUpUseCase burningLevelUpUseCase;

    @GetMapping
    public LevelUpResponse burningLevelUpIn(@RequestBody LevelUpRequest levelUpRequest) {
        LevelUpResponse response = new LevelUpResponse();
        response.setFinalLevel(burningLevelUpUseCase.BurningLevelUp(levelUpRequest));
        return response;
    }
}
