package w4;

/**
 * Односвязный список целых чисел
 */
public class SinglyLinkedList {

    private ListNode head = null;
    private int size = 0;

    /**
     * Добавляет число i в конец списка
     *
     * @param i
     */
    public void push_back(Integer i) {
        if (head == null) {
            head = new ListNode(i);
            size = 1;
        } else {
            ListNode newNode = new ListNode(i);
            newNode.setNext(head);
            head = newNode;
            size++;
        }
    }

    /**
     * Изымает последний добавленный элемент списка
     *
     * @return
     */
    public Integer pop_back() {
        if (head == null) {
            throw new RuntimeException("list is empty!");
        } else {
            Integer value = head.getValue();
            head = head.getNext();
            size--;
            return value;
        }
    }

    /**
     * Сортирует односвязный список.
     */
    public void sort() {
        SinglyLinkedList[] sortedList = new SinglyLinkedList[size];
        while(head != null) {
            SinglyLinkedList newList = new SinglyLinkedList();
            ListNode nodeToPop = head;
            pop_back();
            newList.addListNode(nodeToPop);
            putList(newList, sortedList, 0);
        }
        // После того, как исходный список опустошён, делаем слияние всех
        // списков, которые остались в стеке, и забираем полный список из
        // конца стека
        for (int i = 1; i < sortedList.length; i++) {
            putList(sortedList[i-1], sortedList, i);
        }
        head = sortedList[sortedList.length-1].head;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        ListNode currentNode = head;
        while (currentNode != null) {
            //builder.insert(0, currentNode.getValue() + ", ");
            builder.append(currentNode.getValue()).append(", ");
            currentNode = currentNode.getNext();
        }
        return builder.toString();
    }
    
    /**
     * Кладёт отсортированный список выбранную позицию стрека списков. Если
     * место занято, производит слияние и кладёт полученный список в следующую
     * позицию (по такому же принципу).
     * @param listToPut отсортированный список, который нужно поместить в стек
     * @param sortedLists стек списков
     * @param startPosition позиция, в которую нужно поместить список
     */
    private void putList(SinglyLinkedList listToPut, SinglyLinkedList[] sortedLists, int startPosition) {
        if (startPosition >= sortedLists.length || listToPut == null) {
            return;
        }
        if (sortedLists[startPosition] == null) {
            sortedLists[startPosition] = listToPut;
        } else {
            SinglyLinkedList newList = merge(sortedLists[startPosition], listToPut);
            sortedLists[startPosition] = null;
            putList(newList, sortedLists, startPosition + 1);
        }
    }
    
    private SinglyLinkedList merge(SinglyLinkedList list1, SinglyLinkedList list2) {
        SinglyLinkedList mergedList = new SinglyLinkedList();
        ListNode latestAddedNode = null;
        while (!list1.isEmpty() && !list2.isEmpty()) {
            ListNode node1 = list1.head;
            ListNode node2 = list2.head;
            int val1 = node1.getValue();
            int val2 = node2.getValue();
            ListNode nodeToAdd;
            if (val1 > val2) {
                nodeToAdd = node1;
                list1.pop_back();
            } else {
                nodeToAdd = node2;
                list2.pop_back();
            }
            if (latestAddedNode == null) {
                mergedList.addListNode(nodeToAdd);
            } else {
                latestAddedNode.setNext(nodeToAdd);
            }
            latestAddedNode = nodeToAdd;
        }
        // когда один из списков опустел, оставшиеся значения из другого
        // списка просто добавляются в конец
        SinglyLinkedList notEmptyList;
        if ( !list1.isEmpty() ) {
            notEmptyList = list1;
        } else {
            notEmptyList = list2;
        }
        while ( !notEmptyList.isEmpty() ) {
            ListNode nodeToAdd = notEmptyList.head;
            notEmptyList.pop_back();
            latestAddedNode.setNext(nodeToAdd);
            latestAddedNode = nodeToAdd;
        }
        return mergedList;
    }
    
    private boolean isEmpty() {
        return head == null;
    }

    public void print() {
        System.out.println(toString());
    }
    
    private void addListNode (ListNode node) {
        node.setNext(head);
        head = node;
    }

    private class ListNode {

        private Integer value;
        private ListNode next = null;

        public ListNode(Integer value) {
            this.value = value;
        }

        public ListNode(Integer value, ListNode previous) {
            this(value);
            this.next = previous;
        }

        /**
         * @return the value
         */
        public Integer getValue() {
            return value;
        }

        /**
         * @param value the value to set
         */
        public void setValue(Integer value) {
            this.value = value;
        }

        /**
         * @return the next
         */
        public ListNode getNext() {
            return next;
        }

        /**
         * @param next the next to set
         */
        public void setNext(ListNode next) {
            this.next = next;
        }
    }
}
