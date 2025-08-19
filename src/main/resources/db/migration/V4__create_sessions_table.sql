CREATE TABLE sessions
(
    id        VARCHAR(64) PRIMARY KEY,
    userId    BIGINT    NOT NULL,
    expiresAt TIMESTAMP NOT NULL
);