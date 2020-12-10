package bg.sofia.uni.fmi.mjt.smartcity.device;

import java.time.Duration;
import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public abstract class SmartDeviceAbstractClass implements SmartDevice {
	private String name;
	private double powerConsumption;
	private LocalDateTime installationDateTime;
	// protected String deviceID;
	// private DeviceType type;
	private LocalDateTime registration;

	public SmartDeviceAbstractClass(String name, double powerConsumption, LocalDateTime installationDateTime) {
		this.name = name;
		this.powerConsumption = powerConsumption;
		this.installationDateTime = installationDateTime;

		// deviceID = type.getShortName() + '-' + name + '-';
	}

	@Override
	public abstract String getId();

//	public void setDeviceID(int uniqueNumber)
//	{
//		deviceID = deviceID + uniqueNumber;
//	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getPowerConsumption() {
		return powerConsumption;
	}

	@Override
	public LocalDateTime getInstallationDateTime() {
		return installationDateTime;
	}

	@Override
	public abstract DeviceType getType();

	@Override
	public long getRegistration() {
		return Duration.between(registration, LocalDateTime.now()).toHours();
	}

	@Override
	public void setRegistration(LocalDateTime registration) {
		this.registration = registration;
	}

	@Override
	public long getPowerConsumptionKWh() {
		long duration = Duration.between(getInstallationDateTime(), LocalDateTime.now()).toHours();

		return duration * (long) powerConsumption;
	}

}
