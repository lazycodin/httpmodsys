package model;

import RandVar.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class Abonent extends Thread {

    public int num;
    public boolean isAck;
    public double T;
    public RandVar pauser;
    public RandVar sizer;
    public int size;//bytes
    public boolean isServed;
    public Queue<Message> queue;
    public double downloaded;
    public int Rm;
    public ArrayList<Double> q;
    int pos = 0;

    Abonent(int num, Slot[] Slots, int vMes[], int vPause[]) {

        //this.Rm = 1200;
        q = new ArrayList<Double>();
        queue = new PriorityQueue<>();
        this.num = num;
        this.isAck = true;
        this.sizer = new NormVar(vPause[0], vPause[1], vPause[2]);

        if (vMes[3] == 0) {
            pauser = new ExpVar(vMes[0], vMes[1], vMes[2]);
        }
        if (vMes[3] == 1) {
            pauser = new NormVar(vMes[0], vMes[1], vMes[2]);
        }
        if (vMes[3] == 2) {
            pauser = new LogNorm(vMes[0], vMes[1], vMes[2]);
        }

//        for (int i = 0; i < 15000; i++) {
//            T = T + pauser.getDist();
//            q.add(T);
//        }
//        T = 0;
    }

    public void getMes(int currSlot) {
        Random R = new Random();
        if (this.isAck == true) {

            T = T + pauser.getDist();
            pos++;
            this.size = (int) 1024*1024*100;
            this.isAck = false;
            this.isServed = false;

        }

    }

    public double getMesNotAdaptive(int currSlot) {

        T = T + pauser.getDist();
        this.size = (1024 * 1024) * 2000;
        this.isServed = false;
        return T;
    }
}
