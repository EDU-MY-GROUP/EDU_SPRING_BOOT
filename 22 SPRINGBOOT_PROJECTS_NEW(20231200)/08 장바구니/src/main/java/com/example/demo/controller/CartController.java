package com.example.demo.controller;


import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.domain.dto.CartDto;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/add")
    public @ResponseBody void add(CartDto dto) throws Exception {
        log.info("GET /cart/add " + dto);

        //유효성 체크(생략)

        //서비스 실행
        boolean isadded =  cartService.addCart(dto);
    }

    @GetMapping("/read")
    public void read() throws Exception {
        log.info("GET /cart/read");
    }


}
