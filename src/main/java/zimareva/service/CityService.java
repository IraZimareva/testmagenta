package zimareva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zimareva.entity.City;
import zimareva.exception.CityNotFoundException;
import zimareva.repository.CityRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCities (){
        return StreamSupport
                .stream(cityRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    public City getCity (Long id){
        return cityRepository.findById(id).orElseThrow(()->
                new CityNotFoundException(id));
    }

    public City addCity (City city){
        return cityRepository.save(city);
    }
}
