import static org.junit.Assert.*;
import org.junit.Test;
import w0.Intro;

import java.util.Random;

public class IntroTest {
    private static final int MAX_VAL = 1024;

    @Test
    public void test() {
        Intro intro = new Intro();

        Random r = new Random();
        int a = r.nextInt(MAX_VAL);
        int b = r.nextInt(MAX_VAL);
        assertTrue(intro.sum(a, b) == a + b);
    }
}
