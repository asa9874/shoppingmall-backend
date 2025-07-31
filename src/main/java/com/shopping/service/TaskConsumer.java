package com.shopping.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TaskConsumer {

    @RabbitListener(queues = "taskQueue")
    public void receiveTask(String message) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000); // 1초 대기
            System.out.println("작업 " + (i + 1) + ": " + message + " - 완료!");
        }
    }
}