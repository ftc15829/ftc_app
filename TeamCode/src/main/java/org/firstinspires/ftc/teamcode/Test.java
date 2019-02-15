package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

//@Disabled
@TeleOp(name = "Test")

public class Test extends LinearOpMode
{
	
	public void runOpMode()
	{
		telemetry.addData("Status", "Initialized");
		telemetry.update();
		
		waitForStart();
		
		while (opModeIsActive())
		{
			telemetry.addData("Status", "Running");
			telemetry.update();
			
			if (isStopRequested())
			{
				telemetry.addData("Status", "Stopping");
				telemetry.update();
			}
		}
	}
}