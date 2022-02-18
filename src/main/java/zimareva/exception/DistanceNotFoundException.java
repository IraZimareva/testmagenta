package zimareva.exception;

import zimareva.entity.City;

import java.text.MessageFormat;

public class DistanceNotFoundException extends RuntimeException {
    public DistanceNotFoundException(Long id){
        super(MessageFormat.format("Could not find distance with id: {0} ", id));
    }

    public DistanceNotFoundException(City from, City to){
        super("Could not find distance between " + from.getName() + " and " + to.getName());
    }
}
