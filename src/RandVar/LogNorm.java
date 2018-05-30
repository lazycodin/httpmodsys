package RandVar;

import java.util.ArrayList;

public class LogNorm extends RandVar {

    public double M;
    public double D;//sko
    public ArrayList<Double> values;
    public double min;
    public double max;
    public int counter;

  public  LogNorm(double min, double max, double D) {

        super();

        this.counter = 0;
        values = new ArrayList<>();
        this.min = min;
        this.max = max;

    }

    @Override
    public double getDist() {

        double val = 0;
        double last = super.getVal();
        if (counter % 2 == 0) {
            val = (double) Math.sqrt((-2.00) * Math.log(super.getVal())) * (double) Math.cos(2 * Math.PI * last);
        } else {
            val = (double) Math.sqrt((-2.00) * Math.log(super.getVal())) * (double) Math.sin(2 * Math.PI * last);
        }

        val = Math.exp(val);

        values.add(val);

        if (val > max) {
            return max;
        }

        if (val < min) {
            return min;
        }

        return val;
    }
}
