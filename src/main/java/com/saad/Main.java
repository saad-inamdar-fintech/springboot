package com.saad;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saad.Customer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/customers")
public class Main {

    private final CustomerRespository customerRepository;

    public Main(CustomerRespository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
       SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    //This can be done via @Pathvariable as (@PathVariable int id)
    public Customer getCustomer(@RequestParam int id){
        return customerRepository.findById(id).get();
    }

    @GetMapping("/error")
    public String getError(){
        return "Some Error Occured";
    }

    record NewCustomerRequest(
        String name,
        String email,
        Integer age
    ){

    }
    @PostMapping
    public String addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setName(request.name());
        customer.setAge(request.age());
        customer.setEmail(request.email());
        customerRepository.save(customer);
        return "Customer Saved Successfully";
    }

    
    @PostMapping("/saveCustomers")
    public String addCustomerNew(@RequestBody List<Customer> customer){
        customerRepository.saveAll(customer);
        return "Customer Saved Successfully";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id){
        customerRepository.deleteById(id);
        return "Customer Deleted Successfully";
    }

    @DeleteMapping("/deleteAllCustomer")
    public String deleteCustomers(@RequestBody Customer customer){
        customerRepository.delete(customer);
        return "Customer Deleted Successfully";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll(){
        customerRepository.deleteAll();
        return "Customer Deleted Successfully";
    }

    @PutMapping
    public String update(@RequestBody Customer customer){
        Customer oldCustomer = customerRepository.findById(customer.getId()).orElse(null);
        oldCustomer.setName(customer.getName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setAge(customer.getAge());
        System.out.println(oldCustomer);
        return "Customer Updated Successfully";
    }

    @PatchMapping
    public String updatePatch(@RequestBody Customer customer){
        Customer oldCustomer = customerRepository.findById(customer.getId()).orElse(null);
        oldCustomer.setName(customer.getName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setAge(customer.getAge());
        customerRepository.save(oldCustomer);
        return "Customer Deleted Successfully";
    }



      
}
