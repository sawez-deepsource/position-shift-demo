package demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates line number shift caused by text block \ continuation.
 * Fields after text blocks with \ will report different line numbers
 * on JDT 3.37 vs JDT 3.44+.
 */
public class PositionShiftDemo {

    // === BEFORE TEXT BLOCK — positions should be identical ===

    // JAVA-E0291: self-assignment
    void bugBefore() {
        int x = 5;
        x = x;
    }

    // JAVA-S1050: public mutable static
    public static int[] ARRAY_BEFORE = new int[]{1, 2, 3};

    // JAVA-W1065: concrete collection parameter
    public void concreteBefore(ArrayList<String> list) {
        System.out.println(list);
    }

    // === TEXT BLOCK WITH \ CONTINUATION ===

    void textBlockMethod() {
        String query = """
                SELECT * FROM users \
                WHERE active = true \
                AND role = 'admin' \
                ORDER BY name""";
        System.out.println(query);
    }

    // === AFTER TEXT BLOCK — positions will shift by +3 (three \ above) ===

    // JAVA-E0291: self-assignment (same rule as above, will show shifted line)
    void bugAfter() {
        int y = 5;
        y = y;
    }

    // JAVA-S1050: public mutable static (same rule as above)
    public static int[] ARRAY_AFTER = new int[]{4, 5, 6};

    // JAVA-W1065: concrete collection parameter (same rule as above)
    public void concreteAfter(ArrayList<String> list) {
        System.out.println(list);
    }

    // === SECOND TEXT BLOCK WITH MORE \ — compounds the shift ===

    void anotherTextBlock() {
        String html = """
                <html> \
                <body> \
                <p>Hello</p> \
                </body> \
                </html>""";
        System.out.println(html);
    }

    // === AFTER SECOND TEXT BLOCK — shift is now +3 + +4 = +7 ===

    // JAVA-E0291: self-assignment (same rule, even more shifted)
    void bugFinal() {
        int z = 5;
        z = z;
    }

    // JAVA-S1050: public mutable static
    public static int[] ARRAY_FINAL = new int[]{7, 8, 9};
}
