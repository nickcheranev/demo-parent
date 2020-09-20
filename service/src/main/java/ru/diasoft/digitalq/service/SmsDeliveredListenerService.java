package ru.diasoft.digitalq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.model.SmsDeliveredMessage;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;
import ru.diasoft.digitalq.smsverificationdelivered.subscribe.SmsVerificationDeliveredSubscribeListenerService;

/**
 * @author Cheranev N.
 * created on 06.09.2020.
 */
@RequiredArgsConstructor
@Service
@Primary
public class SmsDeliveredListenerService implements SmsVerificationDeliveredSubscribeListenerService {

    private final SmsVerificationRepository repository;

    @Override
    public void smsVerificationDelivered(SmsDeliveredMessage msg) {

        repository.updateStatusByProcessGuid("OK", msg.getGuid());
    }
}
