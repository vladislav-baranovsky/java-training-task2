package com.example.task2.entity;

import java.util.List;

public interface TextComponent {
    enum Type {
        TEXT,
        PARAGRAPH,
        SENTENCE,
        LEXEME,
        CHARACTER,
        PUNCTUATION,
        WHITESPACE,
    }

    String restore();
    Type getType();
    void addChild(TextComponent child);
    List<TextComponent> getChildren();
}
