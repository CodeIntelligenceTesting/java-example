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

import com.demo.api.helper.SQLInjectionHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class SimpleAPIExample {

  public static class User {
    public int a;
    public int b;
    public String name;
  }

  public SimpleAPIExample() throws SQLException{
    SQLInjectionHelper.connect();
  }


  /**
   * Insecure /hello endpoint function that is vulnerable to SQL injection attacks
   * when params a and b match the checks.
   * @param name
   * @return
   */
  @GetMapping("/simple")
  public String insecureSimpleExample(@RequestParam(required = true) String a,
                                      @RequestParam(required = true) String b,
                                      @RequestParam(required = false, defaultValue = "World") String name) {
    // We trigger a SQL injection vulnerability.
    // This shows how CI Fuzz can detect the guarding checks
    // and generates a test case triggering the vulnerability.
    // Black-box approaches lack insights into the code and thus cannot handle these cases.
    if (a.equals("20000") ) {
      if (b.equals("2000000")) {
          try {
            SQLInjectionHelper.insecureDBRequest(name);
          } catch (Exception ignored) {
            // We don't need to handle the exception
          }
      }
    }
    return "Hello " + name + "!";
  }

  /**
   * Insecure /json endpoint function that is vulnerable to SQL injection attacks
   * expects a JSON object.
   * @param user
   * @return
   */
  @PostMapping("/json")
  public String insecureJsonExample(@RequestBody User user) {
    // We trigger a SQL injection vulnerability.
    // This shows how CI Fuzz can detect the guarding checks
    // and generates a test case triggering the vulnerability.
    // Black-box approaches lack insights into the code and thus cannot handle these cases.
    if (user.a >= 20000) {
      if (user.b >= 2000000) {
          try {
            SQLInjectionHelper.insecureDBRequest(user.name);
          } catch (Exception ignored) {
            // We don't need to handle the exception
          }
      }
    }
    return "Hello " + user.name + "!";
  }
}
