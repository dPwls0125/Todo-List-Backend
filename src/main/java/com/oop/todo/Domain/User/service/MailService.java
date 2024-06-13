package com.oop.todo.Domain.User.service;


import com.oop.todo.Domain.User.entity.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    public static Random random = new Random();
    private HashMap<String, Integer> certificationMap = new HashMap<>();
    public void sendEmail( String toEmail){

        int certNum = random.nextInt(101,999);
        certificationMap.put(toEmail, certNum);

        String title = "[Todo App] 인증번호 발송";
        String text = "이메일 인증번호는 " +  certNum + " 입니다.";

        SimpleMailMessage message = createEmailForm(toEmail, title, text);


        try{
            emailSender.send(message);
            log.info("MailService.sendEmail occured success: {}, " +
                    "title: {}, text: {}", toEmail, title, text);

        }catch (Exception e){
            log.debug("MailService.sendEmail exception occur toEmail: {}, " +
                    "title: {}, text: {}", toEmail, title, text);
            throw e;
        }
    }

    // 발신할 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(
                                            String toEmail,
                                            String title,
                                            String text ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

    public void validateEmail(EmailDto dto)  {
        try{
            String email = dto.getEmail();
            int num = dto.getNum();

            if(certificationMap.get(email) == num){
                System.out.println(certificationMap.get(email));
                certificationMap.remove(email);
            }
            else{
                throw new Exception("Invalid certification number");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
