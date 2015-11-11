package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Customer;
import com.example.domain.JsonData;
import com.example.service.CustomerService;
import com.example.service.LoginUserDetails;

@Controller
@RequestMapping(value="ajax")
public class AjaxController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Customer create(@RequestBody JsonData jsonData, @AuthenticationPrincipal LoginUserDetails userDetails) {

        // 新規作成処理
        Customer customer = new Customer();
        customer.setLastName(jsonData.getLastName());
        customer.setFirstName(jsonData.getFirstName());
        customer.setUser(userDetails.getUser());
        customerService.createAjax(customer);
        return customer;
    }

}
