package w2and3;

import java.util.HashMap;
import java.util.Map;

/**
 * Вы знаете, что такое "множество". В java для работы со множествами используют классы, реализующие
 * интерфейс "Set" (например, HashSet и TreeSet).
 * Вашей задачей будет реализовать мультимноджество, т.е. множество, содержащее одинаковые элементы.
 * (а точнее, все методы этого класса). Для упрощения задачи мультимножество будет содержать только
 * целые числа (Integer).
 */
public class IntegerMultiset {
    private Map<Integer, Integer> values = new HashMap<>();
    /**
     * Добавляет элемент "e"
     * @param e
     * @return true, если в мультимножестве уже есть такой элемент и false иначе
     */
    public boolean add(Integer e) {
        Integer amountOfValues = values.get(e);
        if (amountOfValues == null) {
            values.put(e, 1);
            return false;
        } else {
            values.put(e, amountOfValues+1);
            return true;
        }
    }

    /**
     * Есть ли элемент "e"?
     * @param e
     * @return true, если элемент "e" присутствует в мультимножестве и false иначе
     */
    public boolean contains(Integer e) {
        return values.containsKey(e);
    }

    /**
     * Удаляет один элемент "e"
     * @param e
     * @return true, если удаление имело место (т.е. "e" был в мультимножестве перед удалением) и false иначе
     */
    public boolean pop(Integer e) {
        if ( !contains(e) ) {
            return false;
        }
        int amountOfValues = values.get(e);
        if (amountOfValues == 1) {
            values.remove(e);
        } else {
            values.put(e, amountOfValues-1);
        }
        return true;
    }

    /**
     * Удаляет все элементы, равные "e".
     * @param e
     * @return true, если удаление имело место и false иначе
     */
    public boolean remove(Integer e) {
        return values.remove(e) != null;
    }
}
