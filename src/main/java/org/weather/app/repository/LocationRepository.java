package org.weather.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.weather.app.model.Location;
import org.weather.app.model.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findByUser(User user);

    long deleteByUserAndLatitudeAndLongitude(User user, BigDecimal latitude, BigDecimal longitude);

    boolean existsByUserAndLatitudeAndLongitude(User user, BigDecimal latitude, BigDecimal longitude);

    Optional<Location> findByUserAndLatitudeAndLongitude(User user, BigDecimal latitude, BigDecimal longitude);

}
