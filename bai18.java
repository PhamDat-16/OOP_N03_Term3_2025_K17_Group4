import java.util.Scanner;

public class bai18 {
    public static void main(String[] args){
        String phim;
        do{
            System.out.println("Nhap 1 so a bat ky:");
            int a = new Scanner(System.in).nextInt();
            boolean var = true;
            if(a <= 1){
                var = false;
            } else{
                for(int i = 2 ; i <= a /2 ; i++){
                    if(a % i == 0){
                        var = false;
                        break;  
                    }
                }
            }
            if(var){
                System.out.println("So "+a+" la so nguyen to");
            }else{
                System.out.println("So "+a+" khong la so nguyen to");
            }
            System.out.println("Ban co muon tiep tuc chuong trinh (y/n)");
            phim = new Scanner(System.in).nextLine();
        } while(phim.equals("y"));
        System.out.println("Thoat chuong trinh");  
    }
}
