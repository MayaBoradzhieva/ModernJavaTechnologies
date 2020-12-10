package bg.sofia.uni.fmi.mjt.smartcity.device;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public class SmartTrafficLight extends SmartDeviceAbstractClass {
	private String trafficLightID;
	private static int uniqueNumberSmartTrafficLight = 0;
	DeviceType type;

	public SmartTrafficLight(String name, double powerConsumption, LocalDateTime installationDateTime) {

		super(name, powerConsumption, installationDateTime);
//		super.setType(DeviceType.LAMP);
//		super.setDeviceID(uniqueNumberSmartTrafficLight);
//		trafficLightID = super.deviceID; 

		type = DeviceType.TRAFFIC_LIGHT;
		trafficLightID = type.getShortName() + '-' + name + '-' + uniqueNumberSmartTrafficLight;
		uniqueNumberSmartTrafficLight++;

	}

	@Override
	public DeviceType getType() {
		return type;
	}

	@Override
	public String getId() {
		return trafficLightID;
	}

}
