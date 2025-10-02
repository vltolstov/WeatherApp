package org.weather.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.weather.app.model.Location;
import org.weather.app.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByUser(User user);

    long deleteByUserAndLongitudeAndLatitude(User user, BigDecimal longitude, BigDecimal latitude);

    boolean existsByUserAndLongitudeAndLatitude(User user, BigDecimal longitude, BigDecimal latitude);

    boolean existsByUser(User user);

}
