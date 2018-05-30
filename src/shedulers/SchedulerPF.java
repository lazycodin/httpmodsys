package shedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import model.*;

public class SchedulerPF extends Scheduler {

    public HashMap<Integer, Double> metrics;
    public double tc;
    public boolean first = true;
    public double Tm;
    public int lastSize;

    public SchedulerPF(String type, int nRB, Slot[] slots, Abonent[] abonents, Queue<Message> queue, double tc) {

        super(type, nRB, slots, abonents, queue);

        metrics = new HashMap<>();
        this.tc = tc;

    }

    public int getMetrics(ArrayList<Integer> activeUsers, int currRB, int currSlot) {

        int max = 0;
        double m;
        if (currRB == 0)//убрать
        {
            for (int i = 0; i < activeUsers.size(); i++) {

                if (first) {
                    this.Tm = 1;
                    metrics.put(activeUsers.get(i), this.Tm);
                     Tm = Tm + slots[currSlot].rb[currRB].speed;
                    //Tm = ((1 - (1 / tc)) * Tm) + ((1 / tc) * abonents[activeUsers.get(i)].Rm);
                    this.first = false;
                }

                m = abonents[activeUsers.get(i)].Rm / Tm;

                metrics.put(activeUsers.get(i), m);
                lastSize = activeUsers.size();
            }
        }

        if (lastSize != activeUsers.size()) {//убрать

            for (int h = 0; h < activeUsers.size(); h++) {
                if (metrics.containsKey(activeUsers.get(h))) {
                    continue;
                } else {
                    m = abonents[activeUsers.get(lastSize)].Rm / Tm;
                    metrics.put(activeUsers.get(lastSize), m);
                    lastSize++;
                }
            }

        }//убрать

        double argmax = 0;
        for (int i = 0; i < activeUsers.size(); i++) {

            double val = metrics.get(activeUsers.get(i));
            if (val > argmax) {
                argmax = val;
                max = activeUsers.get(i);
            }
        }

        return max;
    }

    @Override
    public int planning(int currSlot, ArrayList activeUsers, Abonent A, int currRB) {

        int userMax = getMetrics(activeUsers, currRB, currSlot);
        boolean flag = true;
        while (flag) {

            if (slots[currSlot].rb[currRB].setted == -1) {
                slots[currSlot].rb[currRB].setted = userMax;
                queue.add(new Message(abonents[userMax].num, abonents[userMax].size, abonents[userMax].T, currRB));
              //  Tm = (1 - 1 / tc) * Tm + (1 / tc) * abonents[userMax].Rm;
                 Tm = Tm + abonents[userMax].Rm;

                metrics.put(userMax, abonents[userMax].Rm / Tm);//убрать
                currRB++;
                flag = false;
            } else {
                return 1;
            }

        }

        return 0;
    }

}
