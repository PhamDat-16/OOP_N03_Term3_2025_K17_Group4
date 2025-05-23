import java.util.ArrayList;
package test.java;
import main.java.model.Customer;

public class CustomerList {

    ArrayList<Customer> customers = new ArrayList<Customer>();

    public ArrayList<Customer> addCustomer(Customer customer) {
        customers.add(customer);
        return customers;
    }

    public ArrayList<Customer> getEditCustomer(String newName, String idCard) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getIdCard() == (idCard)) {
                System.out.println("true");
                customers.get(i).setName(newName);
            }
        }
        return customers;
    }

    public ArrayList<Customer> getDeleteCustomer(String idCard) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getIdCard() == (idCard)) {
                customers.remove(i);
                break;
            }
        }
        return customers;
    }

    public void printCustomerList() {
        int len = customers.size();
        for (int i = 0; i < len; i++) {
            Customer c = customers.get(i);
            System.out.println("Name: " + c.getName() + " ID Card: " + c.getIdCard() + " Phone: " + c.getPhone());
        }
    }
}
