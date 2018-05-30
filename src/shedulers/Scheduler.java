package shedulers;

import java.util.ArrayList;
import java.util.Queue;
import model.Slot;
import model.Abonent;
import model.Message;
//mother class of schedulers
public abstract class Scheduler {

    public String type;
    public int nRB;
    public Slot[] slots;
    public Abonent[] abonents;
    public Queue<Message> queue;
    public double tc;
    

    public Scheduler(String type, int nRB, Slot[] slots, Abonent[] abonents, Queue<Message> queue) {

        this.type = type;
        this.slots = slots;
        this.abonents = abonents;
        this.queue = queue;
        this.nRB = nRB;
    }

    public int planning(int currSlot,ArrayList activeUsers, Abonent A, int currRB) {

        //планирование абонента
        return 0;
    }

    public void setRes(Abonent A, int numRB, int currTime) {

        slots[currTime].rb[numRB].setted = A.num;

    }

}
