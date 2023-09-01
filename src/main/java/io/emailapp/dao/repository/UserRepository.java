package io.emailapp.dao.repository;

import io.emailapp.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailIgnoreCase(String email);

    Boolean existsByEmail(String email);

}
