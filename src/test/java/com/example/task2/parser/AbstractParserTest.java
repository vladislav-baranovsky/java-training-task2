package com.example.task2.parser;

import com.example.task2.entity.TextComponent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractParserTest {
    @Test
    public void shouldSuccessfullyParseProvidedInputAndReturnExpectedResult() {
        String inputText = "Test1 test2";
        List<String> expectedTokens = List.of("Test1", " ", "test2");
        var expectedRoot = new TextComponentStub(TextComponent.Type.SENTENCE);
        var textComponent1 = new TextComponentStub(TextComponent.Type.LEXEME);
        var textComponent2 = new TextComponentStub(TextComponent.Type.WHITESPACE);
        var textComponent3 = new TextComponentStub(TextComponent.Type.LEXEME);

        Map<String, TextComponentStub> map = Map.of("Test1", textComponent1, " ", textComponent2, "test2", textComponent3);

        var parser = new AbstractParserStub(null, expectedRoot, expectedTokens, map);

        TextComponent textComponent = parser.parse(inputText);
        List<TextComponent> children = textComponent.getChildren();

        assertAll(
            () -> assertEquals(expectedRoot, textComponent),
            () -> assertEquals(3, children.size()),
            () -> assertEquals(textComponent1, children.get(0)),
            () -> assertEquals(textComponent2, children.get(1)),
            () -> assertEquals(textComponent3, children.get(2))
        );
    }

    @Test
    public void shouldSuccessfullyParseEmptyInputAndReturnEmptyRootTextComponent() {
        var root = new TextComponentStub(TextComponent.Type.TEXT);
        var parser = new AbstractParserStub(null, root, List.of(), null);
        TextComponent textComponent = parser.parse("");

        assertAll(
            () -> assertEquals(root, textComponent),
            () -> assertEquals(0, textComponent.getChildren().size())
        );
    }

    @Test
    public void shouldAllowCreatingInstanceWithNextAsNull() {
        var parser = new AbstractParserStub(null, null, null, null);

        assertNull(parser.next);
    }

    @Test
    public void shouldEncapsulateProvidedNextInstance() {
        var expectedNext = new AbstractParserStub(null, null, null, null);
        var parser = new AbstractParserStub(expectedNext, null, null, null);

        assertEquals(expectedNext, parser.next);
    }

    static class AbstractParserStub extends AbstractParser {
        private final TextComponent root;
        private final List<String> tokens;
        private final Map<String, TextComponentStub> tokenToTextComponentMap;

        public AbstractParserStub(
            AbstractParser next,
            TextComponent root,
            List<String> tokens,
            Map<String, TextComponentStub> tokenToTextComponentMap
        ) {
            super(next);

            this.root = root;
            this.tokens = tokens;
            this.tokenToTextComponentMap = tokenToTextComponentMap;
        }

        @Override
        protected TextComponent createRootComponent() {
            return root;
        }

        @Override
        protected List<String> tokenize(String text) {
            return tokens;
        }

        @Override
        protected TextComponent processToken(String token) {
            return tokenToTextComponentMap.get(token);
        }
    }

    static class TextComponentStub implements TextComponent {
        private final TextComponent.Type type;
        private final List<TextComponent> children = new ArrayList<>();

        public TextComponentStub(TextComponent.Type type) {
            this.type = type;
        }

        @Override
        public String restore() {
            return null;
        }

        @Override
        public Type getType() {
            return type;
        }

        @Override
        public void addChild(TextComponent child) {
            this.children.add(child);
        }

        @Override
        public void setChildren(List<TextComponent> children) {
        }

        @Override
        public List<TextComponent> getChildren() {
            return children;
        }
    }
}
