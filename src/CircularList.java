/**
 * Implementation of Josephus problem.  The Josephus problem
 * is named after the historian Flavius Josephus.  For more
 * information on this problem visit:
 * http://en.wikipedia.org/wiki/Josephus_problem
 */

import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.List;

public class CircularList<E> {
    private Node<E> head;
    private Node<E> tail;
    /*
    While I do not think I was supposed to, I created a tail to help keep track of the node prior to head.
    I know there was definitely other ways of doing this without using a tail, I just found it easier to use a tail.
     */
    private int count;  // number of elements in the list

    // running time: O(1)

    /**
     * Constructs an empty Josephus circle
     */
    public CircularList() {
        head = tail = null;
        count = 0;
    }

    // Running time O(n)

    /**
     * Constructs an Josephus circle by adding the
     * elements from a list
     *
     * @param list The List of items of type E
     */

    public CircularList(List<E> list) {
        head = tail = null;
        if (list.size() == 0) {
            throw new NoSuchElementException();
        } else if (list.size() == 1) {
            add(list.get(0));
        } else if (list.size() > 1) {
            addAll(list);
        }

    }
    // Running time: O(n)

    /**
     * Inserts the specified element in the list at the
     * last position
     *
     * @param dataItem the element to add
     */

    public void add(E dataItem) {

        Node<E> node = new Node<E>(dataItem);
        if (head == null) {// list is empty
            head = tail = node;
            count++;
        } else { //If the list is not empty, add the item to the end of the list
            tail.next = node;
            node.previous = tail;
            tail = node;
            tail.next = head;
            head.previous = tail;
            count++;//I increment count here so I don't have to in the addAll method, since I can just use add in addAll
        }

    }

    // Running time O(n)

    /**
     * appends all the elements from the list
     * to the Josephus circle.
     *
     * @param list the List of elements to add
     */

    public void addAll(List<E> list) {
        if (count == 0) {
            for (int i = 0; i < list.size(); i++) {
                add(list.get(i));
            }
        } else {
            int temp = 0;
            for (int i = count - 1; i < list.size(); i++) {
                add(list.get(temp));
                temp++;
            }
            tail.next = head;
        }
    }

    // Running time O(n)

