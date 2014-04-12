import static org.junit.Assert.*;
import org.junit.Test;
import w4.SinglyLinkedList;

import java.util.*;

public class SinglyLinkedListTest {
    private static final int SMALL_LIST_SIZE = 1024;
    private static final int BIG_LIST_SIZE   = (10 * SMALL_LIST_SIZE);
    private static final int HUGE_LIST_SIZE  = (BIG_LIST_SIZE * BIG_LIST_SIZE);

    private static List<Integer> makeRandomNumberList(int size) {
        List<Integer> r = new ArrayList<>();
        Random random = new Random();
        while (--size > 0) r.add(random.nextInt());
        return r;
    }

    private static void copy(List<Integer> from, SinglyLinkedList to) {
        for (Integer n : from) to.push_back(n);
    }

    public void testAddElements(int size) {
        List<Integer> src = makeRandomNumberList(size);
        SinglyLinkedList l = new SinglyLinkedList();
        copy(src, l);

        ListIterator it = src.listIterator(src.size());
        while (it.hasPrevious()) assertEquals(it.previous(), l.pop_back());
    }

    public void testSort(int size) {
        List<Integer> src = makeRandomNumberList(size);
        SinglyLinkedList l = new SinglyLinkedList();
        copy(src, l);

        Collections.sort(src);
        l.sort();

        ListIterator it = src.listIterator(src.size());
        while (it.hasPrevious()) assertEquals(it.previous(), l.pop_back());
    }

    @Test
    public void testSmallList() {
        final int s = SMALL_LIST_SIZE;
        testAddElements(s);
        testSort(s);
    }

    @Test
    public void testBigList() {
        final int s = BIG_LIST_SIZE;
        testAddElements(s);
        testSort(s);
    }

    /*
    @Test
    public void testHugeList() {
        final int s = BIG_LIST_SIZE;
        testAddElements(s);
        testSort(s);
    }
    */
}
