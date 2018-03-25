package com.epam.queue;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.Assert.*;

public class CustomArrayQueueTest {
    private Queue<Item> queue;

    @Before
    public void init(){
        queue = new CustomArrayQueue<>(4);
    }

    @Test
    public void testSizeInitMethod(){
        assertEquals(0, queue.size());
    }

    @Test
    public void testAddMethodSuccess(){
        Item item = new Item(1, "Book");
        queue.add(item);
        assertEquals(1, queue.size());
    }

    @Test(expected = RuntimeException.class)
    public void testAddMoreElementsThanCapacityFailed(){
        queue.add(new Item(1, "Book 1"));
        queue.add(new Item(2, "Book 2"));
        queue.add(new Item(3, "Book 3"));
        queue.add(new Item(4, "Book 4"));
        queue.add(new Item(5, "Book 5"));
    }

    @Test
    public void testRemoveMethodSuccess(){
        queue.add(new Item(1, "Book 1"));
        queue.add(new Item(2, "Book 2"));
        Item item = queue.remove();
        System.out.println(item);
        assertEquals(1, queue.size());
    }

    @Test
    public void testPollMethodSuccess(){
        Item item = new Item(1, "Book 1");
        queue.add(item);
        Item result = queue.poll();
        assertEquals(item, result);
        assertEquals(0, queue.size());
    }

    @Test
    public void testPeekMethodSuccess(){
        Item item = new Item(1, "Book 1");
        queue.add(item);
        assertEquals(1, queue.size());
        Item result = queue.peek();
        assertNotNull(result);
        assertEquals(1, queue.size());
    }

    @Test
    public void testPollMethodReturnNullIfQueueIsEmpty(){
        Item item = queue.poll();
        assertNull(item);
    }

    @Test(expected = NoSuchElementException.class)
    public void testElementMethodThrowExceptionIfQueueIsEmpty(){
        Item item = queue.element();
    }

    @Test
    public void testIterator(){
        queue.add(new Item(1, "Book 1"));
        queue.add(new Item(2, "Book 2"));
        queue.add(new Item(3, "Book 3"));
        queue.remove();
        queue.remove();
        queue.add(new Item(1, "Book 1"));
        queue.add(new Item(2, "Book 2"));
        queue.add(new Item(3, "Book 3"));

        int counter = 0;
        for(Item item : queue){
            counter++;
        }
        assertEquals(4, counter);
    }
}
