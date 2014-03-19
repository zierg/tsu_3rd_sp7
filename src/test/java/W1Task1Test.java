import w1.Task1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class W1Task1Test {
    private static final int ARAY_SIZE = (1024*1024);
    private static final int STOP_GENERATION_AFTER_NUM_OF_FAILS = 100;

    private static Set<Integer> makeSetWithRandomInts(int size) {
        Random r = new Random();

        int fails_in_row = 0;
        Set<Integer> set = new HashSet<>();
        while (set.size() < size) {
            int n = r.nextInt();
            if (set.contains(n))
                fails_in_row++;
            else {
                set.add(n);
                fails_in_row = 0;
            }

            if (fails_in_row >= STOP_GENERATION_AFTER_NUM_OF_FAILS)
                throw new RuntimeException("too many failed tries to generate uniqie random number");
        }

        return set;
    }

    // Implementing Fisher–Yates shuffle
    private static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private static int[] shuffledArrayFromSet(Set<Integer> set) {
        int[] array = new int[set.size()];

        int i = 0;
        for (Integer x : set) array[i++] = x;
        shuffleArray(array);
        return array;
    }

    private static int __generateNotVisitedIndex(int array_lenght, Set<Integer> alreadyFilledArrayIndexes) {
        Random r = new Random();
        int index = r.nextInt(array_lenght);
        int num_of_fails = 0;
        while (alreadyFilledArrayIndexes.contains(index)) {
            if (num_of_fails++ > STOP_GENERATION_AFTER_NUM_OF_FAILS)
                throw new RuntimeException("too many failed tries to generate not visited index of array");
            index = r.nextInt(array_lenght);
        }

        return index;
    }

    private static void fillArrayWithNumbersAndQuantities(int[] array, Map<Integer, Integer> numAndQuantity) {
        Set<Integer> alreadyFilledArrayIndexes = new HashSet<>();
        for (Map.Entry<Integer, Integer> e : numAndQuantity.entrySet()) {
            Integer number = e.getKey();
            Integer quantity = e.getValue();

            while (quantity > 0) {
                int index = __generateNotVisitedIndex(array.length, alreadyFilledArrayIndexes);

                int num_of_fails = 0;
                if (array[index] != number) {
                    array[index] = number;
                    alreadyFilledArrayIndexes.add(index);
                    quantity--;
                } else if (num_of_fails++ > STOP_GENERATION_AFTER_NUM_OF_FAILS)
                    throw new RuntimeException("too many tries to fill array");
            }
        }
    }

    private static Map<Integer, Integer> makeRandomNumbersAndQuantities() {
        Map<Integer, Integer> nq = new HashMap<>();
        nq.put(42, 123);
        nq.put(32, 123);
        nq.put(48, 12);
        nq.put(1, 1);
        return nq;
    }

    Set<Integer> possibleSolutions(Map<Integer, Integer> numAndQuantity) {
        Set<Integer> possibleSolutions = new HashSet<>();

        int max_quantity = Collections.max(numAndQuantity.values());
        for (Map.Entry<Integer, Integer> e : numAndQuantity.entrySet()) {
            Integer number = e.getKey();
            Integer quantity = e.getValue();

            if (quantity == max_quantity)
                possibleSolutions.add(number);
        }

        return possibleSolutions;
    }

    private static int[] prepareArray(int size, Map<Integer, Integer> numsAndQuantities) {
        int[] array = shuffledArrayFromSet(makeSetWithRandomInts(size));
        fillArrayWithNumbersAndQuantities(array, numsAndQuantities);
        return array;
    }

    @Test
    public void testMostFrequentNumber() {
        Map<Integer, Integer> numsAndQuantities = makeRandomNumbersAndQuantities();
        int[] array = prepareArray(ARAY_SIZE, numsAndQuantities);
        Set<Integer> possibleSolutions = possibleSolutions(numsAndQuantities);

        if (possibleSolutions.isEmpty())
            throw new RuntimeException("internal test error: empty solution");

        Task1 task = new Task1();
        int solution = task.mostFrequentNumber(array);
        assertTrue(possibleSolutions.contains(solution));
    }
}
