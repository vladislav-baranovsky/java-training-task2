package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import com.example.task2.entity.impl.TextComposite;

import java.util.Arrays;
import java.util.List;

public class TextParser extends AbstractParser {
    private static final String PARAGRAPH_REGEX = "(?<=\\R|$)";

    TextParser(ParagraphParser next) {
        super(next);
    }

    @Override
    protected TextComponent createRootComponent() {
        return new TextComposite(TextComponent.Type.TEXT);
    }

    @Override
    protected List<String> tokenize(String text) {
        return Arrays.asList(text.split(PARAGRAPH_REGEX));
    }

    @Override
    protected TextComponent processToken(String token) {
        return next.parse(token);
    }
}
