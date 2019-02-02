package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

public class Auto {
    Auto() {

    }

    // Defines hardware
    private static DcMotor leftDrive;
    private static DcMotor rightDrive;
    private static DcMotor linearSlide;
    private static CRServo linearServo;
    private static Servo markerServo;

    public static void drive(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }

    public static void driveDistance(double revolutions, double power) {
        leftDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);

        leftDrive.setTargetPosition((int) (revolutions * 1440));
        rightDrive.setTargetPosition((int) (revolutions * 1440));


        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(power);

        while (leftDrive.isBusy() && rightDrive.isBusy()) {

        }

        stopDriving();

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

    }

    public static void stopDriving() {
        drive(0);
    }

    public static void lowerSlide() {
        /*
        lower slide and turn servo
         */
        driveDistance(2, 0.5);
        /*
        lower slide fully
         */
    }

    public static void turn(double turnUnit, double power) {
        leftDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);

        leftDrive.setTargetPosition((int) (turnUnit * 1440));
        rightDrive.setTargetPosition((int) (-turnUnit * 1440));


        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(power);

        while (leftDrive.isBusy() && rightDrive.isBusy()) {

        }

        stopDriving();

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

    }
}
