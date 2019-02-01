package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

//@Disabled
@Autonomous(name = "Marker")

public class Marker extends LinearOpMode {

    //Defines hardware
//    private DcMotor linearSlide;
    private DcMotor leftDrive;
    private DcMotor rightDrive;
//    private CRServo mainServo;
//    private Servo markerServo;

    private double position_mainServo = 0.7; // Starts servo at position
    private double position_markerServo = 0.0;

    private void drive(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }

    private void driveDistance(int revolutions, double power) {
        leftDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);

        leftDrive.setTargetPosition(revolutions * 1440);
        rightDrive.setTargetPosition(revolutions * 1440);


        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(power);

        while (leftDrive.isBusy() && rightDrive.isBusy()) {

        }

        stopDriving();

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

    }

    private void stopDriving() {
        drive(0);
    }

    private void run() {

        driveDistance(1,1.0);
        stop();
//        linearSlide.setPower(1);
//        sleep(2800);
//
//        linearSlide.setPower(0);
//        mainServo.setPower(1);
//        sleep(200);
//
//        leftDrive.setPower(-0.7);
//        rightDrive.setPower(-0.7);
//        sleep(500);
//        mainServo.setPower(0);
//        sleep(3500);
//
//        leftDrive.setPower(0);
//        rightDrive.setPower(0);
//        markerServo.setPosition(1.0);
//        linearSlide.setPower(-1);
//        sleep(1000);
//
//        leftDrive.setPower(0.7);
//        rightDrive.setPower(0.7);
//        linearSlide.setPower(0);
//        sleep(1000);
//
//        leftDrive.setPower(0);
//        rightDrive.setPower(0);
//
//        stop();
    }

    // Initializes hardware
    @Override
    public void runOpMode() {

        // Updates telemetry (log) to show it is running
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initializes hardware
//        linearSlide = hardwareMap.dcMotor.get("linearSlide");
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
//        mainServo = hardwareMap.crservo.get("mainServo");
//        markerServo = hardwareMap.servo.get("markerServo");

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        //	mainServo.setPower(position_mainServo);
//        markerServo.setPosition(position_markerServo);

        waitForStart();

        // Runs main function
        run();
    }
}
