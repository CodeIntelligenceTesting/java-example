package com.demo.libraries;

import com.code_intelligence.jazzer.junit.FuzzTest;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;
import com.demo.libraries.proto.RectangleOuterClass;
import com.demo.libraries.Rectangle;
import org.junit.jupiter.api.Test;

public class ProtobufRectangleTest {
    /**
     * A simple unit test for our Rectangle class.
     * Unit tests allow us to verify that some example
     * inputs generate the correct output value. But we
     * cannot be sure that there isn't an edge case
     * lurking somewhere that we haven't thout about.
     */
    @Test
    public void unitTest() {
        Rectangle rect = new Rectangle(2, 10, -3, -8);
        long rect_area = rect.calculateArea();
        assert rect_area == 40;
    }

    /**
     * Simple example on how to use fuzz testing to check for vulnerabilities,
     * assertion violations and unexpected exceptions by calling the example
     * function with fuzzer generated inputs. If it crashes or
     * a bug detector finds something, we'll get notified.
     *
     * If code is tested that requires more complex data structures as input,
     * it is a good idea to let the fuzzer generate a protobuf that describes
     * the input format. The mutation algorithm automatically takes care of
     * generating/mutating inputs that correspond to the protobuf structure.
     */
    @FuzzTest
    public void protobufFuzzTest(@NotNull RectangleOuterClass.Rectangle proto_rect) {
        Rectangle rect = new Rectangle(proto_rect.getX1(),
                                       proto_rect.getX2(),
                                       proto_rect.getY1(),
                                       proto_rect.getY2());
        rect.calculateArea();
    }
}
