package com.driver.model;

import com.driver.model.Cab;
import com.driver.model.TripBooking;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//import java.sql.Driver;

@Entity
@Table(name = "driver")
public class Driver{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverId;
    private String mobile;
    private String password;

    @OneToOne //(mappedBy = "driver",cascade = CascadeType.ALL)
    @JoinColumn
    @JsonIgnore
    private Cab cab;
    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TripBooking> tripBookingList=new ArrayList<>();

    public Driver() {
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }
}