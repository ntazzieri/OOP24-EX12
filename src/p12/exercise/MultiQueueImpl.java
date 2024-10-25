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
    public final Map<Q, T> dequeueOneFromAllQueues() {
        final Map<Q, T> retMap = new HashMap<>();
        for(Q queue : queues.keySet()) {
            retMap.put(queue, queues.get(queue).poll());
        }
        return retMap;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        final Set<T> retSet = new HashSet<>();
        for(Q queue : queues.keySet()) {
            retSet.addAll(queues.get(queue));
        }
        return retSet;
    }

    @Override
    public List<T> dequeueAllFromQueue(Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        List<T> retList = new LinkedList<>();
        while(!queues.get(queue).isEmpty()) {
            retList.add(this.dequeue(queue));
        }
        return retList;
    }

    @Override
    public void closeQueueAndReallocate(Q queue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'closeQueueAndReallocate'");
    }

}
