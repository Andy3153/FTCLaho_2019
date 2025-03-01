/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.opmodes.year2020.testing.Autonomy;

import android.app.ApplicationErrorReport;
import android.drm.DrmStore;
import android.hardware.Sensor;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Functions.robotMovement;

import java.nio.channels.FileLock;

import static org.firstinspires.ftc.teamcode.Functions.Constants.clawMinPos;
import static org.firstinspires.ftc.teamcode.Functions.Constants.plateMaxPos;
import static org.firstinspires.ftc.teamcode.Functions.Constants.plateMinPos;
import static org.firstinspires.ftc.teamcode.Functions.Constants.rotateMaxPos;
import static org.firstinspires.ftc.teamcode.Functions.robotGrabbyThings.slashKill;
import static org.firstinspires.ftc.teamcode.Functions.robotMovement.move;
import static org.firstinspires.ftc.teamcode.Functions.robotMovement.strafe;
import static org.firstinspires.ftc.teamcode.Functions.robotMovement.turn;

@Autonomous(name="RightPlate", group ="Autonomy")
public class BroBots_GoRight_Wall extends LinearOpMode {

    public float platePos=plateMinPos;

    public void stopRobot(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR)
    {
        move(FL, FR, BL, BR, 0, 0);
    }

    public void move(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR, float speed, int miliseconds) {
        robotMovement.move(FL, FR, BL, BR, speed);
        sleep(miliseconds);
    }

    public void strafe(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR, float speed, int miliseconds/*int ticks*/) {
        robotMovement.strafe(FL, FR, BL, BR, speed);
        sleep(miliseconds);
    }

    public void turn(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR, float speed, int miliseconds) {
        robotMovement.turn(FL, FR, BL, BR, speed);
        sleep(miliseconds);
    }

    private void slashKill(Servo left, Servo right, boolean isClosing)
    {
        if(isClosing)
            platePos = plateMaxPos;
        else
            platePos = plateMinPos;

        left.setPosition(1-platePos);
        right.setPosition(platePos);

        sleep(1250);
    }

    @Override
    public void runOpMode() {
        float speed = -1;

        DcMotor H1Motor0_FL = hardwareMap.get(DcMotor.class, "H1Motor0_FL");
        DcMotor H1Motor1_FR = hardwareMap.get(DcMotor.class, "H1Motor1_FR");
        DcMotor H1Motor2_BL = hardwareMap.get(DcMotor.class, "H1Motor2_BL");
        DcMotor H1Motor3_BR = hardwareMap.get(DcMotor.class, "H1Motor3_BR");

        DcMotor H2Motor0_Arm = hardwareMap.get(DcMotor.class, "H2Motor0_Arm");
        Servo H2Servo0_PlateLeft = hardwareMap.get(Servo.class, "H2Servo0_PlateLeft");
        Servo H2Servo1_PlateRight = hardwareMap.get(Servo.class, "H2Servo1_PlateRight");
        Servo H2Servo2_RotateClaw = hardwareMap.get(Servo.class, "H2Servo2_RotateClaw");
        Servo H2Servo3_Claw = hardwareMap.get(Servo.class, "H2Servo3_Claw");

        ModernRoboticsI2cRangeSensor ultraSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "ultraSensor");

        H2Servo0_PlateLeft.setPosition(1-platePos);
        H2Servo1_PlateRight.setPosition(platePos);
        H2Servo2_RotateClaw.setPosition(rotateMaxPos);
        H2Servo3_Claw.setPosition(clawMinPos);
        H2Motor0_Arm.setPower(0);

        waitForStart();

        if(opModeIsActive()) {
            //Do stuff
/*
            //Move to plate
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 20);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 275();
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            speed /= 3;
            robotMovement.move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed);

            while (opModeIsActive() && ultraSensor.getDistance(DistanceUnit.CM) > 6.8)
            {
                telemetry.addData("Distance: ", ultraSensor.getDistance(DistanceUnit.CM));
                telemetry.update();
            }

            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            speed*=3/1.2;

            //Catch plate!
            slashKill(H2Servo0_PlateLeft, H2Servo1_PlateRight, true);
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 830);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            slashKill(H2Servo0_PlateLeft, H2Servo1_PlateRight, false);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 1700);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 1000);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 750);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 900);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);

            //Park
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 50);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 800);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 400);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 750);

            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
*/

            //Move to plate
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 30);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 300);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            speed /= 3;
            robotMovement.move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed);

            while (opModeIsActive() && ultraSensor.getDistance(DistanceUnit.CM) > 6.8)
            {
                telemetry.addData("Distance: ", ultraSensor.getDistance(DistanceUnit.CM));
                telemetry.update();
            }

            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            speed*=3/1.7;

            //Catch plate!
            slashKill(H2Servo0_PlateLeft, H2Servo1_PlateRight, true);
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 1100);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            slashKill(H2Servo0_PlateLeft, H2Servo1_PlateRight, false);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 1200);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR,  -speed, 1200);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, -speed, 1300);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 1200);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);

            //Park
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 50);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 1150);
            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
            move(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 300);
            strafe(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR, speed, 700);

            stopRobot(H1Motor0_FL, H1Motor1_FR, H1Motor2_BL, H1Motor3_BR);
        }
    }
}

