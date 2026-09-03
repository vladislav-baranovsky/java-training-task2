package com.example.task2.parser;

import com.example.task2.entity.TextComponent;

import java.util.List;

public abstract class AbstractParser {
    protected final AbstractParser next;

    AbstractParser(AbstractParser next) {
        this.next = next;
    }

    public final TextComponent parse(String text) {
        TextComponent component = createRootComponent();

        List<String> tokens = tokenize(text);
        for (String token : tokens) {
            TextComponent child = processToken(token);
            component.addChild(child);
        }

        return component;
    }

    protected abstract TextComponent createRootComponent();

    protected abstract List<String> tokenize(String text);

    protected abstract TextComponent processToken(String token);

}
