package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

//@Disabled
@Autonomous(name = "Marker To Pit")

public class MarkerToPit extends LinearOpMode {
	
	// Defines hardware
	private DcMotor linearSlide;
	private DcMotor leftDrive;
	private DcMotor rightDrive;
	private CRServo mainServo;
	private Servo markerServo;

    static final double COUNTS_PER_MOTOR_REV = 1440;
	private double position_mainServo = 0.7; // Starts servo at position
	private double position_markerServo = 0.0;

	private void driveForward(double power) {
	    leftDrive.setPower(power);
	    rightDrive.setPower(power);
    }

    private void stopDriving() {
	    driveForward(0);
    }

	private void driveForwardDistance(double power, int distance) {
	    leftDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);
	    rightDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);

	    leftDrive.setTargetPosition(distance);
        rightDrive.setTargetPosition(distance);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveForward(power);

        while(leftDrive.isBusy() && rightDrive.isBusy()) { }

        stopDriving();

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

	private void run() {
		linearSlide.setPower(1);
		sleep(3000);
		
		linearSlide.setPower(0);
		mainServo.setPower(1);
		sleep(200);

		mainServo.setPower(0);
		sleep(4500);
		
		leftDrive.setPower(0);
		rightDrive.setPower(0);
		markerServo.setPosition(1.0);
		linearSlide.setPower(-1);
		sleep(2600);
		
		leftDrive.setPower(0.7);
		rightDrive.setPower(0.7);
		sleep(400);
		
		linearSlide.setPower(0);
		leftDrive.setPower(-0.7);
		rightDrive.setPower(0.7);
		sleep(1100);
		
		leftDrive.setPower(-0.7);
		rightDrive.setPower(-0.7);
		sleep(750);
		
		leftDrive.setPower(-0.7);
		rightDrive.setPower(0.7);
		sleep(1440);
		
		leftDrive.setPower(0);
		rightDrive.setPower(0);
		sleep(100);
		
		leftDrive.setPower(-0.7);
		rightDrive.setPower(-0.7);
		sleep(5600);
		
		stop();
	}
	
	// Initializes hardware
	@Override
	public void runOpMode() {

		// Updates telemetry (log) to show it is running
		telemetry.addData("Status", "Updated!");
		telemetry.update();

		// Initializes hardware
        linearSlide = hardwareMap.dcMotor.get("linearSlide");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        mainServo = hardwareMap.crservo.get("mainServo");
        markerServo = hardwareMap.servo.get("markerServo");

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		leftDrive.setDirection(DcMotor.Direction.REVERSE);
		rightDrive.setDirection(DcMotor.Direction.FORWARD);
	//	mainServo.setPosition(position_mainServo);
		markerServo.setPosition(position_markerServo);
		
		waitForStart();
		
		// Runs main function
		run();
	}
}
