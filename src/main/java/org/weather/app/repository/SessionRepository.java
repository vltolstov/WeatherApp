package org.weather.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weather.app.model.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
