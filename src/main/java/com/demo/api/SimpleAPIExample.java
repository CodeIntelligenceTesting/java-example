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

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class SimpleAPIExample {
  private Connection conn;

  public static class User {
    public String username;
    public String password;
    public String queryValue;
  }

  public SimpleAPIExample() throws SQLException{
    this.connect();
  }


  /**
   * Insecure /hello endpoint function that is vulnerable to SQL injection attacks
   * when params a and b match the checks.
   * @param queryValue
   * @return
   */
  @GetMapping("/simple")
  public String insecureSimpleExample(@RequestParam(required = true) String username,
                                      @RequestParam(required = true) String password,
                                      @RequestParam(required = false, defaultValue = "World") String queryValue) {
    // We trigger a SQL injection vulnerability.
    // This shows how CI Fuzz can detect the guarding checks
    // and generates a test case triggering the vulnerability.
    // Black-box approaches lack insights into the code and thus cannot handle these cases.
    if (username.equals("admin") ) {
      if (password.equals("123456789")) {
          try {
            String query = String.format("SELECT * FROM users WHERE username='%s'", queryValue);
            ResultSet rs = conn.createStatement().executeQuery(query);
          } catch (Exception ignored) {
            // We don't need to handle the exception
          }
      }
    }
    return "Hello " + username + "!";
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
    if (user.username.equals("admin") ) {
      if (user.password.equals("123456789")) {
        try {
          String query = String.format("SELECT * FROM users WHERE username='%s'", user.queryValue);
          ResultSet rs = conn.createStatement().executeQuery(query);
        } catch (Exception ignored) {
          // We don't need to handle the exception
        }
      }
    }
    return "Hello " + user.queryValue + "!";
  }

  /**
   * Helper function that connects to an in-memory db and inserts dummy data
   * @throws SQLException
   */
  private void connect() throws SQLException{
    JdbcDataSource ds = new JdbcDataSource();
    ds.setURL("jdbc:h2:mem:database.db");
    conn = ds.getConnection();

    // A dummy database is dynamically created
    conn.createStatement().execute("CREATE TABLE IF NOT EXISTS users (id IDENTITY PRIMARY KEY, username VARCHAR(50), name VARCHAR(50), password VARCHAR(50))");
    conn.createStatement().execute("INSERT INTO users (username, name, password) VALUES ('admin', 'Administrator', 'passw0rd')");
    conn.createStatement().execute("INSERT INTO users (username, name, password) VALUES ('john', ' John', 'hello123')");
  }
}
