package main.java.main;

import main.java.model.Customer;

public class App {
    public static void main(String[] args){
        Customer human = new Customer("Duy Bao", "23017159", "0326989362");
        System.out.println("Ho va ten: "+ human.getName() + " MSV: "+ human.getIdCard() + " Phone " + human.getPhone());
    }
}
