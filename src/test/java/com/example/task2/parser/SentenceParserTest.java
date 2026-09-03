package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import com.example.task2.entity.impl.TextComposite;
import com.example.task2.entity.impl.TextLeaf;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SentenceParserTest {
    @Test
    public void shouldCreateRootComponentWithCorrectType() {
        var sentenceParser = new SentenceParser(new LexemeParserStub());

        TextComponent rootComponent = sentenceParser.createRootComponent();

        assertInstanceOf(TextComposite.class, rootComponent);
        assertEquals(TextComponent.Type.SENTENCE, rootComponent.getType());
    }

    @Test
    public void shouldSplitSentenceIntoWordsPunctuationWhitespace() {
        var sentenceParser = new SentenceParser(new LexemeParserStub());

        List<String> tokens = sentenceParser.tokenize("FirstWord, secondWord thirdWord.");

        assertAll(
            () -> assertEquals(7, tokens.size()),
            () -> assertEquals("FirstWord", tokens.get(0)),
            () -> assertEquals(",", tokens.get(1)),
            () -> assertEquals(" ", tokens.get(2)),
            () -> assertEquals("secondWord", tokens.get(3)),
            () -> assertEquals(" ", tokens.get(4)),
            () -> assertEquals("thirdWord", tokens.get(5)),
            () -> assertEquals(".", tokens.get(6))
        );
    }

    @Test
    public void shouldCallNextParserOnlyOnLexemeTokens() {
        var lexemeParser = new LexemeParserStub();
        var sentenceParser = new SentenceParser(lexemeParser);

        sentenceParser.processToken("FirstWord");
        sentenceParser.processToken(",");
        sentenceParser.processToken(" ");
        sentenceParser.processToken("secondWord");
        sentenceParser.processToken(".");

        assertEquals(2, lexemeParser.processTokenMethodCalledTimes);
    }

    @Test
    public void shouldProcessTokensIntoLexemeAndWhitespaceOrPunctuationIntoLeafsWithCorrectType() {
        var lexemeParser = new LexemeParserStub();
        var sentenceParser = new SentenceParser(lexemeParser);

        TextComponent textComponent1 = sentenceParser.processToken("Word");
        TextComponent textComponent2 = sentenceParser.processToken(" ");
        TextComponent textComponent3 = sentenceParser.processToken(",");

        assertAll(
            () -> assertInstanceOf(TextComposite.class, textComponent1),
            () -> assertInstanceOf(TextLeaf.class, textComponent2),
            () -> assertInstanceOf(TextLeaf.class, textComponent3),
            () -> assertEquals(TextComponent.Type.LEXEME, textComponent1.getType()),
            () -> assertEquals(TextComponent.Type.WHITESPACE, textComponent2.getType()),
            () -> assertEquals(TextComponent.Type.PUNCTUATION, textComponent3.getType())
        );
    }

    @Test
    public void shouldNotThrowWhenNextIsNull() {
        assertDoesNotThrow(() -> new SentenceParser(null));
    }

    static class LexemeParserStub extends LexemeParser {
        public int processTokenMethodCalledTimes = 0;

        LexemeParserStub() {
            super();
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
