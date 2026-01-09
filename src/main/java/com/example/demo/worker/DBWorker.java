package com.example.demo.worker;

import com.example.demo.model.Newsfeed;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
@Component
public class DBWorker {
    private BlockingQueue<Dbtask> queue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @PostConstruct
    public void start()
    {
        executorService.submit(()->{
            while(true){
                try {
                    Dbtask task = (Dbtask)queue.take();
                    task.execute();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void submit(Dbtask task)
    {
        queue.offer(task);
    }
}
