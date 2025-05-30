package test.java;
import main.java.model.Service;

import java.util.ArrayList;
import java.util.List;
public class ServiceTest {
    private List<Service> services = new ArrayList<>();

    public void addService(Service service) {
        services.add(service);
        System.out.println("Da them dich vu: " + service);
    }

    public void listServices() {
        System.out.println("=== DANH SACH DICH VU ===");
        for (Service service : services) {
            System.out.println(service);
        }
    }
    public double calculateTotalServices(){
        double total = 0;
        for(Service service : services){
            total += service.getTotalPrice();
        }
        return total;
    }

    public static void main(String[] args) {
        ServiceTest st = new ServiceTest();
        st.addService(new Service("Nuoc suoi", 10000 , 5));
        st.addService(new Service("Snack", 20000 , 3));
        st.listServices();
        double total = st.calculateTotalServices();
        System.out.println("==> Tong tien dich vu: " + total + " VND");
    }
    
}
