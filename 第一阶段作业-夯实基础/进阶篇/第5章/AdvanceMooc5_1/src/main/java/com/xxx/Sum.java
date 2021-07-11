package com.xxx;

class Sum extends Thread{
    private Integer start;
    private Integer end;
    private long sum = 0;
    private Boolean sumk = false;
    public Boolean getSumk(){
        return sumk;
    }

    public long getSum() {
        return sum;
    }
    public Sum(Integer start,Integer end){
        this.start = start;
        this.end = end;
    }
    @Override
    public void run(){
        for(int i = start;i <= end;i++){
            sum+=i;
        }
        System.out.println(Thread.currentThread().getName()+"计算的数字为："+(end-start)+"个"+start+"--"+end+"相加为"+sum);
        sumk = true;
    }
}
