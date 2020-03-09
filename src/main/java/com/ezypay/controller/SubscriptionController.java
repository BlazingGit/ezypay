package com.ezypay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezypay.model.SubscriptionModel;
import com.ezypay.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;

	@PostMapping(value = "add", produces = {"application/json"})
    public SubscriptionModel addSubsription(@RequestBody SubscriptionModel model) {
		SubscriptionModel result = null;
        try {
			return subscriptionService.addSubscription(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
	
}
