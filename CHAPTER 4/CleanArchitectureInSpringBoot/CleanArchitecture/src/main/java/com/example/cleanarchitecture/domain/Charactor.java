package com.example.cleanarchitecture.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Charactor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int level;
    @Column
    private String nickname;
    @Column
    private String job;

    public void levelUp() {
        this.level += 1;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }

    public void changeJob(String newJob) {
        this.job = newJob;
    }
}
