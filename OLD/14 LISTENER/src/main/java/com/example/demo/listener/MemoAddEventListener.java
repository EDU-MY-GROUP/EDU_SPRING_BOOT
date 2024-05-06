package com.example.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MemoAddEventListener implements ApplicationListener<MemoEvent> {
    @Override
    public void onApplicationEvent(MemoEvent event) {
        System.out.println("[EVENT] Memo Add  :" +event );
    }


}
