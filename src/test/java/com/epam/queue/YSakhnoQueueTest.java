package com.epam.queue;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

public class YSakhnoQueueTest {

    private Queue<String> queue;

    @Before
    public void setUp() {
        queue = new CustomArrayQueue<>(5);
    }

    @Test
    public void testIteratorOnFullQueue() {
        queue.add("Sunday");
        queue.add("Monday");
        queue.add("Tuesday");
        queue.add("Wednesday");
        queue.add("Thursday");

        assertEquals("Sunday", queue.remove());

        queue.add("Friday");

        assertEquals(5, queue.size());
        assertTrue("Iterator should function correctly on full queues", queue.iterator().hasNext());
    }

    @Test
    public void testIteratorIsFailFast() {
        queue.add("Monday");
        queue.add("Tuesday");
        queue.add("Wednesday");

        Iterator<String> it = queue.iterator();

        it.next();

        queue.remove("Tuesday");

        try {
            it.next();
            fail("Queue's iterator should be fail-fast, throwing ConcurrentModificationException");
        } catch (ConcurrentModificationException cme) {
            // eat
        }
    }
}
