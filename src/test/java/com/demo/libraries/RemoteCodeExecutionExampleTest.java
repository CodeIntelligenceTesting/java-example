package com.demo.libraries;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import com.demo.libraries.RemoteCodeExecutionExample.Book;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static com.demo.libraries.RemoteCodeExecutionExample.*;
import static com.demo.libraries.RemoteCodeExecutionExample.serialize;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoteCodeExecutionExampleTest {

    @Test
    public void unitTest() throws IOException {
        Book book = new Book("Title", "Author");

        byte[] serializedBook = serialize(book).toByteArray();
        Book newBook = deserialize(new ByteArrayInputStream(serializedBook));

        assertEquals(book, newBook, "Deserialized object does not match input");
    }

    /**
     * Simple example showing the RCE sanitizer and showing how it can find RCEs even when the Exceptions
     * are suppressed.
     * @param data
     */
    @FuzzTest
    public void myFuzzTest(FuzzedDataProvider data) {
        try {
            deserialize(new ByteArrayInputStream(data.consumeRemainingAsBytes()));
        } catch (Exception ignored) {
            // We can ignore all exception as the RCE will be caught by Jazzer
        }
    }
}
