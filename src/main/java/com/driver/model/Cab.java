package com.driver.model;

import javax.persistence.*;
import com.driver.model.Driver;

@Entity
@Table(name = "cabs")
public class Cab{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int perKmRate;
    private boolean availbale;
    @OneToOne
    @JoinColumn
    private Driver driver;

    public Cab() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean getAvailbale() {
        return availbale;
    }

    public void setAvailbale(boolean availbale) {
        this.availbale = availbale;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}