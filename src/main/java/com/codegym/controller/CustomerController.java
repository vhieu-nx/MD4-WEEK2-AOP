package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.CustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    ProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> provinces(){
        return provinceService.findAll();
    }

    //region INSERT
    @GetMapping("/add")
    public ModelAndView showInsertForm() {
        ModelAndView modelAndView = new ModelAndView("customer/add");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView insertCustomer(@ModelAttribute("customer") Customer customer) {
//    public ModelAndView insertCustomer(@RequestParam String name, @RequestParam String email, @RequestParam String address) {
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/add");
        modelAndView.addObject("message", "Customer created successfully");
        return modelAndView;

    }
    //endregion
    @GetMapping
    public ModelAndView showAllCustomer(@RequestParam("s")Optional<String> name, @PageableDefault(size = 5) Pageable pageable ){
        Page<Customer> customers;
        if(name.isPresent()){
            customers = customerService.findAllByNameContaining(name.get(), pageable);
        } else {
            customers = customerService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("customer/list");
        modelAndView.addObject("customers",customers);
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            ModelAndView modelAndView = new ModelAndView("customer/edit");
            modelAndView.addObject("customer", customer);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateCustomer(@ModelAttribute("customer") Customer customer){
        customerService.save(customer);
        ModelAndView modelAndView = new ModelAndView("customer/edit");
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("message", "Customer updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            ModelAndView modelAndView = new ModelAndView("customer/delete");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteCustomer(@ModelAttribute("customer") Customer customer){
        customerService.remove(customer.getId());
        return "redirect:/customers";
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detailCustomer(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        if(customer != null) {
            ModelAndView modelAndView = new ModelAndView("customer/detail");
            modelAndView.addObject("customer", customer);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }


}
