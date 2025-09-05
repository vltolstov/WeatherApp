package org.weather.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weather.app.model.Session;

import java.time.LocalDateTime;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Session findById(String sessionToken);

    void deleteByExpiresAtBefore(LocalDateTime dateTime);
}
