package com.demo.libraries;

import java.lang.Math;
import com.demo.libraries.proto.MathCalc;

public class ProtobufExample {
    /**
     * Calculates a lucky number based on the calculations given
     * via the protobuf message. The lucky number cannot be zero.
     * Also make sure that interim results are never zero. It brings bad luck!
     */
    public static int calculateLuckyNumber(MathCalc.Calculation calc) {
        // Start with the provided start value
        int luckyNumber = calc.getStartValue();
        if(luckyNumber == 0) {
            throw new IllegalArgumentException("Start value must not be the unlucky number");
        }

        // Iterate over all operations and calculate the final value
        for(MathCalc.Operation op : calc.getOpsList()) {
            if(op.hasAddition()) {
                if(luckyNumber < 0 && -1 * luckyNumber == op.getAddition().getAddend()) {
                    throw new IllegalArgumentException("Addition results in unlucky number");
                }
                luckyNumber += op.getAddition().getAddend();
            } else if(op.hasSubtraction()) {
                if(luckyNumber == op.getSubtraction().getSubtrahend()) {
                    throw new IllegalArgumentException("Subtraction results in unlucky number");
                }
                luckyNumber -= op.getSubtraction().getSubtrahend();
            } else if(op.hasMultiplication()) {
                if(op.getMultiplication().getFactor() == 0) {
                    throw new IllegalArgumentException("Multiplication results in unlucky number");
                }
                luckyNumber *= op.getMultiplication().getFactor();
            } else if(op.hasDivision()) {
                if(Math.abs(luckyNumber) < Math.abs(op.getDivision().getDivisor())) {
                    throw new IllegalArgumentException("Division results in unlucky number");
                } else if(op.getDivision().getDivisor() == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }
                luckyNumber /= op.getDivision().getDivisor();
            }
        }

        return luckyNumber;
    }
}
