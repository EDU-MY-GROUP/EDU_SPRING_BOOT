package com.example.demo.domain.dto;


import lombok.Data;

import java.util.List;

@Data
public class PaymentDto {

    private Long id;

    //장바구니 상품 정보
    private List<String> cart_id;
    private String address;

    //IMPORT 결제 정보
    private String imp_uid;
    private String merchant_uid;
    private String pay_method;
    private String name;
    private String paid_amount;
    private String status;


}
