package p12.exercise;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q>{

    private final Map<Q, Queue<T>> queues = new HashMap<>();

    @Override
    public Set<Q> availableQueues() {
        return new HashSet<Q>(queues.keySet());
    }

    @Override
    public void openNewQueue(final Q queue) {
        if(queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        queues.put(queue, new LinkedList<T>());
    }

    @Override
    public boolean isQueueEmpty(Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        return queues.get(queue).isEmpty();
    }

    @Override
    public void enqueue(T elem, Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        queues.get(queue).add(elem);
    }

    @Override
    public T dequeue(Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        return queues.get(queue).poll();
    }

    @Override
    public Map<Q, T> dequeueOneFromAllQueues() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dequeueOneFromAllQueues'");
    }

    @Override
    public Set<T> allEnqueuedElements() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'allEnqueuedElements'");
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dequeueAllFromQueue'");
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeQueueAndReallocate'");
    }

}
