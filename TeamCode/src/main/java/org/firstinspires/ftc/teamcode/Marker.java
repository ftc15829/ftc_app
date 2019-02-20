package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//@Disabled
@Autonomous(name = "Marker")

public class Marker extends LinearOpMode
{
	
	// Defines hardware
	private DcMotor leftDrive;
	private DcMotor rightDrive;
	private DcMotor linearSlide;
	private CRServo linearServo;
	private GoldAlignDetector detector;
	
	private void drive(double power)
	{
		leftDrive.setPower(power);
		rightDrive.setPower(power);
	}
	
	private void driveDistance(double revolutions, double power)
	{
		leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		
		leftDrive.setTargetPosition((int) (revolutions * 1440));
		rightDrive.setTargetPosition((int) (revolutions * 1440));
		
		leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		drive(power);
		
		while (leftDrive.isBusy() && rightDrive.isBusy())
		{
		}
		
		stopDriving();
		
		leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		
	}
	
	private void stopDriving()
	{
		drive(0);
	}
	
	private void lower()
	{
		telemetry.addData("Sub Status", "Lowering");
		telemetry.update();
		
//		linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		linearSlide.setTargetPosition(-3440);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		linearSlide.setPower(0.5);
		while (linearSlide.isBusy())
		{
		
		}
		linearSlide.setPower(0);
		
		sleep(500);
		driveDistance(0.25, 0.1);
		
//		linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		linearSlide.setTargetPosition(3440);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		linearSlide.setPower(0.5);
		while (linearSlide.isBusy())
		{
		
		}
		linearSlide.setPower(0);
		linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}
	
	private void turn(double turnUnit, double power)
	{
		leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		
		leftDrive.setTargetPosition((int) (turnUnit * 1440));
		rightDrive.setTargetPosition((int) (-turnUnit * 1440));
		
		leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		drive(power);
		
		while (leftDrive.isBusy() && rightDrive.isBusy())
		{ /*wait*/ }
		
		stopDriving();
		
		leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		
	}
	
	private void alignGold()
	{
		if (!detector.isFound())
		{
			turn(-0.3, 0.2);
			if (!detector.isFound())
			{
				turn(0.6, 0.2);
				if (!detector.isFound())
				{
					end();
				}
			}
		}
		if (detector.getAligned())
		{
		}
		else if (detector.getXPosition() < 320 - (detector.alignSize / 2))
		{
			while (!detector.getAligned())
			{
				leftDrive.setPower(-0.2);
				rightDrive.setPower(0.2);
			}
		}
		else if (detector.getXPosition() > 320 + (detector.alignSize / 2))
		{
			while (!detector.getAligned())
			{
				leftDrive.setPower(0.2);
				rightDrive.setPower(-0.2);
				
			}
		}
	}
	
	private void end()
	{
		detector.disable();
		stop();
	}
	
	private void run()
	{
		telemetry.addData("Status", "Running");
		telemetry.update();
		while (!isStopRequested())
		{
			
			lower();
//			alignGold();
//			driveDistance(2, 0.7);
//			turn(2, 0.2);
//			driveDistance(2, 0.7);
			end();
		}
		end();
	}
	
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
		linearServo = hardwareMap.crservo.get("linearServo");
		leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		leftDrive.setDirection(DcMotor.Direction.FORWARD);
		rightDrive.setDirection(DcMotor.Direction.REVERSE);
		
		
		detector = new GoldAlignDetector(); // Create detector
		detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
		detector.useDefaults(); // Set detector to use default settings
		detector.alignSize = 200; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
		detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
		detector.downscale = 0.4; // How much to downscale the input frames
		detector.areaScoringMethod = DogeCV.AreaScoringMethod.PERFECT_AREA; // Can also be PERFECT_AREA
		detector.perfectAreaScorer.perfectArea = 10000;
		detector.maxAreaScorer.weight = 0.005;
		detector.ratioScorer.weight = 5;
		detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment
		detector.enable(); // Start the detector!
		
		waitForStart();
		
		run();// Runs main function
	}
}
