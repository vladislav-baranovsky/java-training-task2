package com.example.task2.entity.impl;

import com.example.task2.entity.TextComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

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
    void shouldThrowExceptionOnChildrenMethodCalls() {
        TextLeaf leaf = new TextLeaf(TextComponent.Type.CHARACTER, 'a');

        assertAll(
            () -> assertThrows(UnsupportedOperationException.class, () -> leaf.addChild(new TextLeaf(TextComponent.Type.CHARACTER, 'b'))),
            () -> assertThrows(UnsupportedOperationException.class, () -> leaf.setChildren(List.of())),
            () -> assertThrows(UnsupportedOperationException.class, leaf::getChildren)
        );
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
