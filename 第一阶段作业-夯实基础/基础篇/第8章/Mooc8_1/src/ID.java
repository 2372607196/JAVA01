import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class ID {
    public static void main(String[] args) {
        Scanner val = new Scanner(System.in);
        System.out.print("请输入身份证号码：");
        String ID = val.nextLine();
        if(ID.length()!=18){
            System.out.println("0000-00-00");
        }
        else{
            int i,n=0;
            for(i = 0;i<17;i++){
                if(ID.charAt(i)>='0'&&ID.charAt(i)<='9')
                    n++;
                else
                    break;
            }
           if(n<=16)
               System.out.println("0000-00-00");
           else {
               int year, month, day;
               year = Integer.valueOf(ID.substring(6, 10));
               month = Integer.valueOf(ID.substring(10, 12));
               day = Integer.valueOf(ID.substring(12, 14));
                   String Date = ID.substring(6, 14);
                   String pat = "yyyy-mm-dd";//用来从时间对象转换为字符串得模板
                   String pat2 = "yyyymmdd";//用来转换为时间对象的模版
                   SimpleDateFormat sdf = new SimpleDateFormat(pat2);
                   SimpleDateFormat sdf2 = new SimpleDateFormat(pat);
                   java.util.Date d = null;
                   try {
                       d = sdf.parse(Date);
                       System.out.println(d);
                   } catch (Exception ex) {
                       ex.printStackTrace();
                   }
                   String r = sdf.format(d);
                   if(r.equals(Date)){
                   int j,m,sum = 0,mod;
                   int[] k ={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
                   for(j=0;j<17;j++)
                   {
                       m=Integer.valueOf(ID.substring(j,j+1));
                       sum+=k[j]*m;
                   }
                   mod=sum%11;
                   String mods = "10x98765432";
                   if(ID.charAt(17)==mods.charAt(mod))
                       System.out.println(sdf2.format(d));
                   else
                       System.out.println("0000-00-00");
                   }
                   else
                       System.out.println("0000-00-00");
               }
           }
        }
    }
