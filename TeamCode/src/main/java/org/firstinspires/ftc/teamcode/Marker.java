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
    private Servo markerServo;
//    private GoldAlignDetector detector;

    private void drive(double power)
    {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }

    private void driveDistance(double revolutions, double power)
    {
        leftDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);

        leftDrive.setTargetPosition((int) (revolutions * 1440));
        rightDrive.setTargetPosition((int) (revolutions * 1440));


        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(power);

        while (leftDrive.isBusy() && rightDrive.isBusy())
        {

        }

        stopDriving();

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);

    }

    private void stopDriving()
    {
        drive(0);
    }

    private void lower()
    {
        telemetry.addData("Sub Status", "Lowering");
        telemetry.update();
        linearSlide.setMode(DcMotor.RunMode.RESET_ENCODERS);
        linearSlide.setTargetPosition(-3440);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlide.setPower(0.5);
        while (linearSlide.isBusy()) {

        }
        linearSlide.setPower(0);

        sleep(3000);
//        driveDistance(2, 0.5);

        linearSlide.setMode(DcMotor.RunMode.RESET_ENCODERS);
        linearSlide.setTargetPosition(3440);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        linearSlide.setPower(0.5);
        while (linearSlide.isBusy()) {

        }
        linearSlide.setPower(0);
        linearSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
    }

    private void turn(double turnUnit, double power)
    {
        leftDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);

        leftDrive.setTargetPosition((int) (turnUnit * 1440));
        rightDrive.setTargetPosition((int) (-turnUnit * 1440));


        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(power);

        while (leftDrive.isBusy() && rightDrive.isBusy())
        {

        }

        stopDriving();

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

    }


    private void run()
    {
        telemetry.addData("Status", "Running");
        telemetry.update();
        lower();
        sleep(5000);
        stop();
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
        markerServo = hardwareMap.servo.get("markerServo");
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        markerServo.setPosition(0.0);


//        detector = new GoldAlignDetector(); // Create detector
//        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
//        detector.useDefaults(); // Set detector to use default settings
//        detector.alignSize = 100; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
//        detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
//        detector.downscale = 0.4; // How much to downscale the input frames
//        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
//        detector.maxAreaScorer.weight = 0.005;
//        detector.ratioScorer.weight = 5;
//        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment
//        detector.enable(); // Start the detector!

        waitForStart();

//        if (detector.getAligned())
//        {
//        }
//        else if (detector.getXPosition() < 230)
//        {
//            while (!detector.getAligned())
//            {
//                //Turn left
//            }
//        }
//        else if (detector.getXPosition() > 310)
//        {
//            while (!detector.getAligned())
//            {
//                //turn right
//            }
//        }
        run();// Runs main function
    }
}
