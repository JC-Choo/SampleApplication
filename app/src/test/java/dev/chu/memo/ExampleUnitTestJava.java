package dev.chu.memo;

import org.junit.Test;

public class ExampleUnitTestJava {
    @Test
    public void test() {
        Character a = null;
        String b = null;
        int c = 10;
        double d = 10.0;
        float f = 12.3f;

        String test = String.valueOf(a);
        System.out.println(test);
//        test = Character.toString(a);     // null point exception
//        System.out.println(test);

        System.out.println();

        test = String.valueOf(b);
        System.out.println(test);
//        test = b.toString();
//        System.out.println(test);

        System.out.println();

        test = String.valueOf(c);
        System.out.println(test);
        test = Integer.toString(c);
        System.out.println(test);

        System.out.println();

        test = String.valueOf(d);
        System.out.println(test);
        test = Double.toString(d);
        System.out.println(test);

        System.out.println();

        test = String.valueOf(f);
        System.out.println(test);
        test = Float.toString(f);
        System.out.println(test);

        System.out.println();

        Object object = null;
        object.toString();

    }
}
