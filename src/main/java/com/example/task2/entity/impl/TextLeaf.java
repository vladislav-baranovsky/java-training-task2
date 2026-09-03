package com.example.task2.entity.impl;

import com.example.task2.entity.TextComponent;

import java.util.List;
import java.util.Objects;

public class TextLeaf implements TextComponent {
    private final Type type;
    private final char value;

    public TextLeaf(Type type, char value) {
        this.type = Objects.requireNonNull(type);
        this.value = value;
    }

    @Override
    public String restore() {
        return String.valueOf(value);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void addChild(TextComponent child) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TextComponent> getChildren() {
        throw new UnsupportedOperationException();
    }
}
