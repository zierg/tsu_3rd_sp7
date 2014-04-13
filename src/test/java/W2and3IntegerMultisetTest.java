import static org.junit.Assert.*;
import org.junit.Test;
import w2and3.IntegerMultiset;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class W2and3IntegerMultisetTest {
    private static final int NUM_OF_ELEMENTS = 1024;
    private static final int MAX_QUANTITY = 11;

    private static final Map<Integer, Integer> makeRandomNumsWithQuantities() {
        Map<Integer, Integer> r = new HashMap<>();
        Random random = new Random();

        while (r.size() < NUM_OF_ELEMENTS) {
            int n = random.nextInt();
            if (!r.containsKey(n)) {
                int q = 0;
                while ((q = random.nextInt(MAX_QUANTITY)) == 0); // warning! tricky (may be) code
                r.put(n, q);
            }
        }

        return r;
    }

    private static void fillMultisetWithNumsWithQuantities(IntegerMultiset multiset, Map<Integer, Integer> numsAndQuantities) {
        for (Map.Entry<Integer, Integer> e : numsAndQuantities.entrySet()) {
            int n = e.getKey();
            int q = e.getValue();

            while (--q >= 0) multiset.add(n);
        }
    }

    private static void testElementsExistense(IntegerMultiset multiset, Map<Integer, Integer> numsAndQuantities) {
        for (Integer n : numsAndQuantities.keySet())
            assertTrue(multiset.contains(n));
    }

    private static void testElementsPop(IntegerMultiset multiset, Map<Integer, Integer> numsAndQuantities) {
        for (Map.Entry<Integer, Integer> e : numsAndQuantities.entrySet()) {
            int n = e.getKey();
            int q = e.getValue();

            while (--q >= 0) {
                assertTrue(multiset.contains(n));
                boolean elementWasExisted = multiset.pop(n);
                assertTrue(elementWasExisted);
            }

            assertFalse(multiset.contains(n));
        }
    }

    @Test
    public void testIntegerMultisetBasicOperations() {
        IntegerMultiset multiset = new IntegerMultiset();
        Map<Integer, Integer> numsAndQuantities = makeRandomNumsWithQuantities();

        fillMultisetWithNumsWithQuantities(multiset, numsAndQuantities);
        testElementsExistense(multiset, numsAndQuantities);
        testElementsPop(multiset, numsAndQuantities);
    }

    @Test
    public void testIntegerMultisetElementsRemove() {
        IntegerMultiset multiset = new IntegerMultiset();
        Map<Integer, Integer> numsAndQuantities = makeRandomNumsWithQuantities();

        fillMultisetWithNumsWithQuantities(multiset, numsAndQuantities);

        for (Integer n : numsAndQuantities.keySet()) {
            assertTrue(multiset.contains(n));
            boolean elementWasExisted = multiset.remove(n);
            assertTrue(elementWasExisted);
            assertFalse(multiset.contains(n));
        }
    }
}
