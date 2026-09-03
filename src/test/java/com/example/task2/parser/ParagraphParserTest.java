package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import com.example.task2.entity.impl.TextComposite;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParagraphParserTest {
    @Test
    public void shouldCreateRootComponentWithCorrectType() {
        var paragraphParser = new ParagraphParser(new SentenceParserStub(null));

        TextComponent rootComponent = paragraphParser.createRootComponent();

        assertInstanceOf(TextComposite.class, rootComponent);
        assertEquals(TextComponent.Type.PARAGRAPH, rootComponent.getType());
    }

    @Test
    public void shouldSplitTextByWhitespaceAndPreserveWhitespace() {
        var paragraphParser = new ParagraphParser(new SentenceParserStub(null));

        List<String> tokens = paragraphParser.tokenize("Sentence 1. Sentence 2! Sentence 3?");

        assertAll(
            () -> assertEquals(3, tokens.size()),
            () -> assertEquals("Sentence 1. ", tokens.get(0)),
            () -> assertEquals("Sentence 2! ", tokens.get(1)),
            () -> assertEquals("Sentence 3?", tokens.get(2))
        );
    }

    @Test
    public void shouldCallNextParserOnEachTokenProcess() {
        var sentenceParser = new SentenceParserStub(null);
        var paragraphParser = new ParagraphParser(sentenceParser);


        paragraphParser.processToken("Sentence 1. ");
        paragraphParser.processToken("Sentence 2! ");
        paragraphParser.processToken("Sentence 3?");

        assertEquals(3, sentenceParser.processTokenMethodCalledTimes);
    }

    @Test
    public void shouldNotThrowWhenNextIsNull() {
        assertDoesNotThrow(() -> new ParagraphParser(null));
    }

    static class SentenceParserStub extends SentenceParser {
        public int processTokenMethodCalledTimes = 0;

        SentenceParserStub(LexemeParser next) {
            super(next);
        }

        @Override
        protected TextComponent createRootComponent() {
            processTokenMethodCalledTimes++;
            return super.createRootComponent();
        }

        @Override
        protected TextComponent processToken(String token) {
            return new TextComposite(TextComponent.Type.LEXEME);
        }
    }
}
