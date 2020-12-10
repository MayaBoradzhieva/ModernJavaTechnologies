package bg.sofia.uni.fmi.mjt.smartcity.device;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public class SmartLamp extends SmartDeviceAbstractClass {

	private String lampID;
	private static int uniqueNumberSmartLamp = 0;
	DeviceType type;

	public SmartLamp(String name, double powerConsumption, LocalDateTime installationDateTime) {

		super(name, powerConsumption, installationDateTime);
		// super.setType(DeviceType.LAMP);
		// super.setDeviceID(uniqueNumberSmartLamp);
		// lampID = super.deviceID;

		type = DeviceType.LAMP;
		lampID = type.getShortName() + '-' + name + '-' + uniqueNumberSmartLamp;
		uniqueNumberSmartLamp++;

	}

	@Override
	public DeviceType getType() {
		return type;
	}

	@Override
	public String getId() {
		return lampID;
	}

}
