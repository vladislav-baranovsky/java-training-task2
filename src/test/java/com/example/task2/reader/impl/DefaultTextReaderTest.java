package com.example.task2.reader.impl;

import com.example.task2.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultTextReaderTest {

    private DefaultTextReader textReader;

    @BeforeEach
    void setUp() {
        textReader = new DefaultTextReader();
    }

    @Test
    void shouldReturnFileContent() throws CustomException {
        String validPath = "text.txt";

        String result = textReader.read(validPath);

        assertNotNull(result);
        assertEquals("Hello, World!", result);
    }

    @Test
    void shouldThrowsCustomExceptionWhenFileDoesntExist() {
        String missingPath = "non-existent-file.txt";

        CustomException exception = assertThrows(CustomException.class, () -> textReader.read(missingPath));

        assertTrue(exception.getMessage().contains("Resource file not found"));
    }

    @Test
    void shouldThrowsNullPointerExceptionWhenPathIsNull() {
        assertThrows(NullPointerException.class, () -> textReader.read(null));
    }
}
