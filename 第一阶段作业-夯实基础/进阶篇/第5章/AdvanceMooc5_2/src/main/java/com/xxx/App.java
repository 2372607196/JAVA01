package com.xxx;

import java.util.concurrent.*;


public class App 
{
    public static void main( String[] args ) throws InterruptedException, ExecutionException
    {
        int[] numbers = new int[100450];
        int targetNumber = 50;
        for(int i = 0; i < numbers.length; i++){
            numbers[i] = (int)(Math.random()*101);
        }
        System.out.println("findBySerial = " + findBySerial(numbers, targetNumber));
        System.out.println("findByExecutor = " + findByExecutor(numbers, targetNumber));
        System.out.println("findByForkJoin = " + findByForkJoin(numbers, targetNumber));
    }
    //串行循环计算
    public static int findBySerial(int[] numbers,int targetNumber){
        int cnt = 0;
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] == targetNumber){
                cnt++;
            }
        }
        return cnt;
    }
    //Executor框架实现
    public static int findByExecutor(int[] numbers,int targetNumber)throws InterruptedException{
        ExecutorService executor = Executors.newCachedThreadPool();//创建线程池
        int increment = 1000; //每1000长度为一组
        int threadNum = numbers.length/increment;
        CountDownLatch latch = new CountDownLatch(threadNum);//创建闭锁，等待线程

        int startIndex = 0, endIndex = increment - 1;
        for(int i = 0; i < threadNum; i++){
            if(i == threadNum - 1)
                endIndex = numbers.length - 1;
            executor.execute(new ExecutorTask(numbers,startIndex,endIndex,targetNumber,latch));
            startIndex = endIndex + 1;
            endIndex = endIndex + increment;
        }
        latch.await();
        executor.shutdown();
        return ExecutorTask.getSumCnt();
    }

    public static int findByForkJoin(int[] numbers, int targetNumer)throws InterruptedException,ExecutionException{
        ForkJoinPool pool = new ForkJoinPool();//创建Fork-Join线程池
        ForkJoinTask<Integer> task = new MyForkJoinTask(numbers,0,numbers.length-1,targetNumer);
        task = pool.submit(task);
        Integer cnt =  task.get();
        pool.shutdown();
        return cnt;
    }
}
