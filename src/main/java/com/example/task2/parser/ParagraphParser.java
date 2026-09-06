package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import com.example.task2.entity.impl.TextComposite;

import java.util.Arrays;
import java.util.List;

public class ParagraphParser extends AbstractParser {
    private static final String SENTENCE_REGEX = "(?<=[?.!]\\s)";

    ParagraphParser(SentenceParser next) {
        super(next);
    }

    @Override
    protected TextComponent createRootComponent() {
        return new TextComposite(TextComponent.Type.PARAGRAPH);
    }

    @Override
    protected List<String> tokenize(String text) {
        return Arrays.asList(text.split(SENTENCE_REGEX));
    }

    @Override
    protected TextComponent processToken(String token) {
        return next.parse(token);
    }
}
