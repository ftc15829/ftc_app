package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;

//@Disabled
@TeleOp(name = "Test")

public class Test extends LinearOpMode
{
	public Telemetry.Item Status;
	public Telemetry.Item SubStatus;
	public Telemetry.Item Case = telemetry.addData("Case", "");
	/*
telemetry.setAutoClear(false);
       Telemetry.Item countItem = telemetry.addData("count", 0);
       Telemetry.Item elapsedItem = telemetry.addData("elapsedTime", 0);
  
       void onePartOfYourCode() {
           ...
           countItem.setValue(0);
           telemetry.update();
           ...
       }

       void anotherPartOfYourCode() {
           ...
           elapsedItem.setValue("%.3f", elapsedSeconds);
           telemetry.update();
           ...
       }
 */
	public void runOpMode()
	{
		telemetry.setAutoClear(false);
		
		
		Status = telemetry.addData("Status", "Normal");
		SubStatus = telemetry.addData("Sub-Status", "");
		telemetry.update();
		
		sleep(2000);
		
		Status.setValue("Initialized");
		telemetry.update();
		
		waitForStart();
		
		while (opModeIsActive())
		{
			Case.setValue("Left");
			telemetry.update();
			
			if (isStopRequested())
			{
				Status.setValue("Stopping");
				telemetry.update();
			}
		}
	}
}