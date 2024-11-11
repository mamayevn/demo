package kg.asiamotors.demo.services;

import kg.asiamotors.demo.models.Customer;
import kg.asiamotors.demo.repasitories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    // Метод для сохранения покупателя
    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    // Метод для получения всех покупателей
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    // Метод для получения покупателя по ID
    public Customer findCustomerById(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    // Метод для удаления покупателя по ID
    public void deleteCustomerById(int id) {
        customerRepository.deleteById(id);
    }
}
