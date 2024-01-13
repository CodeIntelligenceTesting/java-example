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

import com.demo.api.helper.MockLdapContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import java.util.Base64;


@RestController
public class ComplexAPIExample {
  private final DirContext ctx = new MockLdapContext();

  /**
   * Endpoint vulnerable if base64 encoded id of user id equals "YWRtaW46",
   * numberString XORed with 1000110 equals 1111111111 and the message causes an LDAP vulnerability
   * @param id
   * @param numberString
   * @param message
   * @return
   */
  @GetMapping("/complex")
  public String insecureComplexExample(@RequestParam String id,
                                       @RequestParam String numberString,
                                       @RequestParam String message) {
    Base64.Encoder base64 = Base64.getEncoder();

    // We guard the LDAP vulnerability by checking if the base64 encoded id equals "YWRtaW46"
    // and the numberString XORed with 1000110 equals 1111111111.
    // This displays CI Fuzz's ability to detect the checks made and generate a test case
    // triggering passing the checks and triggering the RCE.
    // Black-box approaches lack insights into the code and thus cannot handle these cases.

    if (base64.encodeToString(id.getBytes()).equals("YWRtaW46")) {
      try {
        if ((Integer.valueOf(numberString) ^ 1000110) == 111111111) {
          String base = "ou=" + message + ",dc=example,dc=com";
          ctx.search(base, "(&(uid=foo)(cn=bar))", new SearchControls());
        }
      } catch (Exception ignored) {
        // We don't need to handle the exception
      }

    }
    return "Hello user " + id + "!";
  }
}
