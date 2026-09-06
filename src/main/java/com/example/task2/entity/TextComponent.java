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

    void setChildren(List<TextComponent> children);

    List<TextComponent> getChildren();

    default boolean isText() {
        return getType() == Type.TEXT;
    }

    default boolean isLexeme() {
        return getType() == Type.LEXEME;
    }
}
