package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous

public class PitAuto extends LinearOpMode {
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor linearSlide;
    Servo mainServo;
    Servo markerServo;

    double position_mainServo = 0.3; // Starts servo at position
    double position_markerServo = 1.0;

    void run(){
        linearSlide.setPower(1);
        sleep(2800);
        linearSlide.setPower(0);
        leftDrive.setPower(-1);
        rightDrive.setPower(-1);
        mainServo.setPosition(1);
        sleep(4000);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        linearSlide.setPower(-1);
        sleep(1000);
        stop();
    }


    // Main function
    @Override
    public void runOpMode(){
        leftDrive=hardwareMap.dcMotor.get("leftDrive");
        rightDrive=hardwareMap.dcMotor.get("rightDrive");
        linearSlide=hardwareMap.dcMotor.get("linearSlide");
        mainServo=hardwareMap.servo.get("mainServo");
        markerServo=hardwareMap.servo.get("markerServo");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        mainServo.setPosition(position_mainServo);
        markerServo.setPosition(position_markerServo);

        waitForStart();
        // runtime.reset();


        run();

    }
}


