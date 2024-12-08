package com.quiz.quiz.repository;

import com.quiz.quiz.model.Session;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class MockQuizRepository {
    private Session session;

    public MockQuizRepository() {
        createSession();
    }

    private void createSession() {
        session = new Session("1234", 1);
    }

    public Session getSession(String sessionKey) {
        return Objects.equals(sessionKey, "1234")
                ? session
                : null;
    }
}
