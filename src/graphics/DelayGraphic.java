package graphics;

import java.io.File;
import javax.swing.JFrame;
import model.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class DelayGraphic extends JFrame {

    public DelayGraphic(int M1, int M2,boolean RR,boolean  PF, boolean MT) {
        //набор параметров//parameters
        int M = 1;//Число абонентов//num of abonents
        String Sched = "RR";//Планировщик//scheduler
        int nRB = 25;//Число ресурсных блоков//num of freaq-time resources
        int vSpeed = 34/ (8);//Вектор максимально достижимой скорости//vector of maximum reach speed
        File fading = null;//Файл fading // file fading (in research)
        int vMes[] = new int[4];//(min/max/sko/расп) (paramaters of distr of time of sleep)
        vMes[0] = 0;
        vMes[1] = Integer.MAX_VALUE;
        vMes[2] = 1;
        vMes[3] = 0;//0 - Exp // 1 - Norm// 2 - LogNorm
        int vPause[] = new int[4];
        vPause[0] = 1;
        vPause[1] = 1800*1800;
        vPause[2] = 2;
        vPause[3] = 0;//parameters of num of messages
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeriesCollection dataset2 = new XYSeriesCollection();
        if (RR == true) {
           XYSeries series = new XYSeries("D(M)RR");
           XYSeries seriesBuf = new XYSeries("left buffer Not Adaptive");
           XYSeries seriesBuf2 = new XYSeries("Adaptive");
           XYSeries seriesNotAdRR = new XYSeries("D(M)RR Not Adaptive");
          System.out.println("RR");
              for (int i = M1; i < M2; i += 10) {
                Model ModelRR = new  Model(i, "RR", nRB, fading, vSpeed, vMes, vPause);
                Notadaptive ModelRRNot = new  Notadaptive(i, "RR", nRB, fading, vSpeed, vMes, vPause);
                seriesBuf.add(i,ModelRRNot.skipped);
                seriesBuf2.add(i,0);
                 series.add(i, (ModelRR.D));
                System.out.println(ModelRRNot.D);
                seriesNotAdRR.add(i,(ModelRRNot.D));
            }
              
             dataset.addSeries(series);
             dataset.addSeries(seriesNotAdRR);
             
        }
        System.out.println("PF");
        if (PF == true) {
           XYSeries series2 = new XYSeries("D(M)PF");
           XYSeries seriesNotAdPF = new XYSeries("D(M)PF Not Adaptive");
            for (int i = M1; i < M2; i += 10) {
                long begin = 0;
                long end = 0;
                begin = System.currentTimeMillis();
                Model ModelPF = new  Model(i,"PF", nRB, fading, vSpeed, vMes, vPause);
                end = System.currentTimeMillis();
                 Notadaptive ModelPFNot = new  Notadaptive(i, "PF", nRB, fading, vSpeed, vMes, vPause);
                series2.add(i, ModelPF.D);
                System.out.println(Math.abs(ModelPFNot.D));
              seriesNotAdPF.add(i,Math.abs(ModelPFNot.D));
            }
       // dataset.addSeries(series2);
        dataset.addSeries(seriesNotAdPF);
        }
     
         System.out.println("MT");
         if (MT == true) {
            XYSeries seriesNotAdMT = new XYSeries("D(M)MT Not Adaptive");
            XYSeries series3 = new XYSeries("D(M)MT");
            for (int i = M1; i < M2; i += 10) {
                Model ModelMT = new  Model(i, "MT", nRB, fading, vSpeed, vMes, vPause);
                Notadaptive ModelMTNot = new  Notadaptive(i, "PF", nRB, fading, vSpeed, vMes, vPause);
                series3.add(i, ModelMT.D);
                seriesNotAdMT.add(i,Math.abs(ModelMTNot.D));
                System.out.println(Math.abs(ModelMTNot.D));
            }
         dataset.addSeries(series3);
         dataset.addSeries(seriesNotAdMT);
        }
        
        
      
        JFreeChart chart1 = ChartFactory.createXYLineChart("Delay Graphic", "Num of Abonents, M", "Delay, T",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        JFrame frame = new JFrame("Graphic");

        frame.getContentPane().add(new ChartPanel(chart1));
        frame.setSize(600, 600);
        frame.show();
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }

    public static double getTher(double lambda, double mu, int M) {

        double T = 0;

        double p0 = 0;

        double sum = 0;
        for (int k = 0; k <= M; k++) {
            p0 = fact(M) / fact(M - k);
            double m = Math.pow((lambda / mu), k);
            p0 = p0 * m;
            sum += p0;
        }

        sum = (1 / sum);

        T = ((M / mu) / (1 - sum)) - (1 / lambda);

        return T;
    }

    public static double fact(int x) {
        if (x < 0) {
            throw new IllegalArgumentException("x должен быть >=0");
        }
        double fact = 1;
        for (int i = 2; i <= x; i++) {
            fact *= i;
        }
        return fact;
    }
}
