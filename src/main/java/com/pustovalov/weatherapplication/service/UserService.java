package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.dao.IUserDao;
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

    private final IUserDao userDao;

    private final UserMapper userMapper;


    public User create(CreateUserFormData createUserFormData) {
        if (createUserFormData == null) {
            throw new IllegalArgumentException("createUserDto cannot be null");
        }
        return userDao.save(userMapper.toEntity(createUserFormData));
    }

    public User findBy(long id) {
        if (0 >= id) {
            throw new IllegalArgumentException("id cannot be less than or equal to zero ");
        }
        return userDao.findBy(id).orElseThrow(() -> new RuntimeException(""));
    }
}