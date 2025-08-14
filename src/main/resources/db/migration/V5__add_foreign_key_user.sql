ALTER TABLE locations
    ADD CONSTRAINT fk_location_user
        FOREIGN KEY (userId) REFERENCES users(id);

ALTER TABLE sessions
    ADD CONSTRAINT fk_session_user
        FOREIGN KEY (userId) REFERENCES users(id);