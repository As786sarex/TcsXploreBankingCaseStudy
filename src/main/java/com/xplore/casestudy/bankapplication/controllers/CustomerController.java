package com.xplore.casestudy.bankapplication.controllers;

import com.xplore.casestudy.bankapplication.models.Customer;
import com.xplore.casestudy.bankapplication.models.CustomerStatus;
import com.xplore.casestudy.bankapplication.services.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/create_customer")
    public String createCustomerPage(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("message", "");
        return "executive/create_customer";
    }

    @PostMapping(value = "/create_customer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addCustomer(Customer customer, Model model) {
        try {
            Customer response = customerService.saveCustomer(customer);
            model.addAttribute("message", "Successfully Added!!");
            return "executive/create_customer";
        } catch (RuntimeException e) {
            model.addAttribute("message", "Something went wrong!!");
            return "executive/create_customer";
        }
    }

    //update customer

    @GetMapping("/update_customer")
    public String updateCustomerPage(Model model) {
        Customer customer = new Customer();
        int index = -1;
        List<Customer> customers = customerService.getAllCustomer();
        model.addAttribute("customerList", customers);
        model.addAttribute("containerCustomer", customer);
        model.addAttribute("index", index);
        model.addAttribute("modelCustomer", customer);
        return "executive/update_customer";
    }

    @GetMapping("/update_customer/{id}")
    public String getUpdateCustomerDetails(@PathVariable("id") String id, Model model) {
        Customer customer = this.customerService.getCustomerById(id).orElseThrow(() -> {
            throw new RuntimeException("Customer does not exist!!");
        });
        model.addAttribute("modelCustomer", customer);
        return "executive/update_customer::resultCustomer";
    }

    @PostMapping("/update_customer")
    public String updateCustomerDetails(Customer customer, Model model) {
        System.out.println(customer);
        try {
            this.customerService.updateCustomer(customer);
            model.addAttribute("message", "Customer Successfully Updated!!");
        } catch (RuntimeException e) {
            model.addAttribute("message", "An error has occurred!!");
            System.out.println(e);
        }
        model.addAttribute("modelCustomer", customer);
        model.addAttribute("containerCustomer", new Customer());
        return "redirect:/customer/update_customer";
    }

    //delete customer
    @GetMapping("/delete_customer")
    public String deleteCustomerPage(Model model) {
        Customer customer = new Customer();
        List<Customer> customers = customerService.getAllCustomer();
        model.addAttribute("customerList", customers);
        model.addAttribute("containerCustomer", customer);
        model.addAttribute("modelCustomer", customer);
        return "executive/delete_customer";
    }

    @GetMapping("/delete_customer/{id}")
    public String getDeleteCustomerDetails(@PathVariable("id") String id, Model model) {
        Customer customer = this.customerService.getCustomerById(id).orElseThrow(() -> {
            throw new RuntimeException("Customer does not exist!!");
        });
        model.addAttribute("modelCustomer", customer);
        return "executive/delete_customer::resultCustomer";
    }

    @PostMapping("/delete_customer")
    public String deleteCustomerDetails(Customer customer, Model model) {
        System.out.println(customer);
        try {
            this.customerService.deleteCustomerById(customer);
            model.addAttribute("message", "Customer Successfully Updated!!");
        } catch (RuntimeException e) {
            model.addAttribute("message", "An error has occurred!!");
            System.out.println(e);
        }
        model.addAttribute("modelCustomer", customer);
        model.addAttribute("containerCustomer", new Customer());
        return "redirect:/customer/delete_customer";
    }

    //customer status

    @GetMapping("/customer_status")
    public String customerStatusPage(Model model){
        List<CustomerStatus> statuses=this.customerService.getAllCustomerStatus();
        model.addAttribute("statusList",statuses);
        return "executive/customer_status";
    }




}
