package com.example.demo.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pc on 2018/12/19 10:29
 **/
@Component
public class MyJob implements Job {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(8);

    @Override
    public void execute(JobExecutionContext context) {
        try {
            int strategy = context.getMergedJobDataMap().getInt("strategy");

            System.out.println(strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("执行任务中");
    }





    public static void main(String[] args) throws InterruptedException {
        for (int i=0;i<1000;i++){
            threadPool.execute(new Test(i));
        }
        while (true){
            if (threadPool.isTerminated())break;
        }
        //Thread.sleep(10000);
    }

    static class Test implements Runnable{
        private  int a;

        Test(int i){
            this.a=i;
        }
        @Override
        public void run(){
            System.out.println(a);
        }
    }

}
