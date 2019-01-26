package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Hardware;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Main_Gamepad", group="Movement")

public class Main_Gamepad extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor linearSlide;
    public DcMotor mainShoulder;
    public Servo mainServo;
    public Servo markerServo;
    public Servo hand;
    public CRServo armExtend;
    // Configures the main servo
    static final double INCREMENT = 0.01; // Amount to move servo each cycle (CYCLE_MS)
    static final int CYCLE_MS =  50; // Time to complete one cycle
    static final double MAX_POS	= 1.0;	// Sets the maximum rotational position (percentage of 180 degrees)
    static final double MIN_POS	= 0.0; // Sets the minimum rotational position
    double position_mainServo = 0.0; // Starts servo at position
    double position_markerServo = 0.0;
    double position_hand = 0.95;
    // Sets the power of the various motors
    private double drivePower(int motorID) {
        double powerLvl = 0.0; // Initializes power level with a default value of 0
        double mod = 1;
        double CORRECTION = 1.0;
        if (this.gamepad1.left_bumper == true)
            mod = 0.5;
        if (this.gamepad1.right_bumper == true)
            mod = 0.75;

        // Takes the motor ID and sets the power
        switch (motorID) {
            case 0: // leftDrive
                // Simply forward the raw y value
                powerLvl = (this.gamepad1.left_stick_y * mod) * CORRECTION;
                break;
            case 1: // rightDrive
                powerLvl = (this.gamepad1.right_stick_y * mod);
                break;
            case 2: // linearSlide
                powerLvl = this.gamepad2.left_stick_y * -0.3;
                break;
            case 3:
                powerLvl = this.gamepad2.right_stick_y * 0.3;
                break;
        }
        return powerLvl;
    }

    // Controls servos
    public void servoControl(int servoID) {
        switch (servoID) {
            case 0: // mainServo
                if (mainServo.getPosition() >= 0.0 && this.gamepad2.a == true) {
                    position_mainServo -= INCREMENT;
                    sleep(5);
                }
                else if (mainServo.getPosition() <= 1.0 && this.gamepad2.b == true) {
                    position_mainServo += INCREMENT;
                    sleep(5);
                }
                mainServo.setPosition(position_mainServo);
                break;
            case 1: // markerServo
                if (this.gamepad2.x == true) {
                    position_markerServo = 1;
                    sleep(5);}
                else if (this.gamepad2.y == true){
                    position_markerServo=0;
                    sleep(5);
                }
                markerServo.setPosition(position_markerServo);
                if (position_mainServo>1)
                    position_mainServo=1;
                if (position_mainServo<0)
                    position_mainServo=0;
                break;

            case 2:
            {
                if (hand.getPosition() >= 0.6 && this.gamepad2.left_trigger !=0) {
                    position_hand -= INCREMENT;
                    sleep(5);
                }
                else if (hand.getPosition() <= 0.95 && this.gamepad2.right_trigger !=0) {
                    position_hand += INCREMENT;
                    sleep(5);
                }
                hand.setPosition(position_hand);
            }
        }
    }
    public double conServo(int id){
        double power=0;
        switch (id)
        {
            case 0: // arm extend
            {
                if (this.gamepad2.right_bumper==true)
                    power=1.0;
                else if (this.gamepad2.left_bumper==true)
                    power=-1.0;
                else
                    power=0.0;
                break;
            }
        }
        return power;
    }
    // Automatically runs
    @Override
    public void runOpMode() {

        // Updates telemetry (log) to show it is running
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initializes hardware
        leftDrive = hardwareMap.dcMotor.get("leftDrive");
        rightDrive = hardwareMap.dcMotor.get("rightDrive");
        linearSlide = hardwareMap.dcMotor.get("linearSlide");
        mainShoulder = hardwareMap.dcMotor.get("mainShoulder");

        mainServo = hardwareMap.servo.get("mainServo");
        markerServo = hardwareMap.servo.get("markerServo");
        hand=hardwareMap.servo.get("hand");
        armExtend = hardwareMap.crservo.get("armExtend");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        mainServo.setPosition(position_mainServo);
        markerServo.setPosition(position_markerServo);
        // Waits for the user to press "Start"
        waitForStart();
        runtime.reset();


        // Game loop
        while (opModeIsActive()) {

            // Obtains value from power() function and sets the motors power to that
            leftDrive.setPower(drivePower(0));
            rightDrive.setPower(drivePower(1));
            linearSlide.setPower(drivePower(2));
            mainShoulder.setPower(drivePower(3));

            //	Controls the servo
            servoControl(0);
            servoControl(1);
            servoControl(2);

            armExtend.setPower(conServo(0));

            // Updates telemetry to show the run time and power levels of the motors
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", drivePower(0), drivePower(1));
            telemetry.addData("Linear Slide", "pwr (%.2f)", drivePower(2));
            telemetry.update();
        }
    }
}
