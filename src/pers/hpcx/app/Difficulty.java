package pers.hpcx.app;

import lombok.Getter;

public enum Difficulty {
    
    EASY("简单"),
    NORMAL("正常"),
    DIFFICULT("困难"),
    HELL("地狱");
    
    @Getter private final String description;
    
    Difficulty(String description) {
        this.description = description;
    }
}
