package com.hardware.business.builder;

import com.hardware.business.model.Ammeter;
import com.hardware.business.model.DoorLock;
import com.hardware.business.model.Hardware;

public abstract class Builder {

	public abstract DoorLock builderDoorLock(Hardware hardware);

	public abstract Ammeter builderAmmeter(Hardware hardware);
}
