package com.example.task2.entity.impl;

import com.example.task2.entity.TextComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextCompositeTest {

    @Test
    void shouldRestoreEmptyComposite() {
        TextComposite composite = new TextComposite(TextComponent.Type.LEXEME);

        assertEquals("", composite.restore());
    }

    @Test
    void shouldRestoreNestedStructureInOrder() {
        TextComposite lexemeComposite = new TextComposite(TextComponent.Type.LEXEME);
        TextLeaf char1 = new TextLeaf(TextComponent.Type.CHARACTER, 'C');
        TextLeaf char2 = new TextLeaf(TextComponent.Type.CHARACTER, 'a');
        TextLeaf char3 = new TextLeaf(TextComponent.Type.CHARACTER, 't');

        lexemeComposite.addChild(char1);
        lexemeComposite.addChild(char2);
        lexemeComposite.addChild(char3);

        assertEquals("Cat", lexemeComposite.restore());
    }

    @Test
    void shouldHandleDeeplyNestedComposites() {
        TextComposite sentenceComposite = new TextComposite(TextComponent.Type.SENTENCE);

        TextComposite word1 = new TextComposite(TextComponent.Type.LEXEME);
        word1.addChild(new TextLeaf(TextComponent.Type.LEXEME, 'H'));
        word1.addChild(new TextLeaf(TextComponent.Type.LEXEME, 'i'));

        TextLeaf space = new TextLeaf(TextComponent.Type.WHITESPACE, ' ');

        TextComposite word2 = new TextComposite(TextComponent.Type.LEXEME);
        word2.addChild(new TextLeaf(TextComponent.Type.LEXEME, 'b'));
        word2.addChild(new TextLeaf(TextComponent.Type.LEXEME, 'y'));
        word2.addChild(new TextLeaf(TextComponent.Type.LEXEME, 'e'));

        sentenceComposite.addChild(word1);
        sentenceComposite.addChild(space);
        sentenceComposite.addChild(word2);

        assertEquals("Hi bye", sentenceComposite.restore());
    }

    @ParameterizedTest
    @EnumSource(TextComponent.Type.class)
    void shouldReturnCorrectType(TextComponent.Type expectedType) {
        TextComposite composite = new TextComposite(expectedType);

        assertEquals(expectedType, composite.getType());
    }

    @Test
    void shouldThrowNullPointerExceptionWhenTypeIsNull() {
        assertThrows(NullPointerException.class, () -> new TextComposite(null));
    }
}
