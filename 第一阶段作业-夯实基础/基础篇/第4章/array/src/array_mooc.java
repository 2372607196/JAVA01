public class array_mooc {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int n_all = n*n;
        for(int i = 1;i<=n_all;i++){
            System.out.print(i);
            if(0==i%n){
                System.out.println();
            }
            else{
                System.out.print('\t');
            }
        }
    }
}
