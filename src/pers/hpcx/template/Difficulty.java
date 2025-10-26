package pers.hpcx.template;

import lombok.Getter;

public enum Difficulty {
    
    EASY("简单"),
    NORMAL("普通"),
    HARD("困难");
    
    @Getter private final String description;
    
    Difficulty(String description) {
        this.description = description;
    }
    
    public GameTemplate newTemplate() {
        return switch (this) {
            case EASY -> new GameEasyTemplate();
            case NORMAL -> new GameNormalTemplate();
            case HARD -> new GameHardTemplate();
        };
    }
}
