package com.example.web;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Customer;
import com.example.domain.JsonData;
import com.example.service.CustomerService;
import com.example.service.LoginUserDetails;

/**
 * 顧客情報を操作するコントローラー
 */
@Controller
@RequestMapping("customers")
public class CustomerController {
    
    @Autowired
    CustomerService customerService;
    
    // フォーム初期化 （マッピングされたメソッドの前に実行, 返り値は自動でModelに追加）
    @ModelAttribute
    CustomerForm setUpForm() {
        return new CustomerForm();
    }
    
    /**
     * 一覧表示メソッド
     * @return 顧客一覧表示画面
     */
    @RequestMapping(method = RequestMethod.GET)
    String list(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customers/list";
    }
    
    /**
     * 新規登録（Submit）メソッド
     * @param form 入力値
     * @param result Validate結果
     * @param userDetails ユーザー情報
     * @return 顧客一覧表示画面
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    String create(@Validated CustomerForm form, BindingResult result, Model model,
                    @AuthenticationPrincipal LoginUserDetails userDetails) {
        // 入力チェックでエラーがある場合は、一覧画面に戻る
        if (result.hasErrors()) {
            return list(model);
        }
        // 新規作成処理
        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        customerService.create(customer, userDetails.getUser());
        return "redirect:/customers";
    }
    
    /**
     * 非同期での新規登録処理
     * @param jsonData 入力値
     * @param userDetails ユーザー情報
     * @return 登録した顧客情報
     */
    @ResponseBody
    @RequestMapping(value = "/ajax/create", method = RequestMethod.POST, 
                    produces = MediaType.APPLICATION_JSON_VALUE) // JSON形式のデータを受け取るための設定
    public Customer ajaxCreate(@RequestBody JsonData jsonData, 
                                @AuthenticationPrincipal LoginUserDetails userDetails) {
        // 新規作成処理
        Customer customer = new Customer();
        customer.setLastName(jsonData.getLastName());
        customer.setFirstName(jsonData.getFirstName());
        customer.setUser(userDetails.getUser());
        customerService.createAjax(customer);
        return customer;
    }
 
    /**
     * 顧客情報編集画面遷移メソッド
     * @param id 編集対象のID
     * @param form 顧客情報
     * @return 編集画面
     */
    @RequestMapping(value = "edit", params = "form", method = RequestMethod.GET)
    String editForm(@RequestParam Integer id, CustomerForm form) {
        Customer customer = customerService.findOne(id);
        BeanUtils.copyProperties(customer, form);
        return "customers/edit";
    }
    
    /**
     * 顧客情報編集実行メソッド
     * @param id 編集対象のID
     * @param form 入力値
     * @param result Validate結果
     * @param userDetails ユーザー情報
     * @return 一覧表示へリダイレクト（POST → REDIRECT → GET）
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    String edit(@RequestParam Integer id, @Validated CustomerForm form, BindingResult result, 
                @AuthenticationPrincipal LoginUserDetails userDetails) {
        if (result.hasErrors()) {
            return editForm(id, form);
        }
        // 更新処理
        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        customer.setId(id);
        customerService.update(customer, userDetails.getUser());
        return "redirect:/customers";
    }
    
    /**
     * 削除メソッド
     * @param id 削除対象のID
     * @return 一覧表示へリダイレクト
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    String delete(@RequestParam Integer id) {
        customerService.delete(id);
        return "redirect:/customers";
    }
    
    /**
     * 一覧画面へ戻る
     * @return 一覧表示へリダイレクト
     */
    @RequestMapping(value = "edit", params = "goToTop")
    String goToTop() {
        return "redirect:/customers";
    }
}