    /**
     * returns a reference to the element at
     * position index
     *
     * @param index The index of the element being sought
     * @return A reference to the element at position index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public E get(int index) {
        int pos = 0;
        if (index < 0 || index > count - 1) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> temp = head;
        while (pos < index) {
            pos++;
            temp = temp.next;
        }
        return temp.data;
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     *
     * @param o the element to search for
     * @return the index of the first occurrence of the specified element in
     * this list, or -1 if this list does not contain the element
     */
    public int indexOf(Object o) {
        for (int i = 0; i < count - 1; i++) {
            if (o.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    // Running time O(n)

    /**
     * Sets the element at position index to reference
     * anEntry.
     *
     * @param index   The position of the element that is to
     *                be set
     * @param anEntry The new value at position index
     * @return the element that was previously at position index
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public E set(int index, E anEntry) {
        if ((index < 0) || (index >= count))
            throw new IndexOutOfBoundsException(Integer.toString(index));
        Node<E> temp = head;
        E temp2 = null;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        temp2 = temp.data;
        temp.data = anEntry;
        return temp2;
    }

    // Running time O(n)

    /**
     * Inserts the specified element in the list at a
     * given index
     *
     * @param index   The position at which the new element
     *                is to be inserted
     * @param anEntry The element to add
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void add(int index, E anEntry) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = new Node<E>(anEntry);
        Node<E> temp = head;
        if (head == null) {// list is empty
            head = tail = node;
            count++;
        } else {
            for (int i = 0; i < index - 1; i++) {
                temp = temp.next;
            }
            node.next = temp.next;
            temp.next.previous = node;
            node.previous = temp;
            temp.next = node;
            count++;
        }
    }
    // Running time O(n)

    /**
     * removes the element at position index
     *
     * @param index The index of the element to be removed
     * @return The element that was removed
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public E remove(int index) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException();
        }

        Node<E> current = head;
        Node<E> previous = null;
        for (int i = 0; i <= index - 1; i++) {
            previous = current;
            current = current.next;
        }
        // current points to the Node at index
        if (current == head) {
            tail.next = head.next;
            head.next.previous = tail;
            head = head.next;
            count--;
        } else if (current == tail) {
            tail.previous.next = head;
            head.previous = tail.previous;
            tail = tail.previous;
            count--;
        } else {
            previous.next = current.next;
            current.next.previous = previous;
            count--;
        }
        return current.data;
    }

    // Running time  O(n)

    /**
     * removes the item from the list if it is present
     *
     * @param item item to be removed
     * @throws NoSuchElementException if the item is not found
     */
    public void remove(E item) {

        for (int i = 0; i < count; i++) {

            if (get(i).equals(item)) {
                if (item == head) {
                    tail.next = head.next;
                    head.next.previous = tail;
                    head = head.next;
                    count--;
                    return;
                } else if (item == tail) {
                    tail.previous.next = head;
                    head.previous = tail.previous;
                    tail = tail.previous;
                    count--;
                    return;
                } else {
                    remove(i);
                    return;
                }
            }
        }
        throw new NoSuchElementException();
    }

    // Running time O(n)

    /**
     * sets the start position for the Josephus game
     *
     * @param index The starting position
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public void setStartPosition(int index) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException();
        } else {
            Node<E> temp = head;
            for (int i = 0; i != index; i++) {
                temp = temp.next;
            }
            head = temp;
        }
    }

    /* This private utility method is used in startJosephus
       method.
       Running time:  O(1)
     */
    private void removeAfter(Node<E> node) {
        node.next = node.next.next;
        node.next.previous = node;
        count--;
    }

    /**
     * simulates the Josephus game by killing every other person
     * until the winner is the only one left.
     *
     * @return The survivor of the game
     */
    private E startJosephus() {
        while (count > 1) {
            removeAfter(head);
            head = head.next;
        }
        return head.data;
    }

    /**
     * returns the sole survivor of the Josephus game
     *
     * @return the sole survivor of the Josephus game
     */
    public E getWinner() {
        return startJosephus();
    }

    /**
     * Returns a list-iterator of the elements in this list
     * (in proper sequence), starting at the beginning
     * of the list.  Also provides methods to traverse
     * the list in the reverse order.
     */
    public ListIterator<E> iterator() {
        return new MyIterator();
    }

    /**
     * @return The number of elements in the list
     */
    public int size() {
        return count;
    }

    // this is an inner class implementing the ListIterator
    // interface.
    // visit http://docs.oracle.com/javase/6/docs/api/java/util/ListIterator.html
    // for a complete list of methods in ListIterator
    // Your book uses the ListIterator methods to implement
    // a doubly linked list (pages 112- 120)
    // I am using only hasNext(), next(), previous() and hasPrevious()
    // methods here.
    private class MyIterator implements ListIterator<E> {
        private Node<E> forward = head;
        private Node<E> backward = head;
        private boolean firstTime = true;

        /**
         * checks if a current item E  is the last
         * in the collection
         */
        public boolean hasNext() {
            return (forward != null);
        }

        /**
         * returns the next item in the collection if
         * there is one.  If there is no more items
         * throws NoSuchElementException
         */
        public E next() {
            if (forward == null) throw
                    new NoSuchElementException();
            else {
                E item = forward.data;
                forward = forward.next;
                if (forward == head) forward = null;
                return item;
            }
        }

        /**
         * checks if a current item is the first
         * in the collection
         */
        public boolean hasPrevious() {
            return (backward != null);
        }

        /**
         * returns the previous item in the collection if
         * there is one.  If there is no more items
         * throws NoSuchElementException
         */
        public E previous() {
            if (backward == null) throw
                    new NoSuchElementException();
            else {
                if (firstTime) {
                    backward = backward.previous;
                    firstTime = false;
                }
                E item = backward.data;
                backward = backward.previous;
                if (backward == head.previous) backward = null;
                return item;
            }
        }

        /* this operation is not supported */
        public void add(E obj) {
            throw new UnsupportedOperationException();
        }

        /* this operation is not supported */
        public void set(E obj) {
            throw new UnsupportedOperationException();
        }

        /* this operation is not supported */
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        /* this operation is not supported */
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        /* this operation is not supported */
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> previous;

        /**
         * constructor Creates an empty node with both next and previous fields
         * set to be null
         *
         * @param dataItem - item to be inserted
         */
        private Node(E dataItem) {
            data = dataItem;
            previous = next = null;
        }

        /**
         * creates a new node that references another node
         *
         * @param dataItem        The data stored
         * @param previousNodeRef The node previous to this node
         * @param nextNodeRef     The node next to this node
         */
        private Node(E dataItem, Node<E> previousNodeRef, Node<E> nextNodeRef) {
            data = dataItem;
            previous = previousNodeRef;
            next = nextNodeRef;
        }
    }
}
