package bg.sofia.uni.fmi.mjt.smartcity.hub;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bg.sofia.uni.fmi.mjt.smartcity.device.SmartDevice;
import bg.sofia.uni.fmi.mjt.smartcity.enums.DeviceType;

public class SmartCityHub // implements Comparator<SmartDevice>
{
	Map<String, SmartDevice> devices;

	public SmartCityHub() {
		devices = new HashMap<String, SmartDevice>();
	}

	/**
	 * Adds a @device to the SmartCityHub.
	 *
	 * @throws IllegalArgumentException         in case @device is null.
	 * @throws DeviceAlreadyRegisteredException in case the @device is already
	 *                                          registered.
	 */
	public void register(SmartDevice device) throws DeviceAlreadyRegisteredException {
		if (device == null) {
			throw new IllegalArgumentException();
		}

		if (!devices.containsKey(device.getId())) {
			devices.put(device.getId(), device);
			device.setRegistration(LocalDateTime.now());
		} else {
			throw new DeviceAlreadyRegisteredException("The device is already registered.");
		}
	}

	/**
	 * Removes the @device from the SmartCityHub.
	 *
	 * @throws IllegalArgumentException in case null is passed.
	 * @throws DeviceNotFoundException  in case the @device is not found.
	 */
	public void unregister(SmartDevice device) throws DeviceNotFoundException {

		if (device == null) {
			throw new IllegalArgumentException();
		}
		if (devices.containsKey(device.getId())) {
			devices.remove(device.getId(), device);
		} else {
			throw new DeviceNotFoundException("The device is not found.");
		}
	}

	/**
	 * Returns a SmartDevice with an ID @id.
	 *
	 * @throws IllegalArgumentException in case @id is null.
	 * @throws DeviceNotFoundException  in case device with ID @id is not found.
	 */
	public SmartDevice getDeviceById(String id) throws DeviceNotFoundException {

		if (id == null) {
			throw new IllegalArgumentException();
		}

		if (devices.containsKey(id)) {
			return devices.get(id);
		} else {
			throw new DeviceNotFoundException("The device is not found.");
		}

	}

	/**
	 * Returns the total number of devices with type @type registered in
	 * SmartCityHub.
	 *
	 * @throws IllegalArgumentException in case @type is null.
	 */
	public int getDeviceQuantityPerType(DeviceType type) {
		int quantity = 0;

		for (SmartDevice value : devices.values()) {
			if (value.getType().getShortName().equals(type.getShortName())) {
				quantity++;
			}
		}

		return quantity;
	}

	public Collection<String> getTopNDevicesByPowerConsumption(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		List<SmartDevice> list = new LinkedList<>();
		for (SmartDevice value : devices.values()) {
			list.add(value);

		}

		KWhComparator compareKWh = new KWhComparator();
		Collections.sort(list, compareKWh);
		
		if (n >= list.size()) {
			n = list.size();
		}
		
		List<String> arrList = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			arrList.add(list.get(i).getId());
		}

		return arrList;
	}

	public Collection<SmartDevice> getFirstNDevicesByRegistration(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		List<SmartDevice> list = new LinkedList<>();

		for (SmartDevice value : devices.values()) {
			list.add(value);

		}

		RegistrationComparator compareReg = new RegistrationComparator();
		Collections.sort(list, compareReg);

		List<SmartDevice> arrList = new ArrayList<SmartDevice>();

		if (n >= list.size()) {
			n = list.size();
		}

		for (int i = 0; i < n; i++) {
			arrList.add(list.get(i));
		}

		return arrList;
	}

//    Comparator<SmartDevice> comparatorKWh = new Comparator<SmartDevice>() 
//	{
//		public int compare(SmartDevice firstDevice, SmartDevice secondDevice)
//		{
//			long hoursFirstDevice = Duration.between(firstDevice.getInstallationDateTime(), LocalDateTime.now()).toHours();
//			long hoursSecondDevice = Duration.between(secondDevice.getInstallationDateTime(), LocalDateTime.now()).toHours();
//			double firstDeviceConsumption = firstDevice.getPowerConsumption();
//			double secondDeviceConsumption = secondDevice.getPowerConsumption();
//			
//			if (firstDeviceConsumption * hoursFirstDevice > secondDeviceConsumption*hoursSecondDevice)
//			{
//				return 1;
//			}
//			else if (firstDeviceConsumption * hoursFirstDevice < secondDeviceConsumption*hoursSecondDevice)
//			{
//				return -1;
//			}
//			else
//			{
//				return 0;
//			}
//			
//		}
//	};

//    Comparator<SmartDevice> comparatorReg = new Comparator<SmartDevice>() 
//	{
//		public int compare(SmartDevice firstDevice, SmartDevice secondDevice)
//		{
//			long firstDeviceRegistration = Duration.between(firstDevice.getRegistration(), LocalDateTime.now()).toHours();
//			long secondDeviceRegistration = Duration.between(secondDevice.getRegistration(), LocalDateTime.now()).toHours();
//			
//			if (firstDeviceRegistration > secondDeviceRegistration)
//			{
//				return 1;
//			}
//			else if (firstDeviceRegistration < secondDeviceRegistration)
//			{
//				return -1;
//			}
//			else
//			{
//				return 0;
//			}
//			
//		}
//	};

}
