package service.custom;

import model.Customer;

import java.util.List;

public interface CustomerService {

    public boolean addCustomer(Customer customer);
    public  boolean updateCustomer(Customer customer);
    public Customer searchCustomer(String id);
    public boolean deleteCustomer(String id);
    List<Customer>getAll();
}
