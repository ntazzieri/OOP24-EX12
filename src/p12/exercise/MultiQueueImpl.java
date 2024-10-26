package p12.exercise;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Iterator;

public class MultiQueueImpl<T, Q> implements MultiQueue<T, Q>{

    private static final int MIN_QUEUES_AVAILABLE_FOR_RELOC = 1;
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
    public boolean isQueueEmpty(final Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        return queues.get(queue).isEmpty();
    }

    @Override
    public void enqueue(final T elem, final Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        queues.get(queue).add(elem);
    }

    @Override
    public T dequeue(final Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        return queues.get(queue).poll();
    }

    @Override
    public final Map<Q, T> dequeueOneFromAllQueues() {
        final Map<Q, T> retMap = new HashMap<>();
        for(final Q queue : queues.keySet()) {
            retMap.put(queue, queues.get(queue).poll());
        }
        return retMap;
    }

    @Override
    public Set<T> allEnqueuedElements() {
        final Set<T> retSet = new HashSet<>();
        for(final Q queue : queues.keySet()) {
            retSet.addAll(queues.get(queue));
        }
        return retSet;
    }

    @Override
    public List<T> dequeueAllFromQueue(final Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        final List<T> retList = new LinkedList<>();
        while(!queues.get(queue).isEmpty()) {
            retList.add(this.dequeue(queue));
        }
        return retList;
    }

    @Override
    public void closeQueueAndReallocate(final Q queue) {
        if(!queues.containsKey(queue)) {
            throw new IllegalArgumentException();
        }
        if(queues.size() <= MIN_QUEUES_AVAILABLE_FOR_RELOC) {
            throw new IllegalStateException();
        }
        final List<T> elemsToSpread = this.dequeueAllFromQueue(queue);
        queues.remove(queue);
        spreadElemsToAvaibleQueues(queue, elemsToSpread); 
    }

    private void spreadElemsToAvaibleQueues(final Q queueToSpread, final List<T> elemsToSpread) {
        Iterator<Q> keySetIterator = null;
        while(!elemsToSpread.isEmpty()) {
            if(keySetIterator == null || !keySetIterator.hasNext()) {
                keySetIterator = queues.keySet().iterator();
            } else {
                Q queueToInsert = keySetIterator.next();
                if(queueToInsert != queueToSpread) {
                    this.enqueue(elemsToSpread.removeFirst() , queueToInsert);
                }
            }
        }
    }

}
