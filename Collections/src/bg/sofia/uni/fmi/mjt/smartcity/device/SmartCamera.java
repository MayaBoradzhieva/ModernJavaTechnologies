package bg.sofia.uni.fmi.mjt.smartcity.device;

import java.time.LocalDateTime;

import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public class SmartCamera extends SmartDeviceAbstractClass {
	private String cameraID;
	private static int uniqueNumberSmartCamera = 0;
	private DeviceType type;

	public SmartCamera(String name, double powerConsumption, LocalDateTime installationDateTime) {

		super(name, powerConsumption, installationDateTime);
		// super.setType(DeviceType.CAMERA);
		// super.setDeviceID(uniqueNumberSmartCamera);
		// = super.deviceID;

		type = DeviceType.CAMERA;
		cameraID = type.getShortName() + '-' + name + '-' + uniqueNumberSmartCamera;
		uniqueNumberSmartCamera++;

	}

	@Override
	public DeviceType getType() {
		return type;
	}

	@Override
	public String getId() {
		return cameraID;
	}

}
