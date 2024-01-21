import groupas.queue.DoubleEndedQueue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

class DoubleEndedQueueTest {

    @Test
    void defaultTest(){
        DoubleEndedQueue<Integer> q = new DoubleEndedQueue<>();
        assertTrue(q.isEmpty());
        int count = 100000;
        for (int i = 0; i < count; i++) {
            q.pushLast(i);
            assertEquals(q.size(), i + 1);
            assertTrue(q.first() == 0);
        }
        int current = 0;
        while (!q.isEmpty()) {
            assertTrue(q.first() == current);
            assertTrue(q.popFirst() == current);
            current++;
        }
        assertTrue(q.isEmpty());
    }

    @Test
    void verifyFirst(){
        var q = new DoubleEndedQueue<Integer>();
        q.pushFirst(1);

        assertEquals(1, q.first());
        assertEquals(1, q.popFirst());
    }

    @Test
    void verifyEmpty() {
        var q = new DoubleEndedQueue<Integer>();
        assertTrue(q.isEmpty());

        var size = q.size();
        for (int i = 0; i < size; i++) {
            assertFalse(q.isEmpty());
            q.popFirst();
        }

        assertTrue(q.isEmpty());
    }

    @Test
    void ensureProperOrder() {
        var queue = new DoubleEndedQueue<Integer>();

        queue.pushFirst(1);
        queue.pushFirst(2);
        queue.pushLast(3);

        var iterator = queue.iterator();
        var values = new ArrayList<>();

        while (iterator.hasNext()){
            values.add(iterator.next());
        }
        assertArrayEquals(new Integer[]{2, 1, 3}, values.toArray());
    }

    @Test
    void nullPointerExceptionIfEmpty() {
        var queue = new DoubleEndedQueue<Integer>();

        assertThrowsExactly(NullPointerException.class, queue::first);
        assertThrowsExactly(NullPointerException.class, queue::last);
        assertThrowsExactly(NullPointerException.class, queue::popFirst);
        assertThrowsExactly(NullPointerException.class, queue::popLast);
    }

    @Test
    void exceptionOnValuesAltered() {
        var queue = new DoubleEndedQueue<Integer>();
        queue.pushFirst(1);
        queue.pushFirst(2);

        var iterator = queue.iterator();

        queue.pushFirst(5);
        assertThrows(ConcurrentModificationException.class , iterator::next);
    }

    @Test
    void exceptionOnValuesAltered_SkipIfNotChanged(){
        var queue = new DoubleEndedQueue<Integer>();
        /*
        * we add plenty of values
        * so that the inner array doesn't shrink in the first pop
        * */
        queue.pushFirst(1);
        queue.pushFirst(1);
        queue.pushFirst(1);
        queue.pushFirst(1);
        queue.pushFirst(1);
        queue.pushFirst(1);

        var iterator = queue.iterator();

        queue.pushFirst(5);
        queue.popFirst();
        assertDoesNotThrow(iterator::next);
    }

}
