package zimareva.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "distance")
@XmlType(propOrder = { "fromCity", "toCity", "distance" })
@Entity
@Table(name = "distance")
public class Distance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_city_id")
    private City fromCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_city_id")
    private City toCity;

    private double distance;

    public Distance() {
    }

    public Distance(City fromCity, City toCity, double distance) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    @XmlTransient
    public void setId(Long id) {
        this.id = id;
    }

    public City getFromCity() {
        return fromCity;
    }

    @XmlElement(name = "fromCity")
    public void setFromCity(City fromCity) {
        this.fromCity = fromCity;
    }

    public City getToCity() {
        return toCity;
    }

    @XmlElement(name = "toCity")
    public void setToCity(City toCity) {
        this.toCity = toCity;
    }

    public double getDistance() {
        return distance;
    }

    @XmlElement(name = "distance")
    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Distance{" +
                "id=" + id +
                ", fromCity=" + fromCity +
                ", toCity=" + toCity +
                ", distance=" + distance +
                '}';
    }
}
