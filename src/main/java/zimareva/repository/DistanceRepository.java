package zimareva.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import zimareva.entity.City;
import zimareva.entity.Distance;

import java.util.Optional;

@Repository
public interface DistanceRepository extends CrudRepository <Distance, Long> {

    Optional<Distance> findByFromCityAndToCity(City fromCity, City toCity);
}
