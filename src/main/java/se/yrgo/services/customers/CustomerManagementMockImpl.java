package se.yrgo.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
    private HashMap<String, Customer> customerMap;

    public CustomerManagementMockImpl() {
        customerMap = new HashMap<String, Customer>();
        customerMap.put("OB74", new Customer("OB74", "Fargo Ltd", "some notes"));
        customerMap.put("NV10", new Customer("NV10", "North Ltd", "some other notes"));
        customerMap.put("RM210", new Customer("RM210", "River Ltd", "some more notes"));
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        if (customerMap.containsKey(newCustomer.getCustomerId())) {
            throw new IllegalArgumentException("Customer already exists");
        }

        customerMap.put(newCustomer.getCustomerId(), newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException {
        if (!customerMap.containsKey(changedCustomer.getCustomerId())) {
            throw new CustomerNotFoundException("Customer " + changedCustomer.getCustomerId() + " does not exist.");
        }

        customerMap.put(changedCustomer.getCustomerId(), changedCustomer);
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException {
        if (!customerMap.containsKey(oldCustomer.getCustomerId())) {
            throw new CustomerNotFoundException("Customer " + oldCustomer.getCustomerId() + " not found!");
        }

        customerMap.remove(oldCustomer.getCustomerId());
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        if (!customerMap.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer " + customerId + " not found!");
        }
        return customerMap.get(customerId);
    }

    @Override
    // Customer has no name field. I can only assume you mean company name.
    public List<Customer> findCustomersByName(String name) {
        List<Customer> matchedCustomers = new ArrayList<>();

        for (Customer customer : customerMap.values()) {
            if (customer.getCompanyName().equals(name)) {
                matchedCustomers.add(customer);
            }
        }

        return matchedCustomers;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        if (!customerMap.containsKey(customerId)) {
            throw new CustomerNotFoundException("Customer " + customerId + " not found!");
        }
        return customerMap.get(customerId);
    }

    @Override
    public void resetAllData() {

    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        Customer customer = customerMap.get(customerId);
        customer.addCall(callDetails);
    }

}
