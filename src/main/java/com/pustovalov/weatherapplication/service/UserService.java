package com.pustovalov.weatherapplication.service;

import com.password4j.Password;
import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.dto.LoginUserFormData;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.repository.IUserRepository;
import com.pustovalov.weatherapplication.service.mapper.UserMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class UserService {

    private final IUserRepository repository;

    private final UserMapper mapper;

    public User save(CreateUserFormData createUserFormData) {
        if (createUserFormData == null) {
            throw new IllegalArgumentException("createUserDto cannot be null");
        }

        createUserFormData.setPassword(Password.hash(createUserFormData.getPassword())
                                               .withBcrypt()
                                               .getResult());

        return repository.save(mapper.toEntity(createUserFormData));
    }

    public User findBy(Long id) {
        if (0 >= id) {
            throw new IllegalArgumentException("id cannot be less than or equal to zero ");
        }
        return repository.findBy(id)
                         .orElseThrow(RuntimeException::new);
    }

    public Optional<User> findBy(String login) {
        if (login == null) {
            throw new IllegalArgumentException("id cannot be less than or equal to zero ");
        }
        return repository.findBy(login);
    }

    public boolean isValidUserCredentials(LoginUserFormData formData, Optional<User> optionalUser) {
        return optionalUser.map(user -> Password.check(formData.password(), user.getPassword())
                                                .withBcrypt())
                           .orElse(false);
    }
}