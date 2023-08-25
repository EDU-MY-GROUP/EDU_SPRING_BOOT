package com.example.demo.RestfulController.C04Naver;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
@RequestMapping("/naver")
public class C99NaverDynamicMapController {
    


//    [참고] https://developers.naver.com/docs/serviceapi/search/local/local.md
//    [개발가이드] https://navermaps.github.io/maps.js.ncp/docs/tutorial-2-Getting-Started.html
//    [참고]  https://velog.io/@silverbeen/Naver-Map-%EC%9E%90%EC%9C%A0%EB%A1%AD%EA%B2%8C-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0
//    [파일 복사 GITHUB] https://github.com/navermaps/maps.js.ncp

    private final String NAVER_API_URL = "https://openapi.naver.com/v1/search/local.json";
    private final String NAVER_CLIENT_ID = "s0femem0gn"; // 네이버 개발자 센터에서 발급받은 클라이언트 ID
    private final String NAVER_CLIENT_SECRET = "SUwuByccz8qwPaALU1SaPKTp9q73Qkg4Xq0yFF7U"; // 네이버 개발자 센터에서 발급받은 클라이언트 시크릿




    @GetMapping("/map")
    public void searchLocal() {

    }







}
