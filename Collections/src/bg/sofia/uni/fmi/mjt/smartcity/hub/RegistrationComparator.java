package bg.sofia.uni.fmi.mjt.smartcity.hub;

import java.util.Comparator;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;

public class RegistrationComparator implements Comparator<SmartDevice> {

	@Override
	public int compare(SmartDevice firstDevice, SmartDevice secondDevice) {

		return (int) (firstDevice.getRegistration() - secondDevice.getRegistration());
	}

}
