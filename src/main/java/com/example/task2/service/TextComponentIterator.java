package com.example.task2.service;

import com.example.task2.entity.TextComponent;

import java.util.*;

final public class TextComponentIterator implements Iterator<TextComponent> {
    private final Queue<TextComponent> nodes = new ArrayDeque<>();

    private TextComponentIterator(TextComponent root, TextComponent.Type targetType) {
        findNodes(Objects.requireNonNull(root), Objects.requireNonNull(targetType));
    }

    public static TextComponentIterator sentenceIterator(TextComponent root) {
        return new TextComponentIterator(root, TextComponent.Type.SENTENCE);
    }

    private void findNodes(TextComponent current, TextComponent.Type targetType) {
        if (current.getType() == targetType) {
            nodes.add(current);
        }

        for (TextComponent children : current.getChildren()) {
            findNodes(children, targetType);
        }
    }

    @Override
    public boolean hasNext() {
        return !nodes.isEmpty();
    }

    @Override
    public TextComponent next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return nodes.poll();
    }
}
