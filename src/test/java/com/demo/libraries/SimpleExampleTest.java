package com.demo.libraries;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class SimpleExampleTest {

    @Test
    public void unitTestDeveloper() {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.simpleExampleFunction(0, 10, "Developer");
    }

    @Test
    public void unitTestMaintainer() {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.simpleExampleFunction(20, -10, "Maintainer");
    }

    /**
     * Simple example on how to use fuzz testing to check for vulnerabilities
     * Calling the example function with fuzzer generated inputs. If it crashes or
     * a bug detector finds something, we'll get notified.
     * @param data
     */
    @FuzzTest
    public void myFuzzTest(FuzzedDataProvider data) {
        SimpleExample simpleExample = new SimpleExample();
        int a = data.consumeInt();
        int b = data.consumeInt();
        String c = data.consumeRemainingAsString();

        simpleExample.simpleExampleFunction(a, b, c);
    }
}
