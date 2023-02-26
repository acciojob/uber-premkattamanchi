package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		if(customer!=null)
			customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		customerRepository2.deleteById(customerId);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList=driverRepository2.findAll();
		int driverId=0;
		for(Driver driver:driverList){
			if(driver.getCab().getAvailable()==true){
				driverId=driver.getDriverId();
				driver.getCab().setAvailable(false);
				break;
			}
		}
		if(driverId==0){
			throw new Exception("No cab available!");
		}

		Driver driver=driverRepository2.findById(driverId).get();
		Customer customer;
		try{
			customer=customerRepository2.findById(customerId).get();
		}
		catch(Exception e){
			throw new Exception("No customer available");
		}
		int costPerKm=driver.getCab().getPerKmRate();
		int bill=costPerKm*distanceInKm;

		//setting TripBooking entity
		TripBooking tripBooking=new TripBooking();
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setBill(bill);
		tripBooking.setStatus(TripStatus.CONFIRMED);
		tripBooking.setCustomer(customer);
		tripBooking.setDriver(driver);

		//adding in Customer tripBookingList
		customer.getTripBookingList().add(tripBooking);
		//adding in Driver tripBookingList
		driver.getTripBookingList().add(tripBooking);

		tripBookingRepository2.save(tripBooking);

		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
        TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		tripBooking.getDriver().getCab().setAvailable(true);//makimg cab available for other users
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.COMPLETED);
		tripBooking.getDriver().getCab().setAvailable(true);
	}
}
