package com.example.task2.entity.impl;

import com.example.task2.entity.TextComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class TextLeafTest {

    @Test
    void shouldRestoreCharacterValue() {
        TextLeaf leaf = new TextLeaf(TextComponent.Type.CHARACTER, 'A');

        String restored = leaf.restore();

        assertEquals("A", restored);
    }

    @ParameterizedTest
    @EnumSource(TextComponent.Type.class)
    void shouldReturnCorrectType(TextComponent.Type expectedType) {
        TextLeaf leaf = new TextLeaf(expectedType, '!');

        assertEquals(expectedType, leaf.getType());
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull() {
        assertThrows(NullPointerException.class, () -> new TextLeaf(null, ' '));
    }

    @Test
    void shouldPreserveSpecialCharacters() {
        TextLeaf tabLeaf = new TextLeaf(TextComponent.Type.WHITESPACE, '\t');
        TextLeaf newlineLeaf = new TextLeaf(TextComponent.Type.WHITESPACE, '\n');

        assertAll(
            () -> assertEquals("\t", tabLeaf.restore()),
            () -> assertEquals("\n", newlineLeaf.restore())
        );
    }
}
