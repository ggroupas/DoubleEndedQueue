package groupas.queue;

import groupas.queue.domain.DeQueue;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class DoubleEndedQueue<E> implements DeQueue<E> {

    private final int DEFAULT_SIZE = 8;
    private final int LOWER_LIMIT_SIZE = 4;

    private E[] array;
    private int size = DEFAULT_SIZE;
    private int front = -1, rear = -1;
    private int count = 0;

    public DoubleEndedQueue() {
        this.array = (E[]) new Object[size];

    }

    @Override
    public void pushFirst(E elem) {
        if (isFull())
            grow();

        if (front == -1) {
            front = rear = 0;
        }else {
            front = (front - 1 + size) % size;
        }
        this.array[front] = elem;
        count++;
    }

    @Override
    public void pushLast(E elem) {
        if (isFull())
            grow();

        if (front == -1) {
            front = rear = 0;
        }else {
            rear = (rear + 1) % size;
        }
        array[rear] = elem;
        count++;
    }

    @Override
    public E popFirst() {
        if (isEmpty())
            throw new NullPointerException("Queue is empty");

        var returnValue = this.array[front];
        this.array[front] = null;
        front = (front + 1 + size) % size;
        count--;

        if (isOnlyQuarterFull())
            shrink();

        return returnValue;
    }

    @Override
    public E popLast() {
        if (isEmpty())
            throw new NullPointerException("Queue is empty");

        var returnValue = this.array[rear];
        this.array[rear] = null;
        rear = (rear - 1 + size) % size;
        count--;

        if (isOnlyQuarterFull())
            shrink();

        return returnValue;
    }

    @Override
    public E first() {
        if (isEmpty())
            throw new NullPointerException("Queue is empty");

        return array[front];
    }

    @Override
    public E last() {
        if (isEmpty())
            throw new NullPointerException("Queue is empty");

        return array[rear];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull(){
        return count == size;
    }

    private boolean isOnlyQuarterFull(){
        return size > LOWER_LIMIT_SIZE && count <= (size / 4);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        front = rear = -1;
        Arrays.fill(array, null);
    }


    private void grow(){
        var newArray = (E[]) new Object[size * 2];

        var it  = iterator();
        var newArrayIndex = size / 2; //we add this padding
        front = newArrayIndex;
        rear = front + count - 1;
        while (it.hasNext()) {
            newArray[newArrayIndex++] = it.next();
        }

        size *= 2;
        array = newArray;
    }

    private void shrink(){
        if (array.length <=4)
            return;

        var newArray = (E[]) new Object[size / 2];

        var it = iterator();
        var newArrayIndex = size / 4; //we add this padding
        front = newArrayIndex;
        rear = front + count - 1;
        while (it.hasNext()) {
            newArray[newArrayIndex++] = it.next();
        }

        size /= 2;
        array = newArray;
    }
    @Override
    public Iterator<E> iterator() {
        return new Iterator() {
            int currentIndex = front;
            int remaining = count;

            final E[] arraySnapshot = array.clone();

            @Override
            public boolean hasNext() {
                return remaining > 0;
            }

            @Override
            public E next() {

                if (!Arrays.equals(arraySnapshot, array))
                    throw new ConcurrentModificationException();

                remaining--;
                return array[(currentIndex++ + size ) % size];
            }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator() {
            int currentIndex = rear;
            int remaining = count;

            final E[] arraySnapshot = array.clone();

            @Override
            public boolean hasNext() {
                return remaining > 0;
            }

            @Override
            public E next() {
                if (!Arrays.equals(arraySnapshot, array))
                    throw new ConcurrentModificationException();

                remaining--;
                return array[(currentIndex-- + size) % size];
            }
        };
    }

    public String toString() {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_PURPLE = "\u001B[35m";


        StringBuilder string = new StringBuilder();
        string.append(ANSI_PURPLE + "Count: " + count + ANSI_RESET + "\n")
                .append(ANSI_PURPLE + "Front: " + front + ANSI_RESET + "\n")
                .append(ANSI_PURPLE + "Rear: " + rear + ANSI_RESET +"\n")
                .append(ANSI_RED + "InternalView: " + ANSI_RESET);

        for (int i = 0; i < array.length; i++) {
            string.append(array[i] != null ? array[i] : "empty")
                    .append(", ");
        }
        string.replace(string.length() - 2, string.length(), "\n").toString();

        string.append(ANSI_CYAN + "ActualView: " + ANSI_RESET);
        var iterator = iterator();
        while (iterator.hasNext()){
            string.append(iterator.next()+ ", ");
        }
        return string.replace(string.length() - 2, string.length(), "\n").toString();
    }
}
