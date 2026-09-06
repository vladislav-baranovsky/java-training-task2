package com.example.task2.service;

import com.example.task2.entity.TextComponent;

import java.util.*;
import java.util.stream.IntStream;

public class TextComponentService {
    public void swapFirstAndLastLexeme(TextComponent textComponent) {
        if (textComponent == null || !textComponent.isText()) {
            return;
        }

        TextComponentIterator iterator = TextComponentIterator.sentenceIterator(textComponent);

        while (iterator.hasNext()) {
            TextComponent sentenceComponent = iterator.next();

            List<TextComponent> children = sentenceComponent.getChildren();

            int[] lexemeIndices = IntStream.range(0, children.size())
                .filter((int i) -> children.get(i).isLexeme())
                .toArray();

            if (lexemeIndices.length > 1) {
                Collections.swap(children, lexemeIndices[0], lexemeIndices[lexemeIndices.length - 1]);

                sentenceComponent.setChildren(children);
            }
        }
    }

    public List<TextComponent> getSentencesOrderedByCharacterCount(TextComponent textComponent, char targetCharacter) {
        if (textComponent == null || !textComponent.isText()) {
            return List.of();
        }

        List<TextComponent> sentenceComponents = new ArrayList<>();

        TextComponentIterator.sentenceIterator(textComponent)
            .forEachRemaining(sentenceComponents::add);

        sentenceComponents.sort(
            Comparator.comparingLong(
                (TextComponent c) -> c.restore()
                    .chars()
                    .filter(character -> character == targetCharacter)
                    .count()
            )
        );

        return sentenceComponents;
    }

    public int countSentencesWithIdenticalLexemes(TextComponent textComponent) {
        if (textComponent == null || !textComponent.isText()) {
            return 0;
        }

        List<TextComponent> sentenceComponents = new ArrayList<>();
        TextComponentIterator.sentenceIterator(textComponent)
            .forEachRemaining(sentenceComponents::add);

        Map<String, Set<Integer>> lexemeToIndices = new HashMap<>();
        for (int i = 0; i < sentenceComponents.size(); i++) {
            TextComponent sentenceComponent = sentenceComponents.get(i);
            List<TextComponent> children = sentenceComponent.getChildren();

            for (TextComponent child : children) {
                if (!child.isLexeme()) {
                    continue;
                }

                String restoredLexemeText = child.restore().toLowerCase(Locale.ROOT);
                lexemeToIndices.computeIfAbsent(restoredLexemeText, _ -> new HashSet<>()).add(i);
            }
        }

        Set<Integer> intersectingIndices = new HashSet<>();
        for (Set<Integer> indices : lexemeToIndices.values()) {
            if (indices.size() > 1) {
                intersectingIndices.addAll(indices);
            }
        }

        return intersectingIndices.size();
    }
}
