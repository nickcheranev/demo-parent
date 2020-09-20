package ru.diasoft.digitalq.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.diasoft.digitalq.Application;
import ru.diasoft.digitalq.domain.SmsVerification;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Cheranev N. created on 05.09.2020.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class SmsVerificationRepositoryTest {

    @Autowired
    private SmsVerificationRepository repository;

    @Test
    public void saveTest() {
        SmsVerification smsVerification =
                SmsVerification.builder()
                        .processGuid(UUID.randomUUID().toString())
                        .phoneNumber("1234567")
                        .secretCode("007")
                        .status("WAITING")
                        .build();
        SmsVerification createdEntity = repository.save(smsVerification);
        assertThat(smsVerification).isEqualTo(createdEntity);
        assertThat(smsVerification.getVerificationId()).isNotNull();
    }

    @Test
    public void findBySecretCodeAndProcessGuidAndStatusTest() {
        SmsVerification smsVerification =
                SmsVerification.builder()
                        .processGuid(UUID.randomUUID().toString())
                        .phoneNumber("1234567")
                        .secretCode("007")
                        .status("WAITING")
                        .build();
        repository.save(smsVerification);
        assertThat(repository.findBySecretCodeAndProcessGuidAndStatus(
                smsVerification.getSecretCode(),
                smsVerification.getProcessGuid(),
                smsVerification.getStatus())).isNotEmpty();
        assertThat(repository.findBySecretCodeAndProcessGuidAndStatus(
                "008",
                smsVerification.getProcessGuid(),
                smsVerification.getStatus())).isEmpty();
    }

    @Test
    public void updateStatusByProcessingGuidTest() {
        String guid = UUID.randomUUID().toString();
        String status = "WAITING";
        String secretCode = "007";

        SmsVerification smsVerification =
                SmsVerification.builder()
                        .processGuid(guid)
                        .phoneNumber("1234567")
                        .secretCode(secretCode)
                        .status(status)
                        .build();
        SmsVerification createdEntity = repository.save(smsVerification);
        int count = repository.updateStatusByProcessGuid("OK", guid);
        assertThat(count).isEqualTo(1);

        Optional<SmsVerification> updatedEntity = repository.findById(createdEntity.getVerificationId());
        assertThat(updatedEntity.isPresent()).isTrue();

        assertThat(updatedEntity.orElse(SmsVerification.builder().build())
                .getStatus())
                .isEqualTo("OK");
    }
}
