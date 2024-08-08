package com.example.reverseproxy.UserRing;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public class SubmissionQueue<E> {
    Queue<E> SQ;

    public SubmissionQueue() {
        this.SQ = new LinkedList<>();
    }

    void enqueue(E e) {
        SQ.offer(e);
    }

    E dequeue() {
        return SQ.poll();
    }

    boolean isEmpty() {
        return SQ.isEmpty();
    }
}
