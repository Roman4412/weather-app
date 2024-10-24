package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.repository.IUserRepository;
import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.service.mapper.UserMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class UserService {

    private final IUserRepository repository;

    private final UserMapper mapper;

    public User create(CreateUserFormData createUserFormData) {
        if (createUserFormData == null) {
            throw new IllegalArgumentException("createUserDto cannot be null");
        }
        return repository.save(mapper.toEntity(createUserFormData));
    }

    public User findBy(Long id) {
        if (0 >= id) {
            throw new IllegalArgumentException("id cannot be less than or equal to zero ");
        }
        return repository.findBy(id)
                         .orElseThrow(RuntimeException::new);
    }
}