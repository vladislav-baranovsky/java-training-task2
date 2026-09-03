package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import com.example.task2.entity.impl.TextComposite;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextParserTest {
    @Test
    public void shouldCreateRootComponentWithCorrectType() {
        var textParser = new TextParser(new ParagraphParserStub(null));

        TextComponent rootComponent = textParser.createRootComponent();

        assertInstanceOf(TextComposite.class, rootComponent);
        assertEquals(TextComponent.Type.TEXT, rootComponent.getType());
    }

    @Test
    public void shouldSplitTextByNewLinesAndPreserveNewLineCharacters() {
        var textParser = new TextParser(new ParagraphParserStub(null));

        List<String> tokens = textParser.tokenize("Paragraph 1.\nParagraph 2.\nParagraph 3.");

        assertAll(
            () -> assertEquals(3, tokens.size()),
            () -> assertEquals("Paragraph 1.\n", tokens.get(0)),
            () -> assertEquals("Paragraph 2.\n", tokens.get(1)),
            () -> assertEquals("Paragraph 3.", tokens.get(2))
        );
    }

    @Test
    public void shouldCallNextParserOnEachTokenProcess() {
        var paragraphParser = new ParagraphParserStub(null);
        var textParser = new TextParser(paragraphParser);

        textParser.processToken("Paragraph 1.\n");
        textParser.processToken("Paragraph 2.\n");
        textParser.processToken("Paragraph 3.");

        assertEquals(3, paragraphParser.processTokenMethodCalledTimes);
    }

    @Test
    public void shouldNotThrowWhenNextIsNull() {
        assertDoesNotThrow(() -> new TextParser(null));
    }

    static class ParagraphParserStub extends ParagraphParser {
        public int processTokenMethodCalledTimes = 0;

        ParagraphParserStub(SentenceParser next) {
            super(next);
        }

        @Override
        protected TextComponent createRootComponent() {
            processTokenMethodCalledTimes++;
            return new TextComposite(TextComponent.Type.TEXT);
        }

        @Override
        protected TextComponent processToken(String token) {
            return new TextComposite(TextComponent.Type.PARAGRAPH);
        }
    }
}
