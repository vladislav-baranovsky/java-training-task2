package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import com.example.task2.entity.impl.TextComposite;
import com.example.task2.entity.impl.TextLeaf;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class SentenceParser extends AbstractParser {
    private static final String LEXEME_OR_SPACE_OR_PUNCTUATION_REGEX = "\\w+|\\s|\\p{P}";

    SentenceParser(LexemeParser next) {
        super(next);
    }

    @Override
    protected TextComponent createRootComponent() {
        return new TextComposite(TextComponent.Type.SENTENCE);
    }

    @Override
    protected List<String> tokenize(String text) {
        return Pattern.compile(LEXEME_OR_SPACE_OR_PUNCTUATION_REGEX, Pattern.UNICODE_CHARACTER_CLASS)
            .matcher(text)
            .results()
            .map(MatchResult::group)
            .toList();
    }

    @Override
    protected TextComponent processToken(String token) {
        char firstCharacter = token.charAt(0);

        if (Character.isLetterOrDigit(firstCharacter)) {
            return next.parse(token);
        }

        if (Character.isWhitespace(firstCharacter)) {
            return new TextLeaf(TextComponent.Type.WHITESPACE, firstCharacter);
        }

        return new TextLeaf(TextComponent.Type.PUNCTUATION, firstCharacter);
    }
}
