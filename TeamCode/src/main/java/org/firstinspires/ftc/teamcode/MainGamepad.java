package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
@TeleOp(name = "Main Gamepad")

public class MainGamepad extends LinearOpMode {
	
	// Defines hardware
	private DcMotor linearSlide;
	private DcMotor mainShoulder;
	private DcMotor leftDrive;
	private DcMotor rightDrive;
	private CRServo mainServo;
	private Servo markerServo;
	private Servo hand;
	private CRServo armExtend;
	
	// Configures the main servo
	private static final double MAIN_ADJUST = 0.01; // Amount to move servo each cycle (CYCLE_MS)
	private static final double HAND_ADJUST = 0.005;
	private static final int CYCLE_MS = 50; // Time to complete one cycle
	private static final double MAX_POS = 1.0;    // Sets the maximum rotational position (percentage of 180 degrees)
	private static final double MIN_POS = 0.0; // Sets the minimum rotational position
	private double position_mainServo = 1.0; // Starts servo at position
	private double position_markerServo = 0.0;
	private double position_hand = 0.95;
	
	// Sets the power of the various motors
	private double drivePower(int motorID) {
		double powerLvl = 0.0; // Initializes power level with a default value of 0
		double driveMod = 1; // Modifier for left and right drive's speed control
		final double CORRECTION = 1.0; // Adjusts leftDrives power to account for unwanted steering
		
		// Adjusts drive modifier
		if (this.gamepad1.left_bumper)
			driveMod = 0.5;
		if (this.gamepad1.right_bumper)
			driveMod = 0.75;
		
		// Takes the motor ID and sets the power
		switch (motorID) {
			case 0: // leftDrive
				powerLvl = (this.gamepad1.left_stick_y * driveMod) * CORRECTION;
				break;
			case 1: // rightDrive
				powerLvl = (this.gamepad1.right_stick_y * driveMod);
				break;
			case 2: // linearSlide
				powerLvl = this.gamepad2.left_stick_y * -0.5;
				break;
			case 3: // Arm
				powerLvl = this.gamepad2.right_stick_y * 0.5;
				break;
		}
		return powerLvl;
	}


	// Controls servos
	private void servoControl(int servoID) {
		switch (servoID) {
			case 0: // mainServo

//				if (mainServo.getPosition() >= 0.0 && this.gamepad2.a) {
//					position_mainServo -= MAIN_ADJUST;
//					sleep(5);
//				} else if (mainServo.getPosition() <= 1.0 && this.gamepad2.b) {
//					position_mainServo += MAIN_ADJUST;
//					sleep(5);
//				}
//				mainServo.setPosition(position_mainServo);
				break;
			case 1: // markerServo
				if (this.gamepad2.x) {
					position_markerServo = 1;
					sleep(5);
				} else if (this.gamepad2.y) {
					position_markerServo = 0;
					sleep(5);
				}
				markerServo.setPosition(position_markerServo);
				break;
			case 2: // hand
				if (hand.getPosition() > 0.6 && this.gamepad2.left_trigger > 0.2) {
					position_hand -= HAND_ADJUST;
					sleep(5);
				} else if (hand.getPosition() < 1.0 && this.gamepad2.right_trigger > 0.2) {
					position_hand += HAND_ADJUST;
					sleep(5);
				}
				hand.setPosition(position_hand);
				break;
		}
	}
	
	private double conServo(int id) {
		double power = 0.0;
		switch (id) {
			case 0: // arm extend
				if (this.gamepad2.right_bumper)
					power = 1.0;
				else if (this.gamepad2.left_bumper)
					power = -1.0;
				break;
		}
		return power;
	}
	
	// Automatically runs
	@Override
	public void runOpMode() {
		// Updates telemetry (log) to show it is running
		telemetry.addData("Status", "Updated!");
		telemetry.update();
		
		// Initializes hardware
        linearSlide = hardwareMap.dcMotor.get("linearSlide");
        mainShoulder = hardwareMap.dcMotor.get("mainShoulder");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        mainServo = hardwareMap.servo.get("mainServo");
        markerServo = hardwareMap.servo.get("markerServo");
		hand = hardwareMap.servo.get("hand");
		armExtend = hardwareMap.crservo.get("armExtend");

		mainServo.setPosition(position_mainServo);
		markerServo.setPosition(position_markerServo);
		leftDrive.setDirection(DcMotor.Direction.REVERSE);
		rightDrive.setDirection(DcMotor.Direction.FORWARD);
		
		waitForStart();
		
		// Game loop
		while (opModeIsActive()) {
			
			// Obtains value from power() function and sets the motors power to that
			leftDrive.setPower(drivePower(0));
			rightDrive.setPower(drivePower(1));
			linearSlide.setPower(drivePower(2));
			mainShoulder.setPower(drivePower(3));
			
			// Controls continuous servos
			armExtend.setPower(conServo(0));
			
			// Controls servos
			servoControl(0);
			servoControl(1);
			servoControl(2);
			
			// Updates telemetry to show power levels
			telemetry.addData("Motors", "left (%.2f), right (%.2f)", drivePower(0), drivePower(1));
			telemetry.addData("Linear Slide", "pwr (%.2f)", drivePower(2));
			telemetry.update();
		}
	}
}
