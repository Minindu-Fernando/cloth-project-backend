package edu.icet.crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.crm.entity.CustomerEntity;
import edu.icet.crm.model.Customer;
import edu.icet.crm.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ObjectMapper mapper;
    @Override
    public Customer presist(Customer customer) {
        CustomerEntity save = customerRepository.save(mapper.convertValue(customer, CustomerEntity.class));
        return mapper.convertValue(save, Customer.class);
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

}
