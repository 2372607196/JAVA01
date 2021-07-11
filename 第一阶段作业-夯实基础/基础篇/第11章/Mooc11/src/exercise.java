import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.lang.String;


class Mycomparator implements Comparator<Map.Entry<String,Integer>>{
    public int compare(HashMap.Entry<String,Integer>he1,HashMap.Entry<String,Integer>he2){
        return he2.getValue()-he1.getValue();
    }
}
public class exercise {
    public static void main(String[] args) {
        HashMap<String,Integer>hm = new HashMap<String,Integer>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\学习资料\\java\\a.txt")))) {
            String line;
            while((line=br.readLine())!=null){
                Pattern p = Pattern.compile("\\s+");//正则匹配空格
                String words[] = p.split(line); //获取的字符串进行分割单词
                for(String item : words){
                    if(item.length()!=0&&!item.equals("============")&&!item.equals("==============")){
                        if(hm.get(item)==null)
                            hm.put(item,1);//将数据放入map中
                        else
                            hm.put(item,hm.get(item)+1);
                    }
                }
            }
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }
        //HashMap转换位list,进行排序
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(hm.entrySet());
        Collections.sort(list,new Mycomparator());
        //将数据写入文件
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\学习资料\\java\\b.txt")))){
            for(Map.Entry<String,Integer>item:list){
                bw.write(item.getKey()+","+item.getValue());
                bw.newLine();
            }
    }catch(Exception ex){
            System.err.println(ex.getMessage());
    }
    }

}
