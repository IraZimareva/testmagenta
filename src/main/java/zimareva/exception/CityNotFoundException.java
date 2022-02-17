package zimareva.exception;

import java.text.MessageFormat;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(Long id){
        super(MessageFormat.format("Could not find city with id: {0} ", id));
    }
}
