public class Queue<T> {

    private int size;
    private int capacity;
    private Element front;
    private Element back;

    public Queue(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        this.back = null;
        this.front = null;
    }

    public boolean enqueue(T t) {
        if (this.size == this.capacity)
            return false;

        Element tmp = back;
        this.back = new Element(t);
        this.back.next = tmp;

        if (this.size++ == 0) {
            front = back;
        }

        return true;
    }

    public T dequeue() {
        if (size == 0)
            throw new java.util.NoSuchElementException();

        Element tmp = back;
        while (size > 1 && tmp.next != front) {
            tmp = tmp.next;
        }

        T out = front.data;
        front = tmp;

        if (--size == 0)
            back = null;

        return out;
    }

    public int size() {
        return size;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public T elementAt(int index) {
        int i = 0;
        // reverse indexing
        index = size - index - 1;

        Element tmp = back;
        while (i++ < index) {
            tmp = tmp.next;
        }

        return tmp.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Element t = back;
        while (t != null) {
            sb.append(t.data);
            sb.append(" - ");

            t = t.next;
        }

        if (sb.length() > 0)
            sb.replace(sb.length() - 2, sb.length(), "").reverse();
        return sb.toString();
    }

    private class Element {
        T data;
        Element next;

        Element(T data) {
            this.data = data;
            this.next = null;
        }

        @Override
        public String toString() {
            return String.valueOf(data);
        }
    }
}
