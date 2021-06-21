package com.codegym.repository;

import com.codegym.model.Customer;
import com.codegym.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    Iterable<Customer> findAllByProvince(Province province);

//    @Query(value = "select * " +
//            "from customer " +
//            "where customer.name like ?", nativeQuery = true)
//    List<Customer> findCustomerByName(String name);

    Page<Customer> findAllByNameContaining(String name, Pageable pageable);
}
