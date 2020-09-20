package ru.diasoft.digitalq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.digitalq.domain.SmsVerification;

import java.util.Optional;

/**
 * @author Cheranev N.
 * created on 05.09.2020.
 */
@Repository
public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {

    Optional<SmsVerification> findBySecretCodeAndProcessGuidAndStatus(String secretCode, String processGuid, String Status);

    @Modifying
    @Transactional
    @Query("update SmsVerification v set status = ?1 where processGuid = ?2")
    int updateStatusByProcessGuid(String status, String processGuid);
}
