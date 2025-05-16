import java.util.Scanner;
public class de_quy{
    public static void main(String[] args){
        System.out.print("Nhap mot so nguyen duong: ");
        int number = new Scanner(System.in).nextInt();
        if(number < 0){
            System.out.println("Dieu kien cua number khong thoa man");
        }else{
            long kqua = deQuy(number);
            System.out.println("Giai thua cua "+ number + " la "+ kqua);
        }
    }
    public static long deQuy(int n){
        if(n == 0 || n == 1){
            return 1;
        } else{
            return n * deQuy(n-1);
        }
    }
}