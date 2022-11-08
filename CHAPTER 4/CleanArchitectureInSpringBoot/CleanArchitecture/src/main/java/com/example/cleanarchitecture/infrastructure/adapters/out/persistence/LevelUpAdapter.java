package com.example.cleanarchitecture.infrastructure.adapters.out.persistence;

import com.example.cleanarchitecture.application.ports.input.dto.LevelUpRequest;
import com.example.cleanarchitecture.application.ports.output.LevelUpOutputPort;
import com.example.cleanarchitecture.domain.Charactor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class LevelUpAdapter implements LevelUpOutputPort {

    private final CharacterRepository characterRepository;

    @Override
    public long normalLevelUp(LevelUpRequest levelUpRequest) {
        Charactor normalCharactor = characterRepository.findById(levelUpRequest.getId())
                .orElseThrow(IllegalArgumentException::new);

        normalCharactor.levelUp();

        return normalCharactor.getLevel();
    }

    @Override
    public long burningLevelUp(LevelUpRequest levelUpRequest) {
        Charactor burningCharactor = characterRepository.findById(levelUpRequest.getId())
                .orElseThrow(IllegalArgumentException::new);

        burningCharactor.levelUp();
        burningCharactor.levelUp();
        burningCharactor.levelUp();

        characterRepository.save(burningCharactor);

        return burningCharactor.getLevel();
    }
}
