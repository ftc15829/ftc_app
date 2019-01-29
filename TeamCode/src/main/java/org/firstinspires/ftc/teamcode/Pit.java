package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "Pit")

public class Pit extends LinearOpMode {
	
	// Defines hardware
	private DcMotor linearSlide = hardwareMap.dcMotor.get("linearSlide");
	private DcMotor leftDrive = hardwareMap.dcMotor.get("leftDrive");
	private DcMotor rightDrive = hardwareMap.dcMotor.get("rightDrive");
	private Servo mainServo = hardwareMap.servo.get("mainServo");
	private Servo markerServo = hardwareMap.servo.get("markerServo");
	
	private double position_mainServo = 0.7; // Starts servo at position
	private double position_markerServo = 1.0;
	
	private void run() {
		linearSlide.setPower(1);
		sleep(2800);
		
		linearSlide.setPower(0);
		leftDrive.setPower(-1);
		rightDrive.setPower(-1);
		mainServo.setPosition(1);
		sleep(4000);
		
		leftDrive.setPower(0);
		rightDrive.setPower(0);
		linearSlide.setPower(-1);
		sleep(1000);
		
		stop();
	}
	
	
	// Main function
	public void runOpMode() {

		// Updates telemetry (log) to show it is running
		telemetry.addData("Status", "Updated!");
		telemetry.update();

		// Initializes hardware
		leftDrive.setDirection(DcMotor.Direction.REVERSE);
		rightDrive.setDirection(DcMotor.Direction.FORWARD);
		mainServo.setPosition(position_mainServo);
		markerServo.setPosition(position_markerServo);
		
		waitForStart();
		
		// Runs main Function
		run();
	}
}


