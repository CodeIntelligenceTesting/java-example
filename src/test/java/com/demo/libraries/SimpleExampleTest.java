package com.demo.libraries;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import com.demo.api.SimpleAPIExample;
import org.junit.jupiter.api.Test;

public class SimpleExampleTest {

    /**
     * Unit Test for {@link SimpleExample#simpleExampleFunction}
     */
    @Test
    public void unitTestSimpleExampleFunctionDeveloper() {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.simpleExampleFunction(0, 10, "Developer");
    }

    /**
     * Unit Test for {@link SimpleExample#simpleExampleFunction}
     */
    @Test
    public void unitTestSimpleExampleFunctionMaintainer() {
        SimpleExample simpleExample = new SimpleExample();
        simpleExample.simpleExampleFunction(20, -10, "Maintainer");
    }

    /**
     * Simple example on how to use fuzz testing to check for vulnerabilities
     * Calling the example function with fuzzer generated inputs. If it crashes or
     * a bug detector finds something, we'll get notified.
     * Tests {@link SimpleExample#simpleExampleFunction}
     * @param data
     */
    @FuzzTest
    public void fuzzTestSimpleExampleFunction(FuzzedDataProvider data) {
        SimpleExample simpleExample = new SimpleExample();
        int a = data.consumeInt();
        int b = data.consumeInt();
        String c = data.consumeRemainingAsString();

        simpleExample.simpleExampleFunction(a, b, c);
    }
}
