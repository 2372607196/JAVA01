import java.util.Arrays;

import java.util.Scanner;
class Currency {
    private String name;        //货币名称
    private int originalValue;  //原始值
    private int value;          //转换为人民币后的值
    public static String[] CURRENCY_NAME = { "CNY", "HKD", "USD", "EUR" };
    public static int[] CURRENCY_RATIO = { 100, 118, 15, 13 };
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOriginalValue() {
        return originalValue;
    }
    public void setOriginalValue(int originalValue) {
        this.originalValue = originalValue;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
class HKD extends Currency implements Comparable<Currency> {
    // 实现你的构造函数与Comparable中的接口
    HKD(int num){
        setName(CURRENCY_NAME[1]);
        setOriginalValue(num);
        int i = CURRENCY_RATIO[0];
        int j = CURRENCY_RATIO[1];
        float k =(float)i/j;
        float z = (int)num;
        setValue((int)(k*z));
    }
    public int compareTo(Currency o) {
        int i = 0;
        if(i ==0){
            return getValue()-o.getValue();
        }
        else {
            return i;
        }
    }
}
class USD extends Currency implements Comparable<Currency> {
    // 实现你的构造函数与Comparable中的接口
    USD(int num){
        setName(CURRENCY_NAME[2]);
        setOriginalValue(num);
        int i = CURRENCY_RATIO[0];
        int j = CURRENCY_RATIO[2];
        float k =(float)i/j;
        float z = (int)num;
        setValue((int)(k*z));
    }
    public int compareTo(Currency o){
        int i = 0;
        if(i==0){
            return getValue()-o.getValue();
        }
        else {
            return i;
        }
    }
}
class EUR extends Currency implements Comparable<Currency> {
    // 实现你的构造函数与Comparable中的接口
    EUR(int num) {
        setName(CURRENCY_NAME[3]);
        setOriginalValue(num);
        int i = CURRENCY_RATIO[0];
        int j = CURRENCY_RATIO[3];
        float k =(float)i/j;;
        float z = (int)num;
        setValue((int)(k*z));
    }

    public int compareTo(Currency o) {
        int i = 0;
        if (i == 0) {
            return getValue() - o.getValue();
        } else {
            return i;
        }
    }
}
public class mooc10 {
    public static void main(String[] args) {
        Currency[] cs = new Currency[3];
        //初始化
        Scanner sc = new Scanner(System.in);
        //利用hasNextXXX()判断是否还有下一输入项
        int a = 0;
        int b = 0;
        int c = 0;
        if (sc.hasNext()) {
            a = sc.nextInt();
            cs[0] = new HKD(a);
        }
        if (sc.hasNext()) {
            b = sc.nextInt();
            cs[1] = new USD(b);
        }
        if (sc.hasNext()) {
            c = sc.nextInt();
            cs[2] = new EUR(c);
        }
        //初始化结束
        Arrays.sort(cs);
        for(Currency currency:cs){
            System.out.println(currency.getName()+currency.getOriginalValue());
        }



        //请补充排序





        //请补充输出结果

    }

}