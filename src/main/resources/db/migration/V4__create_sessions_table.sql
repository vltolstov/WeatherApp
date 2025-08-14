CREATE TABLE sessions (
                          id BIGSERIAL PRIMARY KEY,
                          userId BIGINT NOT NULL,
                          expiresAt TIMESTAMP NOT NULL
);