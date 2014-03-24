import w1.Task1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class W1Task1Test {
    private static final int ARAY_SIZE = (1024*1024);
    private static final int STOP_GENERATION_AFTER_NUM_OF_FAILS = 100;
    private static final int NUM_OF_MULTIPLE_NUMS = 11;

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

    Set<Integer> possibleSolutions(Map<Integer, Integer> numAndQuantity) {
        Set<Integer> possibleSolutions = new HashSet<>();

        int max_quantity = Collections.max(numAndQuantity.values());
        for (Map.Entry<Integer, Integer> e : numAndQuantity.entrySet()) {
            Integer number = e.getKey();
            Integer quantity = e.getValue();

            if (quantity == max_quantity)
                possibleSolutions.add(number);
        }

        if (possibleSolutions.isEmpty())
            throw new RuntimeException("internal test error: empty solution");

        return possibleSolutions;
    }

    private static int[] prepareArray(int size, Map<Integer, Integer> numsAndQuantities) {
        int[] array = shuffledArrayFromSet(makeSetWithRandomInts(size));
        fillArrayWithNumbersAndQuantities(array, numsAndQuantities);
        return array;
    }

    private void __testTaskWithNumsAndQuantities(Task1 task, Map<Integer, Integer> numsAndQuantities) {
        Set<Integer> possibleSolutions = possibleSolutions(numsAndQuantities);

        int[] array = prepareArray(ARAY_SIZE, numsAndQuantities);
        int solution = task.mostFrequentNumber(array);
        assertTrue(possibleSolutions.contains(solution));
    }

    private Map<Integer, Integer> make42numsAndQuantities() {
        Map<Integer, Integer> numsAndQuantities = new HashMap<>();
        numsAndQuantities.put(42, 42);
        return numsAndQuantities;
    }

    private Map<Integer, Integer> makeSingleRandomNumsAndQuantities() {
        Random r = new Random();
        Map<Integer, Integer> numsAndQuantities = new HashMap<>();
        numsAndQuantities.put(r.nextInt(), 2);
        return numsAndQuantities;
    }

    private Map<Integer, Integer> makeMultipleRandomNumsAndQuantities() {
        Random r = new Random();
        Map<Integer, Integer> numsAndQuantities = new HashMap<>();
        for (int i = 0; i < NUM_OF_MULTIPLE_NUMS; i++) {
            numsAndQuantities.put(r.nextInt(), NUM_OF_MULTIPLE_NUMS);
        }

        return numsAndQuantities;
    }

    private void test42(Task1 task) {
        Map<Integer, Integer> numsAndQuantities = make42numsAndQuantities();
        __testTaskWithNumsAndQuantities(task, numsAndQuantities);
    }

    private void testSingleRandomNumber(Task1 task) {
        Map<Integer, Integer> numsAndQuantities = makeSingleRandomNumsAndQuantities();
        __testTaskWithNumsAndQuantities(task, numsAndQuantities);
    }

    private void testMultipleRandomNumbers(Task1 task) {
        Map<Integer, Integer> numsAndQuantities = makeMultipleRandomNumsAndQuantities();
        __testTaskWithNumsAndQuantities(task, numsAndQuantities);
    }

    @Test
    public void testMostFrequentNumber__DUMMY__() {
        Task1 task = new Task1();
        test42(task);
    }

    @Test
    public void testMostFrequentNumberSingle() {
        Task1 task = new Task1();
        testSingleRandomNumber(task);
    }

    @Test
    public void testMostFrequentNumberMultiple() {
        Task1 task = new Task1();
        testMultipleRandomNumbers(task);
    }


    private static int sumOfAllValues(Map<Integer, Integer> m) {
        int a = 0;
        for (Integer v : m.values())
            a += v;
        return a;
    }


    private void __testNumberOfUniqueNumbers(Task1 task, Map<Integer, Integer> numsAndQuantities) {
        int[] array = prepareArray(ARAY_SIZE, numsAndQuantities);

        int solution = task.numberOfUniqueNumbers(array);
        /*
         we have a map where key is a number an value is a quantity of this number
         so we can calculate number of unqie numbers in the array using this formula:
          (lenght of array) - (quantity of non-unqie numbers in the array) + (number of non-unique numbers)
         note: we must add number of non-unique numbers because we need to count ser of same number as one single number
         */
        int trueSolution = array.length - sumOfAllValues(numsAndQuantities) + numsAndQuantities.size();
        assertEquals(solution, trueSolution);
    }


    @Test
    public void testNumberOfUniqueNumbers__DUMMY__() {
        Task1 task = new Task1();
        Map<Integer, Integer> numsAndQuantities = make42numsAndQuantities();
        __testNumberOfUniqueNumbers(task, numsAndQuantities);
    }

    @Test
    public void testNumberOfUniqueNumbersSignle() {
        Task1 task = new Task1();
        Map<Integer, Integer> numsAndQuantities = makeSingleRandomNumsAndQuantities();
        __testNumberOfUniqueNumbers(task, numsAndQuantities);
    }

    @Test
    public void testNumberOfUniqueNumbersMultiple() {
        Task1 task = new Task1();
        Map<Integer, Integer> numsAndQuantities = makeMultipleRandomNumsAndQuantities();
        __testNumberOfUniqueNumbers(task, numsAndQuantities);
    }
}
