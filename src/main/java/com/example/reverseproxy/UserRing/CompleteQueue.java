package com.example.reverseproxy.UserRing;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public class CompleteQueue<E> {
        Queue<E> CQ;

    public CompleteQueue() {
        this.CQ = new LinkedList<>();
    }

    void enqueue(E e) {
            CQ.offer(e);
        }

        E dequeue() {
            return CQ.poll();
        }

        boolean isEmpty() {
            return CQ.isEmpty();
        }

    }
