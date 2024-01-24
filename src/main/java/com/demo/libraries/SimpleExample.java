package com.demo.libraries;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Simple Example class that holds a vulnerable function
 */
public class SimpleExample {

    /**
     * Simple vulnerable function that throws an exception if the input params a special condition.
     * @param a
     * @param b
     * @param c
     */
    public void simpleExampleFunction(int a, int b, String c) {
        if (a >= 20000) {
            if (b >= 2000000) {
                if (b - a < 100000) {
                    if (c.equals("Attacker")) {
                        // "fixed" security issue
                        //throw new SecurityException();
                    }
                }
            }
        }
    }
}
