import static org.junit.Assert.*;
import org.junit.Test;
import w1.Task2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class W1Task2Test {
    private static final int MIN_ARR_LEN = 1;
    private static final int MAX_ARR_LEN = 2048;

    private static void testIt(Task2 task, int array[], int m, int n) {
        assertTrue(m < n);

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < array.length; i++) {
            if (i >= m && i < n) continue;

            int x = array[i];

            int c = 0;
            if (map.containsKey(x))
                c = map.get(x);
            map.put(x, c+1);
        }

        int array2[] = task.remove(array, m, n);
        int newlen = array.length - (n - m);
        for (int i = 0; i < newlen; i++) {
            int x = array2[i];
            assertTrue(map.containsKey(x));
            int c = map.get(x);
            if (c > 1) map.put(x, c - 1);
            else       map.remove(x);
        }
    }

    private static int[] makeArray(int length) {
        int array[] = new int[length];
        Random r = new Random();
        for (int i = 0; i < length; i++) array[i] = r.nextInt();
        return array;
    }

    private static int[] makeArray() {
        Random r = new Random();
        int array[] = makeArray(MIN_ARR_LEN + r.nextInt(MAX_ARR_LEN - MIN_ARR_LEN));
        return array;
    }

    @Test
    public void testSingleRemove() {
        int array[] = makeArray();

        Random r = new Random();
        int i = r.nextInt(array.length);
        testIt(new Task2(), array, i, i + 1);
    }

    @Test
    public void testManyRemove() {
        int array[] = makeArray();

        Random r = new Random();
        int i = r.nextInt(array.length);
        int j = r.nextInt(array.length - i + 1);
        testIt(new Task2(), array, i, i + j);
    }

    @Test
    public void testOneElementArrayRemove() {
        Random r = new Random();
        int array[] = new int[1];
        array[0] = r.nextInt();
        testIt(new Task2(), array, 0, 1);
    }
}
