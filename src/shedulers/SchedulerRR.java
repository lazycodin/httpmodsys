package shedulers;

import java.util.ArrayList;
import model.*;
import java.util.Queue;

public class SchedulerRR extends Scheduler {

    public SchedulerRR(String type, int nRB, Slot[] slots, Abonent[] abonents, Queue<Message> queue) {

        super(type, nRB, slots, abonents, queue);

    }

    @Override
    public int planning(int currSlot, ArrayList activeUsers, Abonent A, int currRB) {

        boolean flag = true;
        while (flag) {
            if (A.T <= currSlot) {

                if (slots[currSlot].rb[currRB].setted == -1) {
                    slots[currSlot].rb[currRB].setted = A.num;
                    queue.add(new Message(A.num, A.size, A.T, currRB));
                    currRB++;
                    flag = false;
                } else {
                    return 1;
                }
            } else{
                return -1;
            }
        }

        /* while (currRB != nRB) {
            for (int j = 0; j < activeUsers.size(); j++) {
                slots[currSlot].rb[currRB].setted = activeUsers.get(j);
                currRB++;
                
                if(currRB == nRB)
                    break;
            }
        }*/
        return 0;
    }

}
