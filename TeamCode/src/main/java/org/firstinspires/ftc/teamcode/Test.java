package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@TeleOp(name="Test")

public class Test extends LinearOpMode {

    public void runOpMode() {
        ElapsedTime runtime = new ElapsedTime();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Time: ", "Elapsed Time: " + runtime);
            telemetry.update();
        }
    }
}