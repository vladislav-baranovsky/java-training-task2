package com.example.task2.reader.impl;

import com.example.task2.exception.CustomException;
import com.example.task2.reader.TextReader;

import java.io.IOException;
import java.io.InputStream;

public class DefaultTextReader implements TextReader {
    @Override
    public String read(String path) throws CustomException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new CustomException(String.format("Resource file not found: %s", path));
            }

            return new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new CustomException("Could not read bytes from stream", e);
        }
    }
}
