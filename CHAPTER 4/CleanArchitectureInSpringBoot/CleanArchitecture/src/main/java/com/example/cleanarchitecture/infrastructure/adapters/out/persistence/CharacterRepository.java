package com.example.cleanarchitecture.infrastructure.adapters.out.persistence;

import com.example.cleanarchitecture.domain.Charactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Charactor, Long> {

}
