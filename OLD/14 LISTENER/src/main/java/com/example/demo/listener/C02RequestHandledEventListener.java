package com.example.demo.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.web.context.support.RequestHandledEvent;

// SevletEventClass..
// SevletContextEvent
// SevletContextAttributeEvent
// RequestContextEvent..
//

public class C02RequestHandledEventListener implements ApplicationListener<RequestHandledEvent>{

	@Override
	public void onApplicationEvent(RequestHandledEvent event) {
		System.out.println("RequestHandledEventListener's onApplicationEvent "+event.getSource());
		
	}



}
