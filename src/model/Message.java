package model;

public class Message {

    public double size;//bytes
    public double arrivalTime;
    public double downloaded;
    public int currRB;
    public int numAbonent;

    public Message(int numAbonent,double size, double arrivalTime, int currRB) {
        
        this.numAbonent = numAbonent;
        this.size = size;
        this.arrivalTime = arrivalTime;
        this.downloaded = 0;
        this.currRB = currRB;

    }
}
