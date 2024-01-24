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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class ComplexAPIExampleTest {
  @Autowired private MockMvc mockMvc;

  /**
   * Unit test for {@link ComplexAPIExample#insecureComplexBase64Example(ComplexAPIExample.ImportantInformation)}
   * @throws Exception
   */
  @Test
  public void unitTestBase64() throws Exception {
    //Creating JSON mapping object
    ObjectMapper om = new ObjectMapper();
    ComplexAPIExample.ImportantInformation importantInformation = new ComplexAPIExample.ImportantInformation();
    importantInformation.id = "Developer";
    importantInformation.checkNumber = 12332;
    importantInformation.className = "String";

    // executing request
    mockMvc.perform(post("/complex")
            .content(om.writeValueAsString(importantInformation))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
  }

  /**
   * Simple fuzz test for {@link ComplexAPIExample#insecureComplexBase64Example(ComplexAPIExample.ImportantInformation)}
   * Letting the fuzzer detect the correct inputs itself
   * @param importantInformation
   * @throws Exception
   */
  @FuzzTest
  public void fuzzTestInsecureJsonExample(ComplexAPIExample.ImportantInformation importantInformation) throws Exception {
    ObjectMapper om = new ObjectMapper();

    if (importantInformation == null || importantInformation.id == null || importantInformation.className == null) {
      return;
    }

    mockMvc.perform(post("/complex")
            .content(om.writeValueAsString(importantInformation))
            .contentType(MediaType.APPLICATION_JSON));
  }
}
