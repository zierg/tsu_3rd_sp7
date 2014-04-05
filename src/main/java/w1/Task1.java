package w1;

import java.util.Arrays;


/**
 * Примечание: в этой задаче не рекомендуется использовать контейнеры.
 * Попробуйте вместо этого воспользоваться сортировкой и работать уже с отсортированным массивом.
 * Разрешается использовать библиотечные методы для сортировки, а не писать свои.
 */
public class Task1 {
    /**
     * Метод вычисляет количество уникальных чисел в массиве "array"
     * @param array массив
     * @return количество уникальных чисел
     */
    public int numberOfUniqueNumbers(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int[] sortedArray = Arrays.copyOfRange(array, 0, array.length);
        // Чтобы не изменять исходный массив
        Arrays.sort(sortedArray);
        int unique = 1;
        int latestUnique = sortedArray[0];
        for (int i = 1; i < sortedArray.length; i++) {
            if (sortedArray[i] > latestUnique) {
                latestUnique = sortedArray[i];
                unique++;
            }
        }
        return unique;
    }

    /**
     * Самое часто встречающееся число. Если таковых несколько, то возвращает любое из них
     * @param array массив
     * @return самое часто встречающееся число (или одно из, если таковых несколько)
     */
    public int mostFrequentNumber(int[] array) {
        return 42;
    }
}
