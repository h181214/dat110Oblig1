package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCServerStopStub;

public class Controller {

    private static int N = 5;

    public static void main(String[] args) {

        Display display;
        Sensor sensor;

        RPCClient displayclient, sensorclient;

        System.out.println("Controller starting ...");

        RPCServerStopStub stopdisplay = new RPCServerStopStub();
        RPCServerStopStub stopsensor = new RPCServerStopStub();

        displayclient = new RPCClient(Common.DISPLAYHOST, Common.DISPLAYPORT);
        sensorclient = new RPCClient(Common.SENSORHOST, Common.SENSORPORT);

        // TODO
        // create display and sensor object
        // create RPC clients for display device and sensor device
        // register RPC methods in the RPC layer

        // Create objects and connect
        display = new Display();
        sensor = new Sensor();
        displayclient.connect();
        sensorclient.connect();

        //Register objects
        displayclient.register(display);
        sensorclient.register(sensor);
        displayclient.register(stopdisplay);
        sensorclient.register(stopsensor);

        // TODO:
        // loop while reading from sensor and write to display via RPC
        int i = 0;
        while (i < N) {
            //Read and display sensordata
            int temp = sensor.read();
            display.write(String.valueOf(temp));

            //wait a bit and increase counter
            sleep(500);
            i++;
        }

        //Stop and disconnect everything
        stopdisplay.stop();
        stopsensor.stop();
        displayclient.disconnect();
        sensorclient.disconnect();

        System.out.println("Controller stopping ...");

    }

    //Simple sleep method to keep code cleaner
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
