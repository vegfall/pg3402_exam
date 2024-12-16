CREATE TABLE IF NOT EXISTS sessions (
    session_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_key VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS questions (
    question_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_key INT NOT NULL,
    question_text VARCHAR(255) NOT NULL,
    session_id BIGINT NOT NULL,
    FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS alternatives (
    alternative_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    alternative_key INT NOT NULL,
    alternative_text VARCHAR(255) NOT NULL,
    correct BOOLEAN NOT NULL,
    alternative_explanation VARCHAR(255),
    question_id BIGINT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE
);