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

package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

//Implements a LinearOpMode that gets its gamepad inputs from the wifi-direct packets

@TeleOp(name="RemoteDriveTest", group="Linear Opmode")
@Disabled
public class RemoteDrive extends LinearOpMode {

    //Wifi-direct socket
    private DatagramSocket socket;
    private boolean canRunGamepadThread;
    
    //the thread which awaits WIFI-DIRECT packets and parses them and updates the gamepad's fields
    private Thread gamepadHandler;

    //The two custom gamepads, one for each driver
    CustomGamepad gamepad1,gamepad2;


    private void startGamepadHandlerThread() {
        // Creating the gamepadHandlerThread
        gamepadHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                while (canRunGamepadThread) {
                    String gamepadAction = "";
                    // Awaiting WIFI-DIRECT packet
                    Log.d("THREAD","ok, awaiting message");
                    try {
                        byte[] buffer = new byte[1024];
                        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                        socket.receive(response);
                        gamepadAction = new String(buffer,0,response.getLength(), StandardCharsets.UTF_8);
                        Log.d("THREAD","received " + gamepadAction);
                        telemetry.addData("received ",gamepadAction);
                        telemetry.update();
                    } catch (Exception e) {
                        Log.d("EXCEPTION",e.getMessage());
                    }

                    if(gamepadAction.isEmpty() == false){
                        int gamepad_id = -1;
                        CustomGamepad curr = new CustomGamepad();
                        String[] parts = gamepadAction.split(",");
                        Log.d("RECEIVED ", gamepadAction);
                        //Parsing the received action
                        for(int i = 0;i < parts.length;i++){
                            String[] command = parts[i].split("~");
                            Log.d("COMMAND_HEAD: ",command[0]);
                            Log.d("COMMAND_STUFF: ",command[1]);
                            if(command[0].equals("G")){
                                gamepad_id = Integer.parseInt(command[1]);
                            }else if(command[0].equals("A")){
                                curr.a = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("B")){
                                curr.b = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("X")){
                                curr.x = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("Y")){
                                curr.y = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("D_UP")){
                                curr.dpad_up = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("D_DN")){
                                curr.dpad_down = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("D_LT")){
                                curr.dpad_left = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("D_RT")){
                                curr.dpad_right = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("L_BMP")){
                                curr.left_bumper = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("R_BMP")){
                                curr.right_bumper = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("L_PRS")){
                                curr.left_stick = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("R_PRS")){
                                curr.right_stick = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("L_TRG")){
                                curr.left_trigger = Double.parseDouble(command[1]);
                            }else if(command[0].equals("R_TRG")){
                                curr.right_trigger = Double.parseDouble(command[1]);
                            }else if(command[0].equals("L_X")){
                                curr.left_stick_x = Double.parseDouble(command[1]);
                            }else if(command[0].equals("L_Y")){
                                curr.left_stick_y = Double.parseDouble(command[1]);
                            }else if(command[0].equals("R_X")){
                                curr.right_stick_x = Double.parseDouble(command[1]);
                            }else if(command[0].equals("R_Y")){
                                curr.right_stick_y = Double.parseDouble(command[1]);
                            }else if(command[0].equals("BACK")){
                                curr.back = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("START")){
                                curr.start = (Integer.parseInt(command[1]) == 1);
                            }else if(command[0].equals("GUIDE")){
                                curr.guide = (Integer.parseInt(command[1]) == 1);
                            }
                        }
                        //updating the required gamepad
                        if(gamepad_id == 1){
                            gamepad1 = curr;
                        }else if(gamepad_id == 2){
                            gamepad2 = curr;
                        }
                    }
                }
                gamepadHandler.interrupt();
            }
        });

        gamepadHandler.setName("Gamepad Handler Thread");
        gamepadHandler.setPriority(Thread.NORM_PRIORITY);
        gamepadHandler.start();
    }

    // Initializes the WIFI-DIRECT socket
    public void _init(){
        //Initializing gamepads
        this.gamepad1 = new CustomGamepad();
        this.gamepad2 = new CustomGamepad();
        String address = "192.168.49.1"; //Check "Program and Manage" tab on the Driver Station and verify the IP address
        int port = 6969; //Change as needed
        canRunGamepadThread = false;

        //Trying to start listening to the specified port
        try {
            this.socket = new DatagramSocket(port);
        } catch (Exception ex) {
            Log.d("EXCEPTION",ex.getMessage());
        }

        Log.d("INIT","Initialized");
        Log.d("INIT","Connect your server to " + address + ":" + port);
        telemetry.update();
    }

    // Starts the gamepadHandleThread
    public void after_start(){
        Log.d("START","this just started");
        canRunGamepadThread = true;
        startGamepadHandlerThread();
    }

    // Cleanly closes the gamepadHandleThread and the socket
    public void _end(){
        Log.d("END","teleop ended");
        canRunGamepadThread = false;
        socket.close();
    }

    // runOpMode implementation example
    @Override
    public void runOpMode(){
        this._init();
        waitForStart();
        this.after_start();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                ;
            }
        }
        this._end();
    }


}
