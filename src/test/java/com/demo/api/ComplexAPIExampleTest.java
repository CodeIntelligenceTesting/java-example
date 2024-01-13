/*
 * Copyright 2023 Code Intelligence GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.demo.api;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class ComplexAPIExampleTest {
  @Autowired private MockMvc mockMvc;

  @Test
  public void unitTestBase64() throws Exception {
    mockMvc.perform(get("/complex")
            .param("id", "Some ID")
            .param("numberString", "1100")
            .param("message", "Some message"));
  }

  /**
   * Simple fuzz test for complex checks guarding the vulnerabilities
   * Letting the fuzzer detect the correct inputs itself
   * @param data
   * @throws Exception
   */
  @FuzzTest
  public void fuzzTestBase64(FuzzedDataProvider data) throws Exception {
    mockMvc.perform(get("/complex")
            .param("id", data.consumeString(10))
            .param("numberString", String.valueOf(data.consumeInt()))
            .param("message", data.consumeRemainingAsString()));
  }
}
