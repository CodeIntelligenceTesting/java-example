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
public class SimpleAPIExampleTest {
  @Autowired private MockMvc mockMvc;

  /**
   * Unit Test for {@link SimpleAPIExample#insecureSimpleExample}
   * @throws Exception
   */
  @Test
  public void unitTestInsecureSimpleExampleDeveloper() throws Exception {
    mockMvc.perform(get("/simple")
            .param("username", "Developer")
            .param("password", "102312")
            .param("queryValue", "Developer"))
            .andExpect(status().is2xxSuccessful());
  }

  /**
   * Unit Test for {@link SimpleAPIExample#insecureSimpleExample}
   * @throws Exception
   */
  @Test
  public void unitTestInsecureSimpleExampleMaintainer() throws Exception {
    mockMvc.perform(get("/simple")
            .param("username", "Maintainer")
            .param("password", "-adad12")
            .param("queryValue", "Maintainer"))
            .andExpect(status().is2xxSuccessful());
  }

  /**
   * Simple fuzz test for {@link SimpleAPIExample#insecureSimpleExample}
   * @param data
   * @throws Exception
   */
  @FuzzTest
  public void fuzzTestInsecureSimpleExample(FuzzedDataProvider data) throws Exception {
    mockMvc.perform(get("/simple")
            .param("username", data.consumeString(10))
            .param("password", data.consumeString(10))
            .param("queryValue", data.consumeRemainingAsString()))
            .andExpect(status().is2xxSuccessful());
  }

  /**
   * Unit Test for {@link SimpleAPIExample#insecureJsonExample(SimpleAPIExample.User)}
   * @throws Exception
   */
  @Test
  public void unitTestInsecureJsonExampleDeveloper() throws Exception {
    ObjectMapper om = new ObjectMapper();
    SimpleAPIExample.User user = new SimpleAPIExample.User();
    user.username = "Developer";
    user.password = "1adada2332";
    user.queryValue = "Developer";
    mockMvc.perform(post("/json")
            .content(om.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
  }

  /**
   * Simple fuzz test for {@link SimpleAPIExample#insecureJsonExample(SimpleAPIExample.User)}
   * This time a JSON object is expected as input.
   * @param user
   * @throws Exception
   */
  @FuzzTest
  public void fuzzTestInsecureJsonExample(SimpleAPIExample.User user) throws Exception {
    ObjectMapper om = new ObjectMapper();

    if (user == null || user.username == null || user.password == null || user.queryValue == null) {
      return;
    }
    mockMvc.perform(post("/json")
            .content(om.writeValueAsString(user))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
  }
}
