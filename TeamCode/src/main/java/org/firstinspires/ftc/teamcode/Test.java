package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//@Disabled
@TeleOp(name = "Test")

public class Test extends LinearOpMode {
	private Telemetry.Item Status;
	private Telemetry.Item SubStatus;
	private Telemetry.Item Case = telemetry.addData("Case", "");
	
	public void runOpMode() {
		telemetry.setAutoClear(false);
		Status = telemetry.addData("Status", "Normal");
		SubStatus = telemetry.addData("Sub-Status", "");
		telemetry.update();
		
		Status.setValue("Initialized");
		telemetry.update();
		
		waitForStart();
		
		while (opModeIsActive()) {
			Case.setValue("Left");
			telemetry.update();
			
			
			if (isStopRequested()) {
				Status.setValue("Stopping");
				telemetry.update();
			}
		}
	}
}