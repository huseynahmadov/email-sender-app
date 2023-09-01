package io.emailapp.service.concrete;

import io.emailapp.dao.entity.ConfirmationEntity;
import io.emailapp.dao.entity.UserEntity;
import io.emailapp.dao.repository.ConfirmationRepository;
import io.emailapp.dao.repository.UserRepository;
import io.emailapp.service.abstraction.EmailService;
import io.emailapp.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

/**
 * @author Huseyn Ahmadov
 * @version 1.0
 * @since 01.09.2023
 */

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceHandler implements UserService {

    UserRepository userRepository;
    ConfirmationRepository confirmationRepository;
    EmailService emailService;

    @Override
    public UserEntity saveUser(UserEntity user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setEnabled(false);
        userRepository.save(user);

        var confirmation = new ConfirmationEntity(user);
        confirmationRepository.save(confirmation);

//        emailService.sendSimpleMailMessage(user.getName(), user.getEmail(), confirmation.getToken());
        emailService.sendMimeMessageWithAttachments(user.getName(), user.getEmail(), confirmation.getToken());
        return user;
    }

    @Override
    public Boolean verifyToken(String token) {
        var confirmation = confirmationRepository.findByToken(token);
        var user = userRepository.findByEmailIgnoreCase(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);

        return Boolean.TRUE;

    }

}
