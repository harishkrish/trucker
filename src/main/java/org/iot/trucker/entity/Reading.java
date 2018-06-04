package org.iot.trucker.entity;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
/**
 * Reading entity
 * @author Harish Krishnamurthi
 *
 */
@Entity
public class Reading {
	@Id
	private String id;
	private String vin;
	private double latitude;
	private double longitude;
	private Timestamp timestamp;
	private float fuelVolume;
	private float speed;
	private float engineHp;
	private boolean checkEngineLightOn;
	private boolean engineCoolantLow;
	private boolean cruiseControlOn;
	private float engineRpm;
	@OneToOne
	private Tires tires;
	
	
	public Reading(){
		this.id = UUID.randomUUID().toString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public float getFuelVolume() {
		return fuelVolume;
	}
	public void setFuelVolume(float fuelVolume) {
		this.fuelVolume = fuelVolume;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getEngineHp() {
		return engineHp;
	}
	public void setEngineHp(float engineHp) {
		this.engineHp = engineHp;
	}
	public boolean isCheckEngineLightOn() {
		return checkEngineLightOn;
	}
	public void setCheckEngineLightOn(boolean checkEngineLightOn) {
		this.checkEngineLightOn = checkEngineLightOn;
	}
	public boolean isEngineCoolantLow() {
		return engineCoolantLow;
	}
	public void setEngineCoolantLow(boolean engineCoolantLow) {
		this.engineCoolantLow = engineCoolantLow;
	}
	public boolean isCruiseControlOn() {
		return cruiseControlOn;
	}
	public void setCruiseControlOn(boolean cruiseControlOn) {
		this.cruiseControlOn = cruiseControlOn;
	}
	public float getEngineRpm() {
		return engineRpm;
	}
	public void setEngineRpm(float engineRpm) {
		this.engineRpm = engineRpm;
	}
	public Tires getTires() {
		return tires;
	}
	public void setTires(Tires tires) {
		this.tires = tires;
	}
	@Override
	public String toString() {
		return "Reading [id=" + id + ", vin=" + vin + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", timestamp=" + timestamp + ", fuelVolume=" + fuelVolume + ", speed=" + speed + ", engineHp="
				+ engineHp + ", checkEngineLightOn=" + checkEngineLightOn + ", engineCoolantLow=" + engineCoolantLow
				+ ", cruiseControlOn=" + cruiseControlOn + ", engineRpm=" + engineRpm + ", tires=" + tires + "]";
	}
	
}
