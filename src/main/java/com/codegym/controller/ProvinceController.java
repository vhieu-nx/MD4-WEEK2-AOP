package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import com.codegym.service.CustomerService;
import com.codegym.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/provinces")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ModelAndView listProvinces(){
        Iterable<Province> provinces = provinceService.findAll();
        ModelAndView modelAndView = new ModelAndView("province/list");
        modelAndView.addObject("provinces",provinces);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("province/add");
        modelAndView.addObject("province",new Province());
        modelAndView.addObject("message", "Province add successfully");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView saveProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("province/add");
        modelAndView.addObject("province",new Province());
        return modelAndView;
    }

    @GetMapping("edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Province province = provinceService.findById(id);
        if (province != null){
            ModelAndView modelAndView = new ModelAndView("province/edit");
            modelAndView.addObject("province",province);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public ModelAndView updateProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("/province/edit");
        modelAndView.addObject("province", province);
        modelAndView.addObject("message", "Province updated successfully");
        return modelAndView;
    }

    @GetMapping("delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Province province = provinceService.findById(id);
        if (province != null){
            ModelAndView modelAndView = new ModelAndView("province/delete");
            modelAndView.addObject("province",province);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteProvince(@ModelAttribute("province") Province province){
        provinceService.remove(province.getId());
        return "redirect:/provinces";
    }

    @GetMapping("view/{id}")
    public ModelAndView viewProvince(@PathVariable("id") Long id){
        Province province = provinceService.findById(id);
        if (province == null){
            return new ModelAndView("/error.404");
        }
        Iterable<Customer> customers = customerService.findAllByProvince(province);

        ModelAndView modelAndView = new ModelAndView("/province/view");
        modelAndView.addObject("province", province);
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
}
