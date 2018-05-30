package RandVar;

import java.util.ArrayList;
import java.util.Random;

public abstract class RandVar {

    private double value;
    private Random R;

    public RandVar() {

        this.R = new Random();
        this.value = 0;

    }

    public double getDist() {

        return 0;
    }

    public double getVal() {

        this.value = R.nextDouble() % 1;
        return this.value;

    }

    public double M(ArrayList<Double> values) {

        double m = 0;
        for (int i = 0; i < values.size(); i++) {
            m += values.get(i);
        }
        return (double) m / values.size();
    }

    public double D(ArrayList<Double> values, double M) {

        double D = 0;
        for (int i = 0; i < values.size(); i++) {
            D += (double) Math.pow((values.get(i) - M), 2);
        }
        return D / values.size();
    }
}
