package com.xxx;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
          Integer Max = 100000000;
          int threads = 6;
          Sum[] sums = new Sum[threads];
          for(int i = 0;i < threads;i++){
              int startInt = Max*i/threads+1;
              int endInt = Max*(i+1)/threads;
              sums[i] = new Sum(startInt,endInt);
              sums[i].start();
          }
          while(true){
              Boolean sumK = true;
              for(int i = 0;i < threads;i++){
                  sumK = sums[i].getSumk();
              }
              if(sumK){
                  break;
              }
              try{
                  Thread.sleep(1000);
              }catch (InterruptedException ex){
                  ex.printStackTrace();
              }
          }
          long sum=0;
          for(int i = 0;i<threads;i++){
              sum+=sums[i].getSum();
          }
          System.out.println("1到"+Max+"的总和为："+sum);

    }
}
