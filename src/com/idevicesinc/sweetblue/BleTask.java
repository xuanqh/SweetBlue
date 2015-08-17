package com.idevicesinc.sweetblue;

import android.bluetooth.BluetoothDevice;

import com.idevicesinc.sweetblue.annotations.Advanced;

import java.util.UUID;

/**
 * Under the hood, SweetBlue uses a priority task queue to serialize all interaction with the native BLE stack.
 * This enumeration represents all the tasks that are used and lets you control various timing options in
 * {@link BleDeviceConfig} and {@link BleManagerConfig}, for example {@link BleDeviceConfig#timeoutRequestFilter}.
 */
@Advanced
public enum BleTask
{
	/**
	 * Associated with {@link BleManager#turnOff()}
	 */
	TURN_BLE_OFF,
	
	/**
	 * Associated with {@link BleManager#turnOn()}
	 */
	TURN_BLE_ON,
	
	/**
	 * Associated with {@link BleManagerConfig#enableCrashResolver}.
	 */
	RESOLVE_CRASHES,
	
	/**
	 * Associated with {@link BleDevice#connect()} and its several overloads.
	 */
	CONNECT,
	
	/**
	 * Associated with {@link BleDevice#disconnect()}.
	 */
	DISCONNECT,
	
	/**
	 * Associated with {@link BleDevice#bond()} and {@link BleDeviceState#BONDING}.
	 */
	BOND,
	
	/**
	 * Associated with {@link BleDevice#unbond()}.
	 */
	UNBOND,
	
	/**
	 * Associated with {@link BleDevice#read(java.util.UUID, com.idevicesinc.sweetblue.BleDevice.ReadWriteListener)}.
	 */
	READ,
	
	/**
	 * Associated with {@link BleDevice#write(java.util.UUID, byte[], com.idevicesinc.sweetblue.BleDevice.ReadWriteListener)}.
	 */
	WRITE,
	
	/**
	 * Associated with {@link BleDevice#enableNotify(java.util.UUID, com.idevicesinc.sweetblue.BleDevice.ReadWriteListener)} and
	 * {@link BleDevice#disableNotify(java.util.UUID, com.idevicesinc.sweetblue.BleDevice.ReadWriteListener)}.
	 */
	TOGGLE_NOTIFY,
	
	/**
	 * Associated with {@link BleDevice#readRssi()} and {@link BleDevice#startRssiPoll(com.idevicesinc.sweetblue.utils.Interval)} (and overloads thereof).
	 */
	READ_RSSI,
	
	/**
	 * Associated with discovering services after a {@link BleDevice} becomes {@link BleDeviceState#CONNECTED}.
	 */
	DISCOVER_SERVICES,


	/**
	 * Associated with sending a notification to a remote client through {@link BleServer#notify(BluetoothDevice, UUID, UUID, byte[])} or overloads.
	 */
	SEND_NOTIFICATION,

	CONNECT_SERVER,

	DISCONNECT_SERVER,

	SEND_READ_WRITE_RESPONSE;
	
	/**
	 * Returns whether <code>this</code> is associated with a {@link BleDevice}.
	 */
	public boolean isDeviceSpecific()
	{
		switch(this)
		{
			//--- DRK > Server-specific.
			case CONNECT_SERVER:
			case DISCONNECT_SERVER:
			case SEND_NOTIFICATION:
			case SEND_READ_WRITE_RESPONSE:

			//--- DRK > Manager-specific.
			case TURN_BLE_OFF:
			case TURN_BLE_ON:
			case RESOLVE_CRASHES:	return false;

			default:				return true;
		}
	}

	/**
	 * Returns whether <code>this</code> is associated with {@link BleManager}.
	 */
	public boolean isManagerSpecific()
	{
		switch(this)
		{
			case TURN_BLE_OFF:
			case TURN_BLE_ON:
			case RESOLVE_CRASHES:

			default:				return true;
		}
	}

	/**
	 * Returns whether <code>this</code> is associated with a {@link BleServer}.
	 */
	public boolean isServerSpecific()
	{
		return !isDeviceSpecific() && !isManagerSpecific();
	}
	
	/**
	 * Returns <code>true</code> if the task can have a characteristic UUID associated with it - for now {@link #READ}, {@link #WRITE} and {@link #TOGGLE_NOTIFY}.
	 */
	public boolean usesCharUuid()
	{
		return this == READ || this == WRITE || this == TOGGLE_NOTIFY;
	}
}
