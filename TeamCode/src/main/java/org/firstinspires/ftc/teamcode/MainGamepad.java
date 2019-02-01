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
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor linearSlide;
//	private DcMotor sweeperArm;
//    private DcMotor dunkArm;
    private CRServo linearServo;
//	private CRServo sweeper;
//    private Servo markerServo;

    // Sets initialization position
	private double position_markerServo = 0.0;
	
	// Sets the power of the various motors
	private double drivePower(int id) {
		double power = 0.0; // Initializes power level with a default value of 0
		double driveMod = 1; // Modifier for left and right drive's speed control
		final double CORRECTION = 1.0; // Adjusts leftDrives power to account for unwanted steering
		
		// Adjusts drive speed modifier
		if (this.gamepad1.left_bumper)
			driveMod = 0.5;
		if (this.gamepad1.right_bumper)
			driveMod = 0.75;
		
		// Takes the motor ID and sets the power
		switch (id) {
			case 0: // leftDrive
				power = (this.gamepad1.left_stick_y * driveMod) * CORRECTION;
				break;
			case 1: // rightDrive
				power = (this.gamepad1.right_stick_y * driveMod);
				break;
			case 2: // linearSlide
				power = this.gamepad2.left_stick_y * -0.5;
				break;
			case 3: // sweeper
				power = this.gamepad2.right_stick_y * 0.5;
				break;
		}
		return power;
	}


	// Controls servos
	private double servo(int id) {
	    double change = 0.0;
        double INCREMENT = 0.1;

		switch (id) {
			case 0: // markerServo
				if (this.gamepad2.x)
					change = INCREMENT;
				else if (this.gamepad2.y)
					change = -INCREMENT;
				break;
		}
		return change;
	}
	
	private double conServo(int id) {
		double power = 0.0; // Initializes power level with a default value of 0

		switch (id) {
			case 0: // linearServo
				if (this.gamepad2.right_bumper)
					power = 1.0;
				else if (this.gamepad2.left_bumper)
					power = -1.0;
				break;
            case 1: // sweeper
                if(this.gamepad2.a)
                    power = 1.0;
                else if (this.gamepad2.b)
                    power = -1.0;
                break;
		}
		return power;
	}
	
	// Automatically runs
	@Override
	public void runOpMode() {
		// Updates telemetry (log) to show it is running
		telemetry.addData("Status", "Initialized");
		telemetry.update();
		
		// Initializes hardware
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        linearSlide = hardwareMap.dcMotor.get("linearSlide");
        linearServo = hardwareMap.crservo.get("linearServo");
//        sweeper = hardwareMap.crservo.get("sweeper");
//        markerServo = hardwareMap.servo.get("markerServo");

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
		rightDrive.setDirection(DcMotor.Direction.FORWARD);

//        markerServo.setPosition(position_markerServo);

        waitForStart();
		
		// Game loop
		while (opModeIsActive()) {
			
			// Controls motors
			leftDrive.setPower(drivePower(0));
			rightDrive.setPower(drivePower(1));
			linearSlide.setPower(drivePower(2));
//            dunkArm.setPower(drivePower(3));

			// Controls continuous servos
            linearServo.setPower(conServo(0));
//            sweeper.setPower(conServo(1));

			// Controls servos
//            markerServo.setPosition(markerServo.getPosition() + servo(0));

			// Updates telemetry to show power levels
			telemetry.addData("Motors", "left (%.2f), right (%.2f)", drivePower(0), drivePower(1));
			telemetry.addData("Linear Slide", "pwr (%.2f)", drivePower(2));
			telemetry.update();
		}
	}
}
