package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.entity.Session;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.repository.SessionRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@ActiveProfiles("test")
@SpringBootTest
public class SessionRepositoryIntegrationTest {

    @Autowired
    private SessionRepository out;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory sessionFactory;

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
    void deleteExpiredSessionsWhenMethodCalledThenOnlyExpiredSessionsDeleted() {
        // GIVEN
        User user = userService.save(new CreateUserFormData("login1", "password"));

        Session session1 = new Session(user, LocalDateTime.now());
        Session session2 = new Session(user, LocalDateTime.now());
        Session session3 = new Session(user, LocalDateTime.now());
        Session session4 = new Session(user, LocalDateTime.now().plusDays(1L));
        out.save(session1);
        out.save(session2);
        out.save(session3);
        out.save(session4);

        int expectedRowsAmount = 3;

        //        WHEN
        int actualRowsAmount = out.deleteAllByExpiresAtBefore(LocalDateTime.now());
        //        THEN
        Assertions.assertThat(actualRowsAmount).isEqualTo(expectedRowsAmount);
        Assertions.assertThat(out.findBy(session4.getId())).isPresent();
    }

}
