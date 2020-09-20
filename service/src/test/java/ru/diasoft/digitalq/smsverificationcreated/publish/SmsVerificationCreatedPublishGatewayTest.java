package ru.diasoft.digitalq.smsverificationcreated.publish;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.digitalq.Application;
import ru.diasoft.digitalq.model.SmsVerificationMessage;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author Cheranev N.
 * created on 06.09.2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class SmsVerificationCreatedPublishGatewayTest {

    @Autowired
    private SmsVerificationCreatedPublishGateway gateway;

    @Test
    public void smsVerificationCreated() {
        gateway.smsVerificationCreated(SmsVerificationMessage.builder()
                .phoneNumber("1")
                .guid(UUID.randomUUID().toString())
                .code("2")
                .build());
    }
}