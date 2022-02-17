package zimareva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import zimareva.entity.City;
import zimareva.entity.Distance;
import zimareva.repository.CityRepository;
import zimareva.repository.DistanceRepository;

@SpringBootApplication
public class TestMagentaApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(TestMagentaApplication.class, args);
        /*CityRepository cityRepository =
                configurableApplicationContext.getBean(CityRepository.class);
        City city1 = new City("Ufa", 54.45, 56.00);
        cityRepository.save(city1);
        City city2 = new City("Penza", 53.12, 45.00);
        cityRepository.save(city2);

        DistanceRepository distanceRepository =
                configurableApplicationContext.getBean(DistanceRepository.class);
        Distance distance1 = new Distance (city1,city2,829);
        distanceRepository.save(distance1);*/

        /*DistanceRepository distanceRepository =
                configurableApplicationContext.getBean(DistanceRepository.class);
        distanceRepository.deleteById(4L);
        distanceRepository.deleteById(5L);*/

    }
}
