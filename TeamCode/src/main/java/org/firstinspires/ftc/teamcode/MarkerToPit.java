package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Marker to Pit")

public class MarkerToPit extends LinearOpMode {
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor linearSlide;
    private Servo mainServo;
    private Servo markerServo;
    
    private double position_mainServo = 0.7; // Starts servo at position
    private double position_markerServo = 0.0;

    private void run(){
        linearSlide.setPower(1);
        sleep(3000);
        
        linearSlide.setPower(0);
        mainServo.setPosition(1);
        sleep(200);
        
        leftDrive.setPower(-0.7);
        rightDrive.setPower(-0.7);
        sleep(4500);
        
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        markerServo.setPosition(1.0);
        linearSlide.setPower(-1);
        sleep(2600);
        
        leftDrive.setPower(0.7);
        rightDrive.setPower(0.7);
        sleep(400);
        
        linearSlide.setPower(0);    
        leftDrive.setPower(-0.7);
        rightDrive.setPower(0.7);
        sleep(1100);
        
        leftDrive.setPower(-0.7);
        rightDrive.setPower(-0.7);
        sleep(750);
        
        leftDrive.setPower(-0.7);
        rightDrive.setPower(0.7);
        sleep(1440);
        
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        sleep(100);
        
        leftDrive.setPower(-0.7);
        rightDrive.setPower(-0.7);
        sleep(5600);
        
        stop();
    }

    // Initializes hardware
    @Override
    public void runOpMode(){
        linearSlide=hardwareMap.dcMotor.get("linearSlide");
        leftDrive=hardwareMap.dcMotor.get("leftDrive");
        rightDrive=hardwareMap.dcMotor.get("rightDrive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        mainServo=hardwareMap.servo.get("mainServo");
        markerServo=hardwareMap.servo.get("markerServo");
        mainServo.setPosition(position_mainServo);
        markerServo.setPosition(position_markerServo);

        waitForStart();

        // Runs main function
        run();
    }
}
