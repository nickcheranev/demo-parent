package ru.diasoft.digitalq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.diasoft.digitalq.domain.ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification;
import ru.diasoft.digitalq.domain.ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification;
import ru.diasoft.digitalq.domain.SmsVerification;
import ru.diasoft.digitalq.model.SmsVerificationMessage;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;
import ru.diasoft.digitalq.smsverificationcreated.publish.SmsVerificationCreatedPublishGateway;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * @author Cheranev N. created on 05.09.2020.
 */
@RequiredArgsConstructor
@Service
@Primary
public class SmsVerificationPrimaryService implements SmsVerificationService {

    private final SmsVerificationRepository repository;
    private final SmsVerificationCreatedPublishGateway messageGateway;

    @Override
    public ResponseEntity<ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification>
    dsSmsVerificationGETnonews_v1_0_SmsVerification(String processGUID, String secretCode) {
        Optional<SmsVerification> oSmsVerification = repository.findBySecretCodeAndProcessGuidAndStatus(secretCode, processGUID, "OK");
        return ResponseEntity.ok()
                .body(new ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification(oSmsVerification.isPresent()));
    }

    @Override
    public ResponseEntity<ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification>
    dsSmsVerificationPOSTnonews_v1_0_SmsVerification(String phoneNumber) {
        SmsVerification smsVerification =
                SmsVerification.builder()
                        .phoneNumber(phoneNumber)
                        .processGuid(UUID.randomUUID().toString())
                        .secretCode(String.format("%04d", new Random().nextInt(10000)))
                        .status("WAITING")
                        .build();
        repository.save(smsVerification);

        messageGateway.smsVerificationCreated(SmsVerificationMessage.builder()
                .guid(smsVerification.getProcessGuid())
                .phoneNumber(smsVerification.getPhoneNumber())
                .build());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification(
                                smsVerification.getProcessGuid()));
    }
}
