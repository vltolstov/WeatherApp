package org.weather.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weather.app.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);

    User getUserByName(String name);
}
