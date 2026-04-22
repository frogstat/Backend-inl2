package se.yrgo.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

public class SimpleClient {

    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {

            CustomerManagementService customerService = container.getBean(CustomerManagementService.class);
            CallHandlingService callService = container.getBean(CallHandlingService.class);
            DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

            // Clear customer BKED from the database to ensure tests are accurate.
            clearDatabase(customerService);

            System.out.println("******** TEST 1 ********");
            System.out.println("Searching for customer that does not exist. Should throw CustomerNotFoundException();");

            try {
                customerService.findCustomerById("Hello");
                System.err.println("Failure!");
            } catch (CustomerNotFoundException e) {
                System.out.println("Success! Caught CustomerNotFoundException!");
            }

            System.out.println("************************");
            System.out.println();
            System.out.println("******** TEST 2 ********");
            System.out.println("Updating customer that does not exist. Should throw CustomerNotFoundException();");

            try {
                customerService.updateCustomer(new Customer("Hello", "I do not", "exist"));
                System.err.println("Failure!");
            } catch (CustomerNotFoundException e) {
                System.out.println("Success! Caught CustomerNotFoundException!");
            }
            System.out.println("************************");
            System.out.println();
            System.out.println("******** TEST 3 ********");
            System.out.println("Deleting customer that does not exist. Should throw CustomerNotFoundException();");

            try{
                customerService.deleteCustomer(new Customer("Hello", "I do not", "exist"));
                System.err.println("Failure!");
            } catch (CustomerNotFoundException e) {
                System.out.println("Success! Caught CustomerNotFoundException!");
            }

            System.out.println("************************");
            System.out.println();
            System.out.println("******** TEST 4 ********");
            System.out.println("Creating new customer, then finding it, then deleting it. Should not throw any exceptions");

            System.out.print("Creating customer with an ID of BKED... ");
            try{
                customerService.newCustomer(new Customer("BKED", "Stena Line", "A good man"));
                System.out.println("Success!");
            } catch (Exception e) {
                System.err.println("Failure! " + e.getMessage());
            }
            System.out.print("Trying to fetch customer with an ID of BKED... ");
            try{
                customerService.findCustomerById("BKED");
                System.out.println("Success!");
            } catch (Exception e){
                System.err.println("Failure!");
            }

            try{
                customerService.deleteCustomer(customerService.findCustomerById("BKED"));
                System.out.println("Success!");
            } catch (Exception e){
                System.err.println("Failure!");
            }

            System.out.println("************************");
            System.out.println();
            System.out.println("******** TEST 5 ********");
            System.out.println("Creating a 4 customers and fetching all:");

            try{
                customerService.newCustomer(new Customer("AAAA", "Netto", "fun"));
                customerService.newCustomer(new Customer("AAAB", "Elgiganten", "fun"));
                customerService.newCustomer(new Customer("AAAC", "YRGO", "fun"));
                customerService.newCustomer(new Customer("AAAD", "Netto", "fun"));

                customerService.getAllCustomers().forEach(System.out::println);
                System.out.println("Success!");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }



//            customerService.newCustomer(new Customer("CS03939", "Acme", "Good Customer"));
//
//            Call newCall = new Call("Larry Wall called from Acme Corp");
//            Action action1 = new Action("Call back Larry to ask how things are going", new GregorianCalendar(2016, 0, 0), "rac");
//            Action action2 = new Action("Check our sales dept to make sure Larry is being tracked", new GregorianCalendar(2016, 0, 0), "rac");
//
//            List<Action> actions = new ArrayList<Action>();
//            actions.add(action1);
//            actions.add(action2);
//
//            try {
//                callService.recordCall("CS03939", newCall, actions);
//            } catch (CustomerNotFoundException e) {
//                System.out.println("That customer doesn't exist");
//            }
//
//            System.out.println("Here are the outstanding actions:");
//            Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("rac");
//            for (Action next : incompleteActions) {
//                System.out.println(next);
//            }

        }
    }

    public static void clearDatabase(CustomerManagementService customerService) {
        try{
            customerService.deleteCustomer(customerService.findCustomerById("BKED"));
        } catch (Exception ignored){}

        try{
            customerService.deleteCustomer(customerService.findCustomerById("AAAA"));
        } catch (Exception ignored){}

        try{
            customerService.deleteCustomer(customerService.findCustomerById("AAAB"));
        } catch (Exception ignored){}

        try{
            customerService.deleteCustomer(customerService.findCustomerById("AAAC"));
        } catch (Exception ignored){}

        try{
            customerService.deleteCustomer(customerService.findCustomerById("AAAD"));
        } catch (Exception ignored){}
    }
}
