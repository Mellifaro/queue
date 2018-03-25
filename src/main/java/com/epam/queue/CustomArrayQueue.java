package com.epam.queue;

import java.util.*;

public class CustomArrayQueue<E> implements Queue<E> {
    private Object[] elementData;
    private int head = 0;
    private int tail = -1;
    private int size = 0;
    private int capacity;
    private int modCount;

    public CustomArrayQueue(int capacity) {
        elementData = new Object[capacity];
        this.capacity = capacity;
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean offer(E e) {
        if(size == capacity){
            throw new RuntimeException("Queue is overflowed");
        }
        size++;
        tail = incremetPositionOfArray(tail);
        elementData[tail] = e;
        modCount++;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove() {
        if(size == 0){
            throw new NoSuchElementException();
        }
        E removed = (E)elementData[head];
        elementData[head] = null;
        head = incremetPositionOfArray(head);
        size--;
        modCount++;
        return removed;
    }

    @Override
    //todo add functiomality for moving elements to left side
    public boolean remove(Object o) {
        for(Object element : elementData){
            if(Objects.equals(element, o)){
                element = null;
                modCount++;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E poll() {
        if(size == 0){
            return null;
        }else{
            E result = (E)elementData[head];
            elementData[head] = null;
            head = incremetPositionOfArray(head);
            size--;
            modCount++;
            return result;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E element() {
        if(size == 0){
            throw new NoSuchElementException();
        }
        return (E)elementData[head];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E peek() {
        if(size == 0){
            return null;
        }
        return (E)elementData[head];
    }

    private int incremetPositionOfArray(int number){
        return number == (capacity - 1) ? 0 : ++number;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return Arrays.stream(elementData).anyMatch(element -> element.equals(o));
    }

    @Override
    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elementData[index];
    }

    private class QueueIterator implements Iterator<E> {
        int cursor = head - 1;
        int currentModCount = modCount;
        int counter = 0;

        @Override
        public boolean hasNext() {
            if(counter == size){
                return false;
            }
            if(cursor == tail && size == capacity){
                return true;
            }
            return cursor != tail;
        }

        @Override
        public E next() {
            checkforModification();
            cursor = incremetPositionOfArray(cursor);
            counter++;
            return elementData(cursor);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void checkforModification(){
            if(currentModCount != modCount){
                throw new ConcurrentModificationException();
            }
        }
    }
}
