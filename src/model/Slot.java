package model;

import java.util.Random;
//slot of channel
public class Slot {

    public int nRB;
    public ResourceBlock[] rb;
    public double start;
    public double end;
  

    public Slot(int nRB, double start, double step) {

        this.nRB = nRB;
        this.start = start;
        this.end = this.start + step;
        
        rb = new ResourceBlock[nRB];
        
        Random R = new Random();
        
        for (int i = 0; i < nRB; i++) {
            rb[i] = new ResourceBlock((R.nextInt(300) + 1700) % 2000);
        }
    }

    public class ResourceBlock {

        public int speed;//bytes
        public int setted = -1;

        ResourceBlock(int speed) {

            this.speed = speed;

        }

    }

}
