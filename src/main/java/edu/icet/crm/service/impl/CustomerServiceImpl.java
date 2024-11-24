package edu.icet.crm.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.entity.CustomerEntity;
import edu.icet.crm.model.Customer;
import edu.icet.crm.repository.CustomerRepository;
import edu.icet.crm.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ObjectMapper mapper;

    @Override
    public Customer persist(Customer customer) {
        Optional<CustomerEntity> existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }
        CustomerEntity save = customerRepository.save(mapper.convertValue(customer, CustomerEntity.class));
        return mapper.convertValue(save, Customer.class);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer login(String email, String password) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByEmail(email);
        if (customerEntityOptional.isPresent()){
            CustomerEntity customerEntity = customerEntityOptional.get();

            if (customerEntity.getPassword().equals(password)){
                return mapper.convertValue(customerEntity, Customer.class);
            }
        }
        return null;
    }

    @Override
    public boolean deleteCustomerIfCredentialsMatch(String email, String password) {
        Optional<CustomerEntity> customerEntityOptional = customerRepository.findByEmail(email);
        if (customerEntityOptional.isPresent()){
            CustomerEntity customerEntity = customerEntityOptional.get();
            if (customerEntity.getPassword().equals(password)){
                customerRepository.delete(customerEntity);
                return true;
            }
        }
        return false;
    }

}