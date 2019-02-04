package org.firstinspires.ftc.teamcode;

import android.content.Context;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hware {
	
	private static final android.content.Context Context = null;
	public static DcMotor leftDrive;
	public static DcMotor rightDrive;
	
	
	public static void init() {
		HardwareMap hardwareMap = new HardwareMap(Context);
		leftDrive = hardwareMap.dcMotor.get("leftDrive");
		rightDrive = hardwareMap.dcMotor.get("rightDrive");
		leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		leftDrive.setDirection(DcMotor.Direction.REVERSE);
		rightDrive.setDirection(DcMotor.Direction.FORWARD);
		
	}
}
