package com.pustovalov.weatherapplication.service;

import com.password4j.Password;
import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.dto.LoginUserFormData;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private UserService out;

    @Autowired
    private SessionFactory sessionFactory;

    private CreateUserFormData createUserFormData;

    @BeforeEach
    void prepare() {
        createUserFormData = new CreateUserFormData("testLogin", "testPassword");
    }

    @AfterEach
    void cleanDatabase() {
        sessionFactory.inTransaction(s -> {
            String querySession = "DELETE FROM Session";
            String queryLocation = "DELETE FROM Location";
            String queryUser = "DELETE FROM User";
            s.createMutationQuery(querySession).executeUpdate();
            s.createMutationQuery(queryLocation).executeUpdate();
            s.createMutationQuery(queryUser).executeUpdate();
        });
    }

    @Test
    void saveWhenSavingUserThenUserIsSavedInDatabase() {
        User savedUser = out.save(createUserFormData);

        assertThat(savedUser)
                  .isNotNull()
                  .extracting("id")
                  .isNotNull();
    }

    @Test
    void saveWhenSavingExistingUserThenThrowsException() {
        out.save(createUserFormData);

        assertThatThrownBy(() -> out.save(createUserFormData))
                  .isInstanceOf(ObjectAlreadyExistException.class);
    }

    @Test
    void saveWhenSavingUserThenPasswordIsEncrypted() {
        String notHashedPass = createUserFormData.getPassword();

        User savedUser = out.save(createUserFormData);

        String actualPass = savedUser.getPassword();
        assertThat(Password.check(notHashedPass, actualPass).withBcrypt()).isTrue();
    }

    @Test
    void isValidUserCredentialsWhenValidCredentialsProvidedThenReturnsTrue() {
        User savedUser = out.save(createUserFormData);
        LoginUserFormData loginUserFormData = new LoginUserFormData("testLogin", "testPassword");

        boolean actual = out.isValidUserCredentials(loginUserFormData, savedUser);

        assertThat(actual).isTrue();

    }

    @Test
    void isValidUserCredentialsWhenNotValidCredentialsProvidedThenReturnsFalse() {
        User savedUser = out.save(createUserFormData);
        LoginUserFormData loginUserFormData = new LoginUserFormData("notValidLogin", "notValidPassword");

        boolean actual = out.isValidUserCredentials(loginUserFormData, savedUser);

        assertThat(actual).isFalse();
    }

}