package io.emailapp.dao.repository;

import io.emailapp.dao.entity.ConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<ConfirmationEntity, Long> {

    ConfirmationEntity findByToken(String token);

}
