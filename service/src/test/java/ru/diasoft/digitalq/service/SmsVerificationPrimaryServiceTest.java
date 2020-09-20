package ru.diasoft.digitalq.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.digitalq.Application;
import ru.diasoft.digitalq.domain.ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification;
import ru.diasoft.digitalq.domain.ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification;
import ru.diasoft.digitalq.domain.SmsVerification;
import ru.diasoft.digitalq.repository.SmsVerificationRepository;
import ru.diasoft.digitalq.smsverificationcreated.publish.SmsVerificationCreatedPublishGateway;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Cheranev N. created on 05.09.2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class SmsVerificationPrimaryServiceTest {

    private final String PHONE_NUMBER = "+79128888888";
    private final String VALID_SECRET_CODE = "007";
    private final String INVALID_SECRET_CODE = "008";
    private final String GUID = UUID.randomUUID().toString();
    private final String STATUS = "OK";

    @Mock
    private SmsVerificationRepository repository;

    @Mock
    private SmsVerificationCreatedPublishGateway messageGateway;

    private SmsVerificationService service;

    @Before
    public void init() {
        service = new SmsVerificationPrimaryService(repository, messageGateway);

        SmsVerification smsVerification =
                SmsVerification.builder()
                        .processGuid(UUID.randomUUID().toString())
                        .phoneNumber(PHONE_NUMBER)
                        .secretCode(VALID_SECRET_CODE)
                        .status(STATUS)
                        .build();
        when(repository.findBySecretCodeAndProcessGuidAndStatus(VALID_SECRET_CODE, GUID, STATUS))
                .thenReturn(Optional.of(smsVerification));
        when(repository.findBySecretCodeAndProcessGuidAndStatus(INVALID_SECRET_CODE, GUID, STATUS))
                .thenReturn(Optional.empty());
    }

    @Test
    public void dsSmsVerificationGETnonews_v1_0_SmsVerification() {
        ResponseEntity<ResultResponseFordsSmsVerificationGETnonews_v10_SmsVerification> response =
                ((SmsVerificationPrimaryService) service)
                        .dsSmsVerificationGETnonews_v1_0_SmsVerification(GUID, VALID_SECRET_CODE);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getCheckResult()).isEqualTo(true);
    }

    @Test
    public void dsSmsVerificationPOSTnonews_v1_0_SmsVerification() {
        ResponseEntity<ResultResponseFordsSmsVerificationPOSTnonews_v10_SmsVerification> response =
                ((SmsVerificationPrimaryService) service)
                        .dsSmsVerificationPOSTnonews_v1_0_SmsVerification(PHONE_NUMBER);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getProcessGUID()).isNotEmpty();
    }
}
