CREATE TABLE locations (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          userId BIGINT NOT NULL,
                          latitude NUMERIC(9,6) NOT NULL,
                          longitude NUMERIC(9,6) NOT NULL
);