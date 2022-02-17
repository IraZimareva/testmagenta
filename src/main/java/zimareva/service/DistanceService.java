package zimareva.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zimareva.entity.City;
import zimareva.entity.Distance;
import zimareva.exception.DistanceNotFoundException;
import zimareva.repository.DistanceRepository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DistanceService {

    private final DistanceRepository distanceRepository;
    private final CityService cityService;

    @Autowired
    public DistanceService(DistanceRepository distanceRepository, CityService cityService) {
        this.distanceRepository = distanceRepository;
        this.cityService = cityService;
    }

    public List<Distance> getDistances (){
        return StreamSupport
                .stream(distanceRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    public Distance getDistance (Long id){
        return distanceRepository.findById(id).orElseThrow(()->
                new DistanceNotFoundException(id));
    }

    public void uploadXML (MultipartFile multipartFile) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Distance.class);
        Unmarshaller mar = context.createUnmarshaller();
        Distance unmDistance = (Distance) mar.unmarshal(multipartFile.getInputStream());

        System.out.println("Unmarshalling object   " + unmDistance.toString());

        City from = cityService.addCity(unmDistance.getFromCity());
        City to = cityService.addCity(unmDistance.getToCity());
        this.addDistance2Dimension(unmDistance, from, to);
    }

    public void addDistance2Dimension (Distance distance, City from, City to){
        Distance createDistanceFromTo = new Distance(from, to, distance.getDistance());
        distanceRepository.save(createDistanceFromTo);

        Distance createDistanceToFrom = new Distance(to, from, distance.getDistance());
        distanceRepository.save(createDistanceToFrom);
    }

    public String calculateTheDistance (String type, Long fromCityID, Long toCityID){
        City fromCity = cityService.getCity(fromCityID);
        City toCity = cityService.getCity(toCityID);
        DistanceCalculator distanceCalculator;
        double resultCrowflight;
        double resultDistanceMatrix;

        switch (type){
            case "ALL":
                distanceCalculator = new DistanceCalculator();
                resultCrowflight = distanceCalculator.calculationByDistance(fromCity,toCity);
                resultDistanceMatrix = this.getDistanceFromDistanceClass(fromCity, toCity);
                return "calculate-distance :: results (crowflight=" + resultCrowflight +
                        ", distancematrix=" + resultDistanceMatrix +")";
            case "CROWFLIGHT":
                distanceCalculator = new DistanceCalculator();
                resultCrowflight = distanceCalculator.calculationByDistance(fromCity,toCity);
                return "calculate-distance :: results (crowflight=" + resultCrowflight + ")";
            case "DISTANCEMATRIX":
                resultDistanceMatrix = this.getDistanceFromDistanceClass(fromCity, toCity);
                return "calculate-distance :: results (distancematrix=" + resultDistanceMatrix + ")";
        }
        return null;
        //        return "calculate-distance";
//        return "calculate-distance :: results (" + resultCrowflight + ")";
    }


    public double getDistanceFromDistanceClass (City from, City to){
        Distance distance = distanceRepository.findByFromCityAndToCity(from, to).orElseThrow(() ->
                new DistanceNotFoundException(from, to));
        return distance.getDistance();
    }


    private class DistanceCalculator {
        /*
         * Source: https://introcs.cs.princeton.edu/java/12types/GreatCircle.java.html
         * */
        public double calculationByDistance(City from, City to) {
            double lat1 = Math.toRadians(from.getLatitude());
            double lat2 = Math.toRadians(to.getLatitude());
            double lon1 = Math.toRadians(from.getLongitude());
            double lon2 = Math.toRadians(to.getLongitude());

            /*************************************************************************
             * Compute using Haversine formula
             *************************************************************************/
            double a = Math.pow(Math.sin((lat2-lat1)/2), 2)
                    + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((lon2-lon1)/2), 2);

            // great circle distance in radians
            double angle2 = 2 * Math.asin(Math.min(1, Math.sqrt(a)));

            // convert back to degrees
            angle2 = Math.toDegrees(angle2);

            // each degree on a great circle of Earth is 111.16 kilometres
            double distanceKM = 111.16 * angle2;
            System.out.println(distanceKM + " kilometres");

            BigDecimal bd = new BigDecimal(Double.toString(distanceKM));
            bd = bd.setScale(3, RoundingMode.HALF_UP);
            return bd.doubleValue();
//            return distanceKM;
        }
    }
}
