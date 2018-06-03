package org.iot.trucker.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * Entity for vehicle
 * @author Harish Krishnamurthi
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class Vehicle {
	@Id
	private String vin;
	private String make;
	private String model;
	private int year;
	private float redlineRpm;
	private float maxFuelVolume;
	private Timestamp lastServiceDate;
	@OneToMany
	@JoinColumn(name="vin")
	private List<Reading> readings;
	@OneToMany
	@JoinColumn(name="vin")
	private List<Alert> alerts;
	
	public List<Alert> getAlerts() {
		return alerts;
	}
	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
	public List<Reading> getReadings() {
		return readings;
	}
	public void setReadings(List<Reading> readings) {
		this.readings = readings;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public float getRedlineRpm() {
		return redlineRpm;
	}
	public void setRedlineRpm(float redlineRpm) {
		this.redlineRpm = redlineRpm;
	}
	public float getMaxFuelVolume() {
		return maxFuelVolume;
	}
	public void setMaxFuelVolume(float maxFuelVolume) {
		this.maxFuelVolume = maxFuelVolume;
	}
	public Timestamp getLastServiceDate() {
		return lastServiceDate;
	}
	public void setLastServiceDate(Timestamp lastServiceDate) {
		this.lastServiceDate = lastServiceDate;
	}
	
	public String toString() {
		return "Vehicle [vin=" + vin + ", make=" + make + ", model=" + model + ", year=" + year + ", redlineRpm="
				+ redlineRpm + ", maxFuelVolume=" + maxFuelVolume + ", lastServiceDate=" + lastServiceDate + "]";
	}
	
}
