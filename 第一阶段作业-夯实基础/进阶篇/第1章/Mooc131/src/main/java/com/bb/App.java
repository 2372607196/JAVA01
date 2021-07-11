package com.bb;
import net.sourceforge.pinyin4j.PinyinHelper;
public class App
{
    public static String PinYin(String HanZi){
        String result = null;
        if(null !=HanZi&&!"".equals(HanZi)){
            char[] charArray = HanZi.toCharArray();
            StringBuffer s = new StringBuffer();
            for(char c:charArray){
                String[] stringArray = PinyinHelper.toHanyuPinyinStringArray(c);
                if(null != stringArray){
                    s.append(stringArray[0].replaceAll("\\d",""));
                }
            }
            if(s.length()>0){
                result = s.toString();
            }
        }
        return result;
    }
    public static void main( String[] args )
    {
        System.out.println( PinYin("祖国万岁") );
    }
}
