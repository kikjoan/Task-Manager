package util.managers.historyManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyLinkedList <T> extends LinkedList<Integer> {

    public static class Nodes<E> implements Serializable {
        public E data;
        public Nodes<E> next;
        public Nodes<E> prev;

        public Nodes(E data, Nodes<E> prev, Nodes<E> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private Nodes<T> head;
    private Nodes<T> tail;
    private int index = 0;

    public Nodes<T> linkLast(T t) {
        final Nodes<T> oldHead = head;
        final Nodes<T> newHead = new Nodes<>(t, null, oldHead);
        head = newHead;
        if (oldHead == null)
            tail = newHead;
        else {
            oldHead.prev = newHead;
            newHead.next = oldHead;
        }
        index++;
        return newHead;

    }

    public List<Integer> getTasks() {
        List<Integer> i = new ArrayList<>();

        Nodes<T> node = head;
        while (node != null) {
            i.add((Integer) node.data);
            node = node.next;
        }
        return i;

    }

    public T removeNode(Nodes<T> x) {
        final T element = x.data;
        final Nodes<T> next = x.next;
        final Nodes<T> prev = x.prev;

        if (prev == null)
            head = next;
        else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null)
            tail = prev;
        else {
            next.prev = prev;
            x.next = null;
        }
        index--;
        x.data = null;
        return element;
    }

}
