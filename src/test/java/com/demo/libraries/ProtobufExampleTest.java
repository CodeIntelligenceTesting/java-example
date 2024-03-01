package com.demo.libraries;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;
import com.demo.libraries.proto.MathCalc;
import java.lang.IllegalArgumentException;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

public class ProtobufExampleTest {
    /**
     * A simple unit test that checks one sequence of calculations.
     */
    @Test
    public void unitTest() {
        // Build a protobuf object with a number of operations:
        // (((5 + 5) * 12) - 36) / 2
        MathCalc.Operation op1 = MathCalc.Operation.newBuilder()
            .setAddition(MathCalc.OpAddition.newBuilder()
                .setAddend(5).build()).build();
        MathCalc.Operation op2 = MathCalc.Operation.newBuilder()
            .setMultiplication(MathCalc.OpMultiplication.newBuilder()
                .setFactor(12).build()).build();
        MathCalc.Operation op3 = MathCalc.Operation.newBuilder()
            .setSubtraction(MathCalc.OpSubtraction.newBuilder()
                .setSubtrahend(36).build()).build();
        MathCalc.Operation op4 = MathCalc.Operation.newBuilder()
            .setDivision(MathCalc.OpDivision.newBuilder()
                .setDivisor(2).build()).build();
        MathCalc.Calculation calc = MathCalc.Calculation.newBuilder()
            .setStartValue(5)
            .addOps(op1)
            .addOps(op2)
            .addOps(op3)
            .addOps(op4)
            .build();

        // Calculate the result and check it
        int luckyNumber = ProtobufExample.calculateLuckyNumber(calc);
        assert luckyNumber == 42;
    }

    /**
     * This fuzz test uses a protobuf object as input that describes
     * a sequence of calculations with arbitrarily many steps. The
     * fuzzer automatically takes care of mutating the structure.
     */
    @FuzzTest
    public void protobufFuzzTest(@NotNull MathCalc.Calculation calc) {
        // Make sure that the chains we're testing are a bit longer
        if(calc.getOpsCount() <= 5) {
            return;
        }

        // It is expected that the fuzzer creates calculations
        // that result in unlucky numbers or divisions by zero.
        // Since we're not interested in those errors, we can
        // simply catch and ignore them here.
        try {
            ProtobufExample.calculateLuckyNumber(calc);
        } catch(IllegalArgumentException ex) {}
    }
}
