package com.fpt.OnlineQuiz.controller;

import com.fpt.OnlineQuiz.service.MailService;
import com.fpt.OnlineQuiz.service.implement.MailServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

@Controller
@RequestMapping(path = "/contact")
public class ContactController {

//    private JavaMailSender emailSender;

    @Autowired
    private MailService mailService;

    @GetMapping(path = "/create")
    public String showContactPage(ModelMap modelMap){
        return "contact_page";
    }

    @PostMapping(path = "/save")
    public String sendContactToEmail(HttpServletRequest request, ModelMap modelMap) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String topic = request.getParameter("topic");
        String message = request.getParameter("message");

        try {
            mailService.sendContactEmail("lebatuyen562@gmail.com", name, email, topic, message);
//            mailService.sendContactEmail();
            modelMap.addAttribute("msg", "Send email Successful!");
        }catch (MailException exception){
            modelMap.addAttribute("msg", "Send email fail");
            System.out.println("Error sand email: "+exception.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "contact_page";
    }
}
