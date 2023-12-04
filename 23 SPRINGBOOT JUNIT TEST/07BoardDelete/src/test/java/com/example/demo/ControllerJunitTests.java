package com.example.demo;




import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.controller.BoardController;
import com.example.demo.domain.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

//----------------------------------------------------------------
//
//----------------------------------------------------------------
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// 단위테스트 관련 정리 - https://velog.io/@hjun0917/SpringBootTest-vs-WebMvcTest
// Controller테스트 - https://velog.io/@beomsun/Controller%EA%B3%84%EC%B8%B5-%EB%8B%A8%EC%9C%84%ED%85%8C%EC%8A%A4%ED%8A%B8
@WebMvcTest(BoardController.class)
public class ControllerJunitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @DisplayName("GET /board/list 테스트")
    @Test
    public void t1() throws Exception {
        //given(값 지정 : 파라미터설정 ex 회원가입정보 등등)

        //when(조건  설정)

        //then(실행 & 확인)
        mockMvc.perform
                        (
                            get("/board/list")
                        )

                .andExpect(status().isOk());

        verify(boardService).save();
    }


}
