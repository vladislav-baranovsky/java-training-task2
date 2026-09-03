package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import com.example.task2.entity.impl.TextComposite;
import com.example.task2.entity.impl.TextLeaf;

import java.util.Arrays;
import java.util.List;

public class LexemeParser extends AbstractParser {
    LexemeParser() {
        super(null);
    }

    @Override
    protected TextComponent createRootComponent() {
        return new TextComposite(TextComponent.Type.LEXEME);
    }

    @Override
    protected List<String> tokenize(String text) {
        int length = text.length();
        String[] tokens = new String[length];

        for (int i = 0; i < length; i++) {
            tokens[i] = String.valueOf(text.charAt(i));
        }

        return Arrays.asList(tokens);
    }

    @Override
    protected TextComponent processToken(String token) {
        return new TextLeaf(TextComponent.Type.CHARACTER, token.charAt(0));
    }
}
