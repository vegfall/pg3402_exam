CREATE DATABASE IF NOT EXISTS quiz_service;

USE quiz_service;

CREATE TABLE session (
    session_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status ENUM('CREATED', 'ONGOING', 'COMPLETED') DEFAULT 'CREATED',
    session_key VARCHAR(255) NOT NULL UNIQUE,
    theme VARCHAR(255) NOT NULL,
    number_of_alternatives INT NOT NULL,
    username VARCHAR(255) NOT NULL,
    current_question_key INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_session_key ON session (session_key);