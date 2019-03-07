package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//@Disabled
@Autonomous(name = "Marker")

public class Marker extends OpMode {
	
	// Defines hardware
	private DcMotor leftDrive;
	private DcMotor rightDrive;
	private DcMotor linearSlide;
	private DcMotor intakeArm;
	private CRServo linearServo;
	private GoldAlignDetector detector;
	private int caseNum;
	
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
	
	private void stopDriving() {
		drive(0);
	}
	
	private void dropMarker() {
		telemetry.addData("Status", "Dropping Marker");
		telemetry.update();
		
		intakeArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
		
		// Intake Arm Down
		intakeArm.setTargetPosition(-1200);
		intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		intakeArm.setPower(0.5);
		while (intakeArm.isBusy()) { /*wait*/ }
		intakeArm.setPower(0);
		
		// Intake Arm Up
		intakeArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		intakeArm.setTargetPosition(-60);
		
		intakeArm.setPower(-0.5);
		while (intakeArm.isBusy()) { /*wait*/ }
		intakeArm.setPower(0);
	}
	
	private void lower() {
		telemetry.addData("Status", "Lowering");
		telemetry.update();
		
		leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		
		// Linear Slide Up
		linearSlide.setTargetPosition(7900);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		linearSlide.setPower(0.5);
		while (linearSlide.isBusy()) { /*wait*/ }
		linearSlide.setPower(0);
		
		// Servo
		linearServo.setPower(0.5);
//		sleep(200);
		driveDistance(1, 0.3);
		linearServo.setPower(-0.5);
//		sleep(800);
		linearServo.setPower(0);
		
		// Linear Slide Down
		linearSlide.setTargetPosition(0);
		leftDrive.setTargetPosition(1440);
		rightDrive.setTargetPosition(1440);
		leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		
		linearSlide.setPower(0.5);
		leftDrive.setPower(0.3);
		rightDrive.setPower(0.3);
		while (linearSlide.isBusy()) { /*wait*/ }
		linearSlide.setPower(0);
		stopDriving();
		
		leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}
	
	private void alignGold() {
		telemetry.addData("Status", "Aligning");
		
		if (!detector.isFound()) {
			turn(-0.3, 0.4);
			if (detector.isFound())
				caseNum = 0;
		}
		
		if (!detector.isFound()) {
			turn(0.6, 0.4);
			if (detector.isFound())
				caseNum = 1;
		}
		
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
	}
	
	@Override
	public void init() {
		// Updates telemetry (log) to show it is running
		telemetry.addData("Status", "Initializing");
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
		detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
		detector.alignPosOffset = 250; // How far from center frame to offset this alignment zone.
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
		while (linearSlide.isBusy()) { /*wait*/ }
		linearSlide.setPower(0);
	}
	
	@Override
	public void init_loop() {
	
	}
	
	@Override
	public void start() {
		telemetry.addData("Status", "Running");
		telemetry.update();

		lower();
//		alignGold();
//		switch (caseNum) {
//			case 0:
//			{
//				telemetry.addData("case 0", "true");
//
//				driveDistance(3.0,0.5);
//				turn(1.5,0.5);
//				driveDistance(1.9,0.5);
//				dropMarker();
//				driveDistance(-6.5,-0.5);
//
//				break;
//			}
//			case 1:
//			{
//				telemetry.addData("case 1", "true");
//
//				driveDistance(3.5,0.5);
//				dropMarker();
//				turn(-1.7,0.5);
//				driveDistance(7.0,.75);
//
//				break;
//			}
//			case 2:
//			{
//				telemetry.addData("case 2", "true");
//
//				driveDistance(2.7,0.5);
//				turn(-1.2,.25);
//				driveDistance(1.7,.25);
//				dropMarker();
//				driveDistance(-10,.5);
//
//				break;
//			}
//		}
//		stop();
	}
	
	@Override
	public void loop() {
//		telemetry.addData("IsAligned", detector.getAligned());
//		telemetry.addData("X Pos", detector.getXPosition());
//		telemetry.update();
	}
	
	@Override
	public void stop() {
		if (detector != null)
			detector.disable();
	}
}
