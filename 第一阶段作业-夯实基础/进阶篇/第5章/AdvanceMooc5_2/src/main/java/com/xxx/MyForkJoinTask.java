package com.xxx;


import java.util.concurrent.RecursiveTask;

public class MyForkJoinTask extends RecursiveTask<Integer> {
    int[] numbers;
    int startIndex;
    int endIndex;
    int targetNumber;

    public MyForkJoinTask(int[] numbers, int startIndex, int endIndex, int targetNumber){
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.targetNumber = targetNumber;
    }

    @Override
    protected Integer compute(){
        int cnt = 0;
        if(endIndex - startIndex < 1000){
            for(int i = startIndex;i <= endIndex; i++){
                if(numbers[i] == targetNumber)
                    cnt++;
            }
        }else{
            int midIndex = (startIndex + endIndex) / 2;
            MyForkJoinTask leftTask = new MyForkJoinTask(numbers,startIndex,midIndex,targetNumber);
            MyForkJoinTask rightTask = new MyForkJoinTask(numbers,midIndex,endIndex,targetNumber);
            invokeAll(leftTask,rightTask);
            cnt = rightTask.join()+leftTask.join();
        }
        return  cnt;
    }
}
