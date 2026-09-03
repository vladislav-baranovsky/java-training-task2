package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import com.example.task2.entity.impl.TextComposite;
import com.example.task2.entity.impl.TextLeaf;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LexemeParserTest {
    @Test
    public void shouldCreateRootComponentWithCorrectType() {
        var lexemeParser = new LexemeParser();

        TextComponent rootComponent = lexemeParser.createRootComponent();

        assertInstanceOf(TextComposite.class, rootComponent);
        assertEquals(TextComponent.Type.LEXEME, rootComponent.getType());
    }

    @Test
    public void shouldSplitWordIntoSingleCharacters() {
        var lexemeParser = new LexemeParser();

        List<String> tokens = lexemeParser.tokenize("Cat");

        assertAll(
            () -> assertEquals(3, tokens.size()),
            () -> assertEquals("C", tokens.get(0)),
            () -> assertEquals("a", tokens.get(1)),
            () -> assertEquals("t", tokens.get(2))
        );
    }

    @Test
    public void shouldProcessTokensIntoLeafsWithCorrectType() {
        var sentenceParser = new LexemeParser();

        TextComponent textComponent = sentenceParser.processToken("A");

        assertAll(
            () -> assertInstanceOf(TextLeaf.class, textComponent),
            () -> assertEquals(TextComponent.Type.CHARACTER, textComponent.getType())
        );
    }
}
