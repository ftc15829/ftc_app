package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//@Disabled
@Autonomous(name = "MarkerToPit")

public class MarkerToPit extends LinearOpMode
{
	
	// Defines hardware
	private DcMotor leftDrive;
	private DcMotor rightDrive;
	private DcMotor linearSlide;
	private DcMotor intakeArm;
	private CRServo linearServo;
	private GoldAlignDetector detector;
	private int caseNum;
	
	private Telemetry.Item Status = telemetry.addData("Status", "Initialized");
	private Telemetry.Item SubStatus = telemetry.addData("Sub-Status", "");
	private Telemetry.Item Case = telemetry.addData("Case", "");
	
	private void end() {
		detector.disable();
		stop();
	}
	
	private void stopDriving() {
		drive(0);
	}
	
	private void drive(double power) {
		leftDrive.setPower(power);
		rightDrive.setPower(power);
	}
	
	private void driveDistance(double revolutions, double power) {
		leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		
		leftDrive.setTargetPosition((int) (revolutions * 1440));
		rightDrive.setTargetPosition((int) (revolutions * 1440));
		leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		drive(power);
		while (leftDrive.isBusy() && rightDrive.isBusy()) { /*wait*/ }
		stopDriving();
		
		leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}
	
	private void turn(double turnUnit, double power) {
		leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		
		leftDrive.setTargetPosition((int) (turnUnit * 1440));
		rightDrive.setTargetPosition((int) (-turnUnit * 1440));
		leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		drive(power);
		while (leftDrive.isBusy() && rightDrive.isBusy()) { /*wait*/ }
		stopDriving();
		
		leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
	}
	
	private void dropMarker() {
		Status.setValue("Dropping Marker");
		
		// Intake Arm Down
		SubStatus.setValue("Lowering Arm");
		telemetry.update();
		intakeArm.setTargetPosition(-1200);
		intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		intakeArm.setPower(0.5);
		while (intakeArm.isBusy()) { /*wait*/ }
		intakeArm.setPower(0);
		
		// Intake Arm Up
		SubStatus.setValue("Raising Arm");
		telemetry.update();
		intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		intakeArm.setTargetPosition(-60);
		
		intakeArm.setPower(-0.5);
		while (intakeArm.isBusy()) { /*wait*/ }
		intakeArm.setPower(0);
		
		intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		
		Status.setValue("Running");
		SubStatus.setValue("");
		telemetry.update();
	}
	
	private void lower() {
		Status.setValue("Lowering");
		
		// Linear Slide Up
		SubStatus.setValue("Lowering Robot");
		telemetry.update();
		linearSlide.setTargetPosition(7900);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		linearSlide.setPower(0.5);
		while (linearSlide.isBusy()) { /*wait*/ }
		linearSlide.setPower(0);
		
		// Servo
		SubStatus.setValue("Driving Forward");
		telemetry.update();
		linearServo.setPower(0.5);
		sleep(200);
		driveDistance(1, 0.3);
		linearServo.setPower(-0.5);
		sleep(800);
		linearServo.setPower(0);
		
		// Linear Slide Down
		SubStatus.setValue("Lowering Linear Slide");
		telemetry.update();
		linearSlide.setTargetPosition(0);
		leftDrive.setTargetPosition(1440);
		rightDrive.setTargetPosition(1440);
		leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		linearSlide.setPower(1);
		leftDrive.setPower(0.3);
		rightDrive.setPower(0.3);
		while (linearSlide.isBusy()) { /*wait*/ }
		linearSlide.setPower(0);
		stopDriving();
		
		leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		linearSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		
		Status.setValue("Running");
		SubStatus.setValue("");
		telemetry.update();
	}
	
	private void alignGold() {
		Status.setValue("Aligning");
		SubStatus.setValue("Finding Gold");
		telemetry.update();
		
		if (detector.isFound()) {
			caseNum = 1;
		}
		
		if (!detector.isFound()) {
			turn(-0.4, 0.4);
			if (detector.isFound())
				caseNum = 0;
		}
		
		if (!detector.isFound()) {
			turn(0.8, 0.4);
			caseNum = 2;
		}
		
		SubStatus.setValue("Fine-Tuning");
		telemetry.update();
		
		while (detector.getXPosition() < ((320 + detector.alignPosOffset) - (detector.alignSize / 2))) {
			leftDrive.setPower(-0.4);
			rightDrive.setPower(0.4);
		}
		
		while (detector.getXPosition() > ((320 + detector.alignPosOffset) + (detector.alignSize / 2))) {
			leftDrive.setPower(0.4);
			rightDrive.setPower(-0.4);
		}
		
		leftDrive.setPower(0);
		rightDrive.setPower(0);
		
		Status.setValue("Running");
		SubStatus.setValue("");
		telemetry.update();
	}
	
	private void run()
	{
		Status.setValue("Running");
		telemetry.update();
		
		lower();
		alignGold();
		switch (caseNum)
		{
			case 0: // Gold is Left
			{
				Case.setValue("Left");
				telemetry.update();
				
				driveDistance(3.0, 0.5);
				turn(1.5, 0.5);
				driveDistance(1.9, 0.5);
				dropMarker();
				driveDistance(-6.5, -0.5);
				break;
			}
			case 1: // Gold is Middle
			{
				Case.setValue("Middle");
				telemetry.update();
				
				driveDistance(3.0, 1);
				dropMarker();
				driveDistance(-0.2, 1);
				turn(-0.6, 1);
				driveDistance(0.5, 1);
				turn(-0.6, 1);
				driveDistance(0.7, 1);
				turn(-1.1, 1);
				driveDistance(6.0, 1);
				break;
			}
			case 2: // Gold is Right
			{
				Case.setValue("Right");
				telemetry.update();
				
				driveDistance(2.9, 1);
				turn(-1.5, .5);
				driveDistance(1.9, 1);
				dropMarker();
				driveDistance(-0.3, 1);
				turn(-0.8, 1);
				driveDistance(2.1, 1);
				turn(-0.7, 1);
				driveDistance(10, 1);
				break;
			}
		}
		end();
	}
	
	@Override
	public void runOpMode()
	{
		// Updates telemetry (log) to show it is running
		telemetry.addData("Status", "Initialized");
		telemetry.addData("Sub-Status", "");
		telemetry.update();
		
		// Initializes hardware
		leftDrive = hardwareMap.dcMotor.get("leftDrive");
		rightDrive = hardwareMap.dcMotor.get("rightDrive");
		linearSlide = hardwareMap.dcMotor.get("linearSlide");
		linearServo = hardwareMap.crservo.get("linearServo");
		intakeArm = hardwareMap.dcMotor.get("intakeArm");
		intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
		
		linearSlide.setTargetPosition(0);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		linearSlide.setPower(0.5);
		while (linearSlide.isBusy())
		{ /*wait*/ }
		linearSlide.setPower(0);
		
		waitForStart();
		
		run();// Runs main function
	}
}
