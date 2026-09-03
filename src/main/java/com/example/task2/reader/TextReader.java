package com.example.task2.reader;

import com.example.task2.exception.CustomException;

public interface TextReader {
    String read(String path) throws CustomException;
}
