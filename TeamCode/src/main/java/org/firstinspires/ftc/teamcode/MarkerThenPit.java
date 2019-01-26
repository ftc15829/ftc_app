package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

// Autonomous means that the robot may recieve no gamepad input and complete tasks using this program only
@Autonomous

public class MarkerThenPit extends LinearOpMode {
    DcMotor leftDrive;
    DcMotor rightDrive;
    DcMotor linearSlide;
    Servo mainServo;
    Servo markerServo;
    
    double position_mainServo = 0.6; // Starts servo at position
    double position_markerServo = 0.0;

    void run(){
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
    // public void runOpMode() {
    //     // Initializes hardware
    //     // hware.sensors();
        
    //     // Defines team based variables in order for the robot to know what team it's on
    //     int redSights = 0;
    //     int blueSights = 0;
    //     boolean redTeam; // If true it's on the red team. If false it's on the blue team
        
    //     // Waits for the press of the "Start" button on the phone
    //     waitForStart();
        
    //     // Game loop
    //     while (opModeIsActive()) {
    //         // Note: Blue am-2947, Red am-2946
            
    //         stop();
    //     }
    // }
}
