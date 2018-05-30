package model;

import java.io.File;
import java.util.*;
import static java.util.Collections.list;
import javax.management.ObjectName;
import shedulers.*;

public class Model {

    public Abonent[] abonents;
    public Queue<Message> queue;
    public Queue<Message> tmpQueue;
    public Slot[] slots = new Slot[50000];
    public Scheduler Scheduler;
    public double Rm;
    public double Tm;
    public int served;
    public double D;
    public ArrayList<Double> d = new ArrayList<>();

    public Model(int M, String Sched, int nRB, File fading,
            int vSpeed, int vMes[],
            int vPause[]) {

        abonents = new Abonent[M];

        //init
        for (int i = 0; i < M; i++) {

            abonents[i] = new Abonent(i, slots, vMes, vPause);
        }

        queue = new PriorityQueue<>(comparator);
        tmpQueue = new PriorityQueue<>(comparator);

        double step = 1;
        double start = 0;
        for (int j = 0; j < slots.length; j++) {
            slots[j] = new Slot(nRB, start, step);
            start += step;
        }

        //выбор планировщика
        if ("RR".equals(Sched)) {
            Scheduler = new SchedulerRR("RR", nRB, slots, abonents, queue);
        }
        if ("PF".equals(Sched)) {
            Scheduler = new SchedulerPF("PF", nRB, slots, abonents, queue, 2);
        }
        if ("MT".equals(Sched)) {
            Scheduler = new SchedulerMT("MT", nRB, slots, abonents, queue);
        }

        //System start
        int currSlot = 1;//1 - slot/1 ms
        int currRB = 0;
        int userCounter = 0;
        ArrayList<Integer> activeUsers = new ArrayList<>();
        int simTime = 5000;
        int currAbonent = 0;
         int g = 0;
         Random R = new Random();
        while (currSlot != simTime) {

            for (int i = 0; i < abonents.length; i++) {
               // abonents[i].Rm = (i + 1) * vSpeed / (abonents.length + 1);
                abonents[i].Rm = R.nextInt(34*1024*1024);
            }

            while (currRB < nRB) {
                //генерирование требования каждым абонентом
                for (int i = 0; i < abonents.length; i++) {
                    abonents[i].getMes(currSlot);
                    //System.out.println(abonents[i].T); 
                    if (Math.ceil(abonents[i].T) <= currSlot) {
                        if (checkUser(i, activeUsers) == 0) {
                            activeUsers.add(i);
                        }
                    }
                }

                if (currSlot == simTime - 1) {
                    break;
                }

               // activeUsers = sort(activeUsers);//доделать оптимизировать

//            for (int b = 0; b < activeUsers.size(); b++) {
//                System.out.println(abonents[activeUsers.get(b)].T);
//            }
                /////
                if (activeUsers.size() == 0) {
                    currSlot++;
                    currRB = 0;
                    continue;
                }

                Message Mes = null;

////                    if (currRB == nRB) {
////                        userCounter = k;
////                        break;
////                    }
                Scheduler.planning(currSlot, activeUsers, abonents[activeUsers.get(currAbonent)], currRB);

                // System.out.println("currRB " + currRB + "  " + slots[currSlot].rb[currRB].setted);

                /*   if (currRB == (nRB - 1)) {
                        userCounter = k;
                        break;
                    }*/
                if (!queue.isEmpty()) {//обслуживание
                    Mes = queue.remove();

                    if (Mes != null) {

                        // abonents[activeUsers.get(k)].downloaded += slots[currSlot].rb[Mes.currRB].speed;
                        //abonents[activeUsers.get(k)].downloaded += abonents[activeUsers.get(k)].Rm;
                        abonents[Mes.numAbonent].downloaded += abonents[Mes.numAbonent].Rm;
                        currRB++;
                        if (abonents[Mes.numAbonent].downloaded >= Mes.size) {
                            abonents[Mes.numAbonent].isAck = true;
                            abonents[Mes.numAbonent].isServed = false;
                            abonents[Mes.numAbonent].downloaded = 0;
                            //найти абонента в списке
                            int deleted = 0;
                            for (int i = 0; i < activeUsers.size(); i++) {
                                if (Mes.numAbonent == activeUsers.get(i)) {
                                    deleted = i;
                                    break;
                                }
                            }
                            ///////////////////////////////
                            if (Scheduler.type == "PF"){
                                Scheduler.tc = currSlot;
                            }
                            activeUsers.removeAll(activeUsers.subList(deleted, deleted + 1));
                            served++;
                            double delay = (currSlot + 1) - Mes.arrivalTime;//поправить аррайвал тайм
                            if (delay > 1000){
                               g+=1;
                            }
                            d.add(delay);
                        }
                    }
                } else {
                    currRB++;
                }

                   if(Scheduler.type == "RR"){
                        currAbonent++;
                   }
                    if (currAbonent >= activeUsers.size()) {
                        currAbonent = 0;
                    }
                
            }

            currSlot++;

            currRB = 0;
            // System.out.println("Curr Slot " + currSlot);
        }
        // System.out.println("Абонентов:" + abonents.length);
        // System.out.println("Всего обслужено:" + served);
        double dd = 0;
        double di = 0;
        ArrayList vv = new ArrayList<Double>();
        for (int b = 0; b < activeUsers.size(); b++) {
            dd = simTime - abonents[activeUsers.get(b)].T;
            d.add(dd);
            //System.out.println(dd);
             di += dd;
        }

        double D = 0;
        int coin = 0;
        double addition = 0;
        for (int i = 0; i < d.size(); i++) {
            if (d.get(i) < 0) {
                coin++;
            }
            D += d.get(i);
        }
        // время симулирования - время то что успелось загрузиться 
//        System.out.println(coin);
        this.D = (D) / served;
        this.D = this.D / 1000;
        //System.out.println(this.D);
        
        //System.out.println(this.D);
       // System.out.println(g);
        /* if (Scheduler.type == "MT"){
           this.D = slots.length  * (abonents.length - 1) / abonents.length;
           this.D = this.D / 1000;
       }*/

        //  System.out.println("Cредняя задержка:" + this.D);
    }

    public ArrayList<Integer> sort(ArrayList<Integer> activeUsers) {

        double min = Double.MAX_VALUE;
        int minUser = 0;
        int index = 0;
        int dim = activeUsers.size();
        ArrayList<Integer> tmp = new ArrayList<>();

        if (activeUsers.isEmpty()) {
            return tmp;
        }

        for (int i = 0; i < dim; i++) {

            for (int j = 0; j < activeUsers.size(); j++) {

                if (abonents[activeUsers.get(j)].T < min) {
                    min = abonents[activeUsers.get(j)].T;
                    minUser = activeUsers.get(j);
                    index = j;
                }
            }

            tmp.add(minUser);
            min = Double.MAX_VALUE;

            if (dim == tmp.size()) {
                return tmp;
            }

            if (i != activeUsers.size()) {
                activeUsers.removeAll(activeUsers.subList(index, index + 1));
            }

        }

        return tmp;
    }

    public int checkUser(int numUser, ArrayList<Integer> List) {

        for (int i = 0; i < List.size(); i++) {
            if (List.get(i) == numUser) {
                return 1;
            }
        }
        return 0;
    }

    Comparator<Message> comparator = new Comparator<Message>() {

        @Override
        public int compare(Message o1, Message o2) {
            return (int) (o1.arrivalTime - o2.arrivalTime);
        }
    };

}
