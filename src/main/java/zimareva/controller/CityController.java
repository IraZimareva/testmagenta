package zimareva.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zimareva.entity.City;
import zimareva.entity.Distance;
import zimareva.repository.CityRepository;
import zimareva.service.CityService;
import zimareva.service.DistanceService;

import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping(path = "/cities")
public class CityController {

    private final CityService cityService;
    private final DistanceService distanceService;

    @Autowired
    public CityController(CityService cityService, DistanceService distanceService ) {
        this.cityService = cityService;
        this.distanceService = distanceService;
    }

    @GetMapping()
    public String getAllCities(Model model) {
        List<City> cities = cityService.getCities();
        model.addAttribute("cities",cities);
        return "show";
    }


    @GetMapping(value = "/calculate-distance")
    public String getCitiesToCalculate (Model model){
        model.addAttribute("cities",cityService.getCities());
        return "calculate-distance";
    }

    @GetMapping("/calculate")
    public String calculateTheDistance(@RequestParam ("type") String type,
                                       @RequestParam ("fromCityID") Long fromCityID,
                                       @RequestParam ("toCityID") Long toCityID){
        System.out.println("type " + type);
        System.out.println("fromCityID " + fromCityID);
        System.out.println("toCityID " + toCityID);

        return distanceService.calculateTheDistance(type,fromCityID,toCityID);
    }



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @ResponseStatus(HttpStatus.OK)
    public String uploadXML (@RequestParam ("xmlFile") MultipartFile multipartFile) throws JAXBException, IOException {
        distanceService.uploadXML (multipartFile);
        return "redirect:/cities";
    }
}

