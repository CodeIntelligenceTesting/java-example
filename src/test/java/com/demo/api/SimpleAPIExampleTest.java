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

@WebMvcTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class SimpleAPIExampleTest {
  @Autowired private MockMvc mockMvc;

  @Test
  public void unitTestHelloDeveloper() throws Exception {
    mockMvc.perform(get("/simple")
            .param("a", "0")
            .param("b", "10")
            .param("name", "Developer"));
  }

  @Test
  public void unitTestMaintainer() throws Exception {
    mockMvc.perform(get("/simple")
            .param("a", "20")
            .param("b", "-10")
            .param("name", "Maintainer"));
  }

  /**
   * Simple fuzz test to trigger a simple guarded vulnerability
   * @param data
   * @throws Exception
   */
  @FuzzTest
  public void fuzzTestSimpleExample(FuzzedDataProvider data) throws Exception {
    mockMvc.perform(get("/simple")
            .param("a", data.consumeString(10))
            .param("b", data.consumeString(10))
            .param("name", data.consumeRemainingAsString()));
  }


  @Test
  public void unitTestJsonDeveloper() throws Exception {
    ObjectMapper om = new ObjectMapper();
    SimpleAPIExample.User user = new SimpleAPIExample.User();
    user.a = 0;
    user.b = 10;
    user.name = "Developer";
    mockMvc.perform(post("/json").content(om.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON));
  }

  /**
   * Simple fuzz test to trigger a simple guarded vulnerability.
   * This time a JSON object is expected as input.
   * @param data
   * @throws Exception
   */
  @FuzzTest
  public void fuzzTestJsonExample(FuzzedDataProvider data) throws Exception {
    ObjectMapper om = new ObjectMapper();
    SimpleAPIExample.User user = new SimpleAPIExample.User();
    user.a = data.consumeInt();
    user.b = data.consumeInt();
    user.name = data.consumeRemainingAsString();
    mockMvc.perform(post("/json").content(om.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON));
  }
}
