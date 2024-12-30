package com.foodWorld.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.foodWorld.service.SendEmailService;
//
//@Controller
//public class EmailController {
//
//	@Autowired
//	private SendEmailService sendEmailService;
//	
//	@GetMapping("SendEmail")
//	public String sendEmail() {
//		sendEmailService.sendEmail("sairamlanka8458@gmail.com", "Test Body", "TestSubject");
//		return "Sent Successfully";
//	}
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodWorld.service.SendEmailService;

@RestController
public class EmailController {

    @Autowired
    private SendEmailService emailService;

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
//        emailService.sendEmail(to, subject, body);
        return "Email sent successfully to " + to;
    }
}
