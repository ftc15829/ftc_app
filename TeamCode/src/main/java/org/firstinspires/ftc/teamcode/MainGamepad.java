/*
 * E-transfer from onBot Java to android studio for gold sensor
 * E-shout-out to team #### mechanical memes
 * C-control scheme
 * E-skipped blocks because we knew how limiting it would be in the future
 * C-proud of autonomous; big learning curve when dealing with encoders but it worked out really well
 * C-hot fixing
 * C-started off with amazing autonomous
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.jetbrains.annotations.Contract;

import static java.lang.Math.abs;

//@Disabled
@TeleOp(name = "Main Gamepad")

public class MainGamepad extends LinearOpMode
{
	
	// Defines hardware
	private DcMotor leftDrive;
	private DcMotor rightDrive;
	private DcMotor linearSlide;
	private DcMotor intakeArm;
	private DcMotor dunkArm;
	private DcMotor dunkSlide;
	private DcMotor intake;
	private CRServo linearServo;
	private CRServo grabExtend;
	private boolean spin;
	private boolean spinBack;
	// Sets the power of the various motors
	private double drivePower(int id)
	{
		
		double power = 0.0; // Initializes power level with a default value of 0
		double driveMod = 1; // Modifier for left and right drive's speed control
		final double CORRECTION = 1.0; // Adjusts leftDrives power to account for unwanted steering
		
		// Adjusts drive speed modifier
		if (this.gamepad1.left_bumper)
			driveMod = 0.5;
		if (this.gamepad1.right_bumper)
			driveMod = 0.75;
		
		// Takes the motor ID and sets the power
		switch (id)
		{
			case 0: // leftDrive
				power = (this.gamepad1.left_stick_y * driveMod) * CORRECTION;
				break;
			
			case 1: // rightDrive
				power = (this.gamepad1.right_stick_y * driveMod);
				break;
			
			case 2: // linearSlide
				if (this.gamepad2.dpad_up/* && linearSlide.getCurrentPosition() < (int)(5.0 * 1440)*/)
					power = 0.5;
				else if (this.gamepad2.dpad_down/* && linearSlide.getCurrentPosition() > 0*/)
					power = -0.5;
				break;
			
			case 3: // intakeArm
				power = -this.gamepad2.right_stick_y * 0.6;
				break;
			
			case 4: // dunkArm
				//if (dunkArm.getCurrentPosition() < (int) (0.7 * 1440) && dunkArm.getCurrentPosition() > 0)
				power = this.gamepad2.left_stick_y * 1;
				break;
			
			case 5: // dunkSlide
				if (this.gamepad2.left_bumper/* && dunkSlide.getCurrentPosition() > (int) (1.0 * 1440)*/)
					power = -1;
				else if (this.gamepad2.right_bumper/* && dunkSlide.getCurrentPosition() < 0*/)
					power = 1;
				break;
			case 6: // intake
//				if (this.gamepad1.right_trigger != 0 && !spin)
//				{
//					spin = true;
//					spinBack = false;
//				}
//				else if (this.gamepad1.left_trigger != 0 && !spinBack)
//				{
//					spinBack = true;
//					spin = false;
//				}
//
//				else if (spin && this.gamepad1.right_trigger != 0)
//				{
//					spin = false;
//					spinBack = false;
//				}
//
//				else if (spinBack && this.gamepad1.left_trigger != 0)
//				{
//					spinBack = false;
//					spin = false;
//				}
//
//
//				if (spin)
//					power = -1;
//				else if (spinBack)
//					power = 1;
				if (this.gamepad1.right_trigger != 0)
					power = -this.gamepad1.right_trigger;
				else if (this.gamepad1.left_trigger != 0)
					power = this.gamepad1.left_trigger;
				break;
		}
		return power;
	}
	
	
	// Controls servos
	private double servo(int id)
	{
		double change = 0.0;
		double INCREMENT = 0.1;

//        switch (id)
//        {
//			case 0: // markerServo
//				if (this.gamepad2.x && markerServo.getPosition() < 0.8)
//					change = INCREMENT;
//				else if (!this.gamepad2.x && markerServo.getPosition() <= 0.8)
//				    change = (markerServo.getPosition() > 0 ? -INCREMENT : 0);
//				else if (markerServo.getPosition() >= 0.8 && this.gamepad2.x)
//                    change = -INCREMENT;
//				break;
//        }
		return change;
	}
	
	private double conServo(int id)
	{
		double power = 0.0; // Initializes power level with a default value of 0
		
		switch (id)
		{
			case 0: // linearServo
				if (this.gamepad2.dpad_right)
					power = 1.0;
				else if (this.gamepad2.dpad_left)
					power = -1.0;
				break;
			case 1: // intake
//				if (this.gamepad1.left_trigger != 0)
//					power = -this.gamepad1.left_trigger;
//				else if (this.gamepad1.right_trigger != 0)
//					power = this.gamepad1.right_trigger;
				break;
			case 2:
				
				if (this.gamepad1.dpad_up)
					power = -1;
				else if (this.gamepad1.dpad_down)
					power = 1;
				break;
		}
		return power;
	}
	
	// Automatically runs
	@Override
	public void runOpMode()
	{
		// Updates telemetry (log) to show it is running
		telemetry.addData("Status", "Initialized");
		telemetry.update();
		
		// Initializes hardware
		leftDrive = hardwareMap.dcMotor.get("leftDrive");
		rightDrive = hardwareMap.dcMotor.get("rightDrive");
		linearSlide = hardwareMap.dcMotor.get("linearSlide");
		intakeArm = hardwareMap.dcMotor.get("intakeArm");
		dunkArm = hardwareMap.dcMotor.get("dunkArm");
		dunkSlide = hardwareMap.dcMotor.get("dunkSlide");
		intake = hardwareMap.dcMotor.get("intake");
		linearServo = hardwareMap.crservo.get("linearServo");
		grabExtend = hardwareMap.crservo.get("grabExtend");
		leftDrive.setDirection(DcMotor.Direction.REVERSE);
		rightDrive.setDirection(DcMotor.Direction.FORWARD);
		leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		dunkArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		dunkSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		
		waitForStart();
		
		// Game loop
		while (opModeIsActive())
		{
			
			// Controls motors
			leftDrive.setPower(drivePower(0));
			rightDrive.setPower(drivePower(1));
			linearSlide.setPower(drivePower(2));
			intakeArm.setPower(drivePower(3));
			dunkArm.setPower(drivePower(4));
			dunkSlide.setPower(drivePower(5));
			intake.setPower(drivePower(6));
			
			// Controls continuous servos
			linearServo.setPower(conServo(0));
			grabExtend.setPower(conServo(2));
			
			// Controls servos
			
			
			// Reset Encoders
			if (this.gamepad1.x)
			{
				linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				intakeArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				dunkArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				dunkSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
				linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
				intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
				dunkArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
				dunkSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
			}
			
			// Updates telemetry to show power levels
			telemetry.addData("Motors", "left (%.2f), right (%.2f)", drivePower(0), drivePower(1));
			telemetry.addData("Linear Slide", "pwr (%.2f)", drivePower(2));
			telemetry.addData("dunkSlide", dunkSlide.getCurrentPosition());
			telemetry.addData("linearSlide", linearSlide.getCurrentPosition());
			telemetry.addData("intakeArm", intakeArm.getCurrentPosition());
			telemetry.addData("dunkArm", dunkArm.getCurrentPosition());
			telemetry.update();
		}
	}
}
