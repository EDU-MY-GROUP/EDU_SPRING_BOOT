package com.example.demo.C06Payment;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/th/pay")

public class PortOneApiController {
    //[메인페이지] https://portone.io/korea/ko/service
    //[참고] https://velog.io/@seowj0710/Spring-%EC%95%84%EC%9E%84%ED%8F%AC%ED%8A%B8iamport%EB%A1%9C-%EA%B2%B0%EC%A0%9C-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%8
    //[개발문서]

    @GetMapping("/index")
    public void index() {
        log.info("GET /pay/index");
    }
    //Access Token 발급
    //https://developers.portone.io/docs/ko/api/rest-api-access-token

}




// 사업자등록없이(개인사업자) 결제 API 사용법
// https://www.sixshop.com/official-blog/%EC%82%AC%EC%97%85%EC%9E%90%EB%93%B1%EB%A1%9D-%EC%97%86%EC%9D%B4-%EC%98%A8%EB%9D%BC%EC%9D%B8-%ED%8C%90%EB%A7%A4%ED%95%98%EB%8A%94-%EA%B0%80%EC%9E%A5-%EB%B9%A0%EB%A5%B8-%EB%B0%A9%EB%B2%95-ftabx6
