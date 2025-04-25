import java.util.Scanner;

public class Test1 {
    public static void main(String[] args) {
        System.out.println("Nhap ten cua ban :");
        String ten = new Scanner(System.in).nextLine();
        System.out.println("Nhap nam sinh cua ban :");
        int nam = new Scanner(System.in).nextInt();
        System.out.println("Nhap thang sinh cua ban :");
        int thang = new Scanner(System.in).nextInt();
        System.out.println("Nhap ngay sinh cua ban :");
        int ngay = new Scanner(System.in).nextInt();
        System.out.println("Ten cua ban la : "+ ten);
        System.out.println("Ngay sinh : "+ ngay + "-" + thang + "-" + nam);
        int update = 2025;
        int tuoi = update - nam;
        System.out.println("Nam nay ban duoc "+ tuoi + " tuoi");
        String kq = (tuoi > 60) ? ("nguoi gia") : ((tuoi <= 60 && tuoi > 45) ? ("trung nien") : ((tuoi <= 45 && tuoi > 18) ? ("thanh nien") : ("thieu nien")));
        System.out.println("Ban la 1 "+ kq);
        System.out.println("Nhap ngay hien tai :");
        int newDay = new Scanner(System.in).nextInt();
        System.out.println("Nhap thang hien tai :");
        int newMonth = new Scanner(System.in).nextInt();
        System.out.println("Nhap nam hien tai :");
        int newYear = new Scanner(System.in).nextInt();
        int totalDay;
        // if (newMonth == 1 || newMonth == 3 || newMonth == 5 || newMonth == 7 || newMonth == 8 || newMonth == 10 || newMonth == 12){
        //     totalDay = 31;
        // }else if(newMonth == 4 || newMonth == 6 || newMonth == 9 || newMonth == 11){
        //     totalDay = 30;
        // }else {
        //     totalDay = 28;
        // }
        // if newMonth < thang {

        // } 
    }
}
