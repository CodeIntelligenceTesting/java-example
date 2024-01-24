package com.demo.property_based;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import com.google.json.JsonSanitizer;
import org.jetbrains.annotations.NotNull;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class PropertyBasedFuzzTest {

    /**
     * Real test that found the XSS vulnerability in json-sanitizer version 1.2.1
     * @param input
     * @throws Exception
     */
    @FuzzTest
    public void fuzzTestJsonSanitizer(@NotNull String input) throws Exception {
        String safeJSON;

        try {
            // Calling the sanitize function with fuzzer generated inputs
            safeJSON = JsonSanitizer.sanitize(input, 10);
        } catch (Exception ignored){
            return;
        }

        // Property based evaluation as sort of custom bug detector.
        assert !safeJSON.contains("</script")
                : new SecurityException("XSS Vulnerability in JsonSanitizer");
    }
}
