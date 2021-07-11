package com.xxx;

import java.util.concurrent.CountDownLatch;

class ExecutorTask implements Runnable{
    private static int sumCnt = 0;
    private int[] numbers;
    private int startIndex;
    private int endIndex;
    private int targetNumber;
    private CountDownLatch latch;

    public ExecutorTask(int[] numbers,int startIndex, int endIndex, int targetNumber, CountDownLatch latch){
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.targetNumber = targetNumber;
        this.latch = latch;
    }

    @Override
    public void run(){
        int cnt = 0;
        for(int i = startIndex; i <= endIndex;i++){
            if(numbers[i] == targetNumber){
                cnt++;
            }
        }

        synchronized (ExecutorTask.class){
            sumCnt += cnt;
        }
        latch.countDown();
    }

    public static int getSumCnt(){
        return sumCnt;
    }
}
