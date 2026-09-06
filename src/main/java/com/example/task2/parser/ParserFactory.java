package com.example.task2.parser;

public class ParserFactory {
    public TextParser create() {
        var lexemeParser = new LexemeParser();
        var sentenceParser = new SentenceParser(lexemeParser);
        var paragraphParser = new ParagraphParser(sentenceParser);

        return new TextParser(paragraphParser);
    }
}
