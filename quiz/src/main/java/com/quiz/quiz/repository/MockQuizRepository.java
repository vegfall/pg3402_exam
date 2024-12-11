package com.quiz.quiz.repository;

import com.quiz.quiz.model.Session;
import org.springframework.stereotype.Repository;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

@Repository
public class MockQuizRepository {
    private final Map<Long, Session> mockSessionDatabase;
    private Long mockIdCounter;

    public MockQuizRepository() {
        mockSessionDatabase = new Hashtable<>();
        mockIdCounter = 0L;
    }

    public Session getSession(String sessionKey) {
        for (Session session : mockSessionDatabase.values()) {
            if (Objects.equals(session.getSessionKey(), sessionKey)) {
                return session;
            }
        }

        return null;
    }

    public Session insertSession(Session session) {
        session.setSessionId(mockIdCounter++);

        mockSessionDatabase.put(session.getSessionId(), session);

        return mockSessionDatabase.get(session.getSessionId());
    }
}
