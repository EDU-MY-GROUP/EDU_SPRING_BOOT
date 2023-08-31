package com.example.demo.C03KakaoAPI;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/th/kakao/pay")
public class C04KakaoPayAPI {


    private final String ADMIN_KEY = "ADMIN KEY INSERT";

    @GetMapping("/index")
    public void payIndex(){
        System.out.println("GET /th/kakao/index");
    }

    @GetMapping("/req")
    public @ResponseBody PaymentResponse pay(){
        System.out.println("GET /th/kakao/req");
        //URL
        String url = "https://kapi.kakao.com/v1/payment/ready";
        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.add("Authorization", "KakaoAK "+ADMIN_KEY);

        //Parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid","TC0ONETIME");
        params.add("partner_order_id","partner_order_id");
        params.add("partner_user_id","partner_user_id");
        params.add("item_name","쵸코파이");
        params.add("quantity","1");
        params.add("total_amount","2200");
        params.add("vat_amount","200");
        params.add("tax_free_amount","0");
        params.add("approval_url","http://localhost:8080/th/kakao/pay/success");
        params.add("fail_url","http://localhost:8080/th/kakao/pay/fail");
        params.add("cancel_url","http://localhost:8080/th/kakao/pay/cancel");
        params.add("vat_amount","200");

        //header + parameter
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params,headers);

        //Request_Case1
//          RestTemplate rt = new RestTemplate();
//          ResponseEntity<String> response =  rt.exchange(url, HttpMethod.POST,entity,String.class);
//
//          System.out.println(response);
//          System.out.println(response.getBody());

        //Request_Case2
        RestTemplate rt = new RestTemplate();
        PaymentResponse response =  rt.postForObject(url,entity,PaymentResponse.class);
        System.out.println(response);

        return response;


    }


    //https://localhost:8080/th/kakao/paySuccess
    @GetMapping("/success")
    public void paySuccess(String pg_token){
        System.out.println("GET /th/kakao/success " + pg_token);
    }
    @GetMapping("/fail")
    public void payFail(String value){
        System.out.println("GET /th/kakao/fail " + value);
    }
    @GetMapping("/cancel")
    public void payCancel(String value){
        System.out.println("GET /th/kakao/cancel " + value);
    }

}



//-------------------------------------------------------------------



@Data
class PaymentResponse {
    private String tid;
    private boolean tms_result;
    private String next_redirect_app_url;
    private String next_redirect_mobile_url;
    private String next_redirect_pc_url;
    private String android_app_scheme;
    private String ios_app_scheme;
    private String created_at;

}


