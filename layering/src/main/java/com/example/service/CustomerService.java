package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;
import com.example.domain.User;
import com.example.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    CustomerRepository customerRepository;
    
    // 全件取得
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
    // 1件取得
    public Customer findOne(Integer id) {
        return customerRepository.findOne(id);
    }
    // 新規登録
    public Customer create(Customer customer, User user) {
        customer.setUser(user);
        return customerRepository.save(customer);
    }
    // 非同期での新規登録
    public Customer createAjax(Customer customer) {
        return customerRepository.save(customer);
    }
    // 更新
    public Customer update(Customer customer, User user) {
        customer.setUser(user);
        return customerRepository.save(customer);
    }
    // 削除
    public void delete(Integer id) {
        customerRepository.delete(id);
    }
    
}
