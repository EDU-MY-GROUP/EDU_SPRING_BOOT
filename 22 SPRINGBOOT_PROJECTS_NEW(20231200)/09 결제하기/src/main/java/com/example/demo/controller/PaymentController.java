package com.example.demo.controller;


import com.example.demo.domain.dto.PaymentDto;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.service.PaymentService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/payment")
public class PaymentController {


// -----------------------------------------------------------
// 결제 조회 관련 내용은 RESTAPI로 요청해야 한다
// -----------------------------------------------------------
// DOCUMENT : https://developers.portone.io/api/rest-v1/payment
// DOCUMNET - AccessToken사용  https://developers.portone.io/docs/ko/api/rest-api-access-token?v=v1#step-03--%ED%86%A0%ED%81%B0-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
//
// 관리자(결제내역확인) : https://classic-admin.portone.io/

    @Autowired
    private PaymentService paymentService;

    private PortOneResponseData portOneResponseData;



    @GetMapping("/read")
    public void read(Long[] id_arr, Model model) {
        log.info("GET /payment/read...id_arr ");
        for (Long id : id_arr) System.out.println(id);

        List<Cart> list = paymentService.getCartInfo(id_arr);


        list.forEach(cart -> System.out.println(cart));

        int totalPrice = 0;
        for (Cart cart : list) {
            totalPrice += cart.getAmount() * Integer.parseInt(cart.getImageBoard().getPrice());


        }

        List<Long> cart_id_list = new ArrayList<Long>();

        for (Long id : id_arr) {
            cart_id_list.add(id);

        }
        model.addAttribute("list", list);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart_id", cart_id_list);
    }


    @GetMapping("/add")
    public @ResponseBody void add(PaymentDto params) throws UnsupportedEncodingException {


        params.setAddress(URLDecoder.decode(params.getAddress(), "UTF-8"));
        params.setName(URLDecoder.decode(params.getName(), "UTF-8"));

        List<String> tmp = params.getCart_id();
        List<String> n_cart_id = new ArrayList<String>();
        for(String id : tmp)
        {
           n_cart_id.add(URLDecoder.decode(id, "UTF-8"));
        }
        params.setCart_id(n_cart_id);
        log.info("GET /payment/add ... dto " + params);

        boolean isadded =  paymentService.addPayment(params);


    }

    // -----------------------------------------------------------
    // 결제 조회 관련 내용은 RESTAPI로 요청해야 한다
    // -----------------------------------------------------------
    // DOCUMENT : https://developers.portone.io/api/rest-v1/payment
    // DOCUMNET - AccessToken사용  https://developers.portone.io/docs/ko/api/rest-api-access-token?v=v1#step-03--%ED%86%A0%ED%81%B0-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
    //
    // 관리자(결제내역확인) : https://classic-admin.portone.io/



//    //
//    //  PortOne AccessToken 받기
//    //
//    @GetMapping("/getAccessToken")
//    public @ResponseBody String getAccessToken(){
//        log.info("GET /payment/getAccessToken ");
//
//        //URL
//        String url = "https://api.iamport.kr/users/getToken";
//        //Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//        //Parameter
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("imp_key","1546549255738924");
//        params.add("imp_secret","Zjy29fdoI6cNNwIZYMrDX4dkLCLvf6HFyFbbVCNwlRD5YzHCEQV4onWbydWFVbT1ID1Zw0Kp6POYsvKg");
//
//
//        //header + parameter
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);
//
//        //Request_Case1
////      RestTemplate rt = new RestTemplate();
////      ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);
////
////      System.out.println(response);
////      System.out.println(response.getBody());
//
//        //Request_Case2
//        RestTemplate rt = new RestTemplate();
//        PortOneResponseData response =  rt.postForObject(url,entity,PortOneResponseData.class);
//        System.out.println(response);
//        this.portOneResponseData = response;
//
//        return "";
//    }
//
//
//
//
//    @GetMapping("/cancel/{imp_uid}")
//    public @ResponseBody ResponseEntity<String> Cancel(@PathVariable String imp_uid){
//        log.info("GET /payment/cancel ..." + imp_uid);
//        getAccessToken();
//        log.info("AccessToken : " + portOneResponseData.getResponse().getAccess_token());
//        String accessToken = portOneResponseData.getResponse().getAccess_token();
//        //URL
//        String url = "https://api.iamport.kr/payments/cancel";
//        //Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//        headers.add("Authorization", "Bearer " + accessToken);
//        //Parameter
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("imp_uid",imp_uid);
//
//        //header + parameter
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);
//
//        //Request_Case1
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);
//        System.out.println(response);
//        System.out.println(response.getBody());
//
//        //Db 삭제
//        paymentService.removePayment(imp_uid);
//
//
//        return new ResponseEntity("success", HttpStatus.OK);
//    }
}

//Access Token 받기 위한 클래스
@Data
class PortOneResponseData{
    public int code;
    public Object message;
    public Response response;

    @Data
    class Response{
        public String access_token;
        public int now;
        public int expired_at;
    }
}


