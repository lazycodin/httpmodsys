package RandVar;

import java.util.ArrayList;

public class ExpVar extends RandVar{

    public double M;
    public double D;//sko
    public ArrayList<Double> values;
    public double lambda;
    public double min;
    public double max;

  public  ExpVar(double min, double max, double D) {

        super();

        lambda = Math.sqrt(1/D);
        values = new ArrayList<>();
        this.min = min;
        this.max = max;

    }

    @Override
    public double getDist() {

        double val = 0;

        val = (double) Math.log(super.getVal()) * (double) (- 1) / lambda;
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
