package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.CRServo;
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
	private int caseNum;
	
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
		
		linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		linearSlide.setTargetPosition(7200);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		linearSlide.setPower(0.5);
		while (linearSlide.isBusy())
		{
		
		}
		linearSlide.setPower(0);
		linearServo.setPower(.25);
		sleep(500);
		
		driveDistance(1, 0.3);
		linearServo.setPower(0);
//		linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		linearSlide.setTargetPosition(0);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		linearSlide.setPower(0.5);
		while (linearSlide.isBusy())
		{
		
		}
		linearSlide.setPower(0);
		linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		telemetry.addData("Sub Status", "End Lowering");
		telemetry.update();
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
		telemetry.addData("Error Code", "Aligning");
		telemetry.update();
		if (!detector.isFound())
		{
			telemetry.addData("Error Code", "Just in case 1");
			telemetry.update();
			sleep(2000);
			
			turn(-0.25, 0.2);
			if (!detector.isFound())
			{
				telemetry.addData("Error Code", "Just in case 2");
				telemetry.update();
				sleep(2000);
				
				turn(0.5, 0.2);
				if (!detector.isFound())
				{
					telemetry.addData("Error Code", "Just in case 3");
					telemetry.update();
					sleep(2000);
					end();
				}
			}
		}
		if (detector.getAligned())
		{
			telemetry.addData("Error Code", "not good");
			telemetry.update();
			sleep(2000);
			caseNum = 1;
		}
		
		else if (detector.getXPosition() < 310)
		{
			telemetry.addData("Error Code", "Sure I guess");
			telemetry.update();
			sleep(2000);
			
			caseNum = 0;
			while (detector.getXPosition() < 310)
			{
				leftDrive.setPower(-0.4);
				rightDrive.setPower(0.4);
			}
			leftDrive.setPower(0);
			rightDrive.setPower(0);
		}
		else if (detector.getXPosition() > 460)
		{
			caseNum = 2;
			while (detector.getXPosition() > 460)
			{
				leftDrive.setPower(0.4);
				rightDrive.setPower(-0.4);
				
			}
			leftDrive.setPower(0);
			rightDrive.setPower(0);
		}
		else
		{
			telemetry.addData("Error Code", "Yup, no good indeed");
			telemetry.update();
			sleep(2000);
			caseNum = 1;
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
			alignGold();
			sleep(2000);
//			driveDistance(1.5, 0.7);
			switch (this.caseNum)
			{
				case 0:
				{
					telemetry.addData("case 0", "true");
					telemetry.update();
					sleep(2000);
					break;
				}
				case 1:
				{
					telemetry.addData("case 1", "true");
					telemetry.update();
					sleep(2000);
//					driveDistance(2.2,0.5);
					break;
				}
				case 2:
				{
					telemetry.addData("case 2", "true");
					telemetry.update();
					sleep(2000);
					break;
				}
			}
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
		detector.alignSize = 150; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
		detector.alignPosOffset = 100; // How far from center frame to offset this alignment zone.
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
