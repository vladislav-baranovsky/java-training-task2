package com.example.task2.entity.impl;

import com.example.task2.entity.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TextComposite implements TextComponent {
    private final List<TextComponent> children = new ArrayList<>();
    private final Type type;

    public TextComposite(Type type) {
        this.type = Objects.requireNonNull(type);
    }

    public void addChild(TextComponent child) {
        children.add(child);
    }

    @Override
    public List<TextComponent> getChildren() {
        return List.copyOf(children);
    }

    @Override
    public String restore() {
        return children.stream()
            .map(TextComponent::restore)
            .collect(Collectors.joining());
    }

    @Override
    public Type getType() {
        return type;
    }
}
