package shedulers;

import java.util.ArrayList;
import model.*;
import java.util.Queue;

public class SchedulerMT extends Scheduler {

    public SchedulerMT(String type, int nRB, Slot[] slots, Abonent[] abonents, Queue<Message> queue) {

        super(type, nRB, slots, abonents, queue);

    }

    @Override
    public int planning(int currSlot,ArrayList activeUsers, Abonent A, int currRB) {
            
        int best = getBest(activeUsers);
        
        boolean flag = true;
        while (flag) {
          
                if (slots[currSlot].rb[currRB].setted == -1) {
                    slots[currSlot].rb[currRB].setted = best;
                    queue.add(new Message(abonents[best].num, abonents[best].size, abonents[best].T, currRB));
                    currRB++;
                    flag = false;
                } else {
                    return 1;
                }
        }

        return 0;
    }
    
    public int getBest(ArrayList<Integer> activeUsers){
        
        int max = 0;
        int maxVal = 0;
        for(int i = 0; i < activeUsers.size(); i++){
            if (maxVal <  abonents[activeUsers.get(i)].Rm)
            {
            maxVal = abonents[activeUsers.get(i)].Rm;
            max = activeUsers.get(i);
            }
        }
        return max;
    }
   
}
