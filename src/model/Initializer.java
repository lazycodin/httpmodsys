package model;

import graphics.DelayGraphic;
import graphics.MapOfR;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Initializer
        extends JFrame {

    public int M = 5;
    public String Sched = "PF";
    public int nRB = 25;
    public int vSpeed = 0;
    public File fading = null;
    public int[] vMes = new int[4];
    public int[] vPause = new int[4];

    public Initializer() {
        super("Modelling programm");

        this.vMes[0] = 0;
        this.vMes[1] = Integer.MAX_VALUE;
        this.vMes[2] = 2;
        this.vMes[3] = 0;

        setDefaultCloseOperation(3);
        setBounds(400, 100, 500, 500);

        JPanel panel = new JPanel();

        panel.setLayout(null);

        JButton button = new JButton("Обзор");
        button.setBounds(100, 0, 80, 30);

        JButton button1 = new JButton("Старт");
        button1.setBounds(350, 0, 80, 30);

        final JLabel label = new JLabel("Открыть конфиг");
        label.setBounds(0, 0, 100, 30);

        JLabel label1 = new JLabel("---------Введите свои параметры----------");
        label1.setBounds(130, 30, 500, 30);

        JLabel label2 = new JLabel("Число абонентов");
        label2.setBounds(0, 50, 170, 30);

        final JTextField abonents = new JTextField();
        abonents.setBounds(135, 50, 30, 30);

        JLabel label3 = new JLabel("Планировщик");
        label3.setBounds(30, 80, 150, 30);

        final JTextField sched = new JTextField();
        sched.setBounds(135, 80, 30, 30);

        JLabel label4 = new JLabel("Число RB");
        label4.setBounds(30, 110, 150, 30);

        final JTextField rb = new JTextField();
        rb.setBounds(135, 110, 30, 30);

        JLabel label5 = new JLabel("Min,Max,Sqo,Распределение");
        label5.setBounds(30, 145, 250, 30);

        final JTextField param1 = new JTextField();
        param1.setBounds(200, 150, 20, 20);

        final JTextField param2 = new JTextField();
        param2.setBounds(230, 150, 20, 20);

        final JTextField param3 = new JTextField();
        param3.setBounds(260, 150, 20, 20);

        final JTextField param4 = new JTextField();
        param4.setBounds(290, 150, 20, 20);

        JLabel label6 = new JLabel("Вектор максимально достижимых скоростей");
        label6.setBounds(30, 180, 270, 30);

        JTextField speed = new JTextField();
        param1.setBounds(300, 185, 20, 20);

        JButton submit = new JButton("Submit");
        submit.setBounds(250, 430, 100, 30);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                fileopen.setCurrentDirectory(new File("C:\\Дипломное проектирование\\DipTest"));
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == 0) {
                    File file = fileopen.getSelectedFile();
                    label.setText(file.getName());
                }
            }
        });
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Date curTime = new Date();
                DateFormat dtfrm = DateFormat.getDateInstance();
                String dateTime = dtfrm.format(curTime);
                String name = "config " + dateTime + ".mcfg";
                File f = new File(name);
                try {
                    PrintWriter printer = new PrintWriter(f);
                    printer.println("# Configuration file for modelling programm");
                    printer.println("Number of abonents " + abonents.getText());
                    Initializer.this.M = Integer.parseInt(abonents.getText());
                    printer.println("Scheduler " + sched.getText());
                    Initializer.this.Sched = sched.getText();
                    printer.println("Number of resource blocks " + rb.getText());
                    Initializer.this.nRB = Integer.parseInt(rb.getText());
                    printer.println("# Parameters of Vector of Messages");

                    printer.println("Min bound of distr" + param1.getText());
                    Initializer.this.vMes[0] = Integer.parseInt(param1.getText());

                    printer.println("Max bound of distr" + param2.getText());
                    Initializer.this.vMes[1] = Integer.parseInt(param2.getText());

                    printer.println("SQO" + param3.getText());
                    Initializer.this.vMes[2] = Integer.parseInt(param3.getText());

                    printer.println("# 0 - Exp Distr 1 - Norm Distr 2 - LogNorm");
                    printer.println("Distibution" + param4.getText());
                    Initializer.this.vMes[3] = Integer.parseInt(param4.getText());

                    printer.flush();
                    Initializer.this.setVisible(false);
                    Model localModel = new Model(Initializer.this.M, Initializer.this.Sched, Initializer.this.nRB, Initializer.this.fading, Initializer.this.vSpeed, Initializer.this.vMes, Initializer.this.vPause);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Initializer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel.add(submit);
        panel.add(speed);
        panel.add(label6);
        panel.add(param4);
        panel.add(param3);
        panel.add(param2);
        panel.add(param1);
        panel.add(label5);
        panel.add(label4);
        panel.add(rb);
        panel.add(sched);
        panel.add(label3);
        panel.add(abonents);
        panel.add(label2);
        panel.add(button1);
        panel.add(label1);
        panel.add(label);
        panel.add(button);
        panel.setVisible(true);
        getContentPane().add(panel);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        //прототип пользовательского интерфейса, //prototype of ui
        //будет улучшаться  в следующих версиях  //will be developing in next version
        //раскоммментить //delete comment
        //  Initializer I = new Initializer();
        //Исполняющая основная функция delay graphic//main func delay graphic
        DelayGraphic D = new DelayGraphic(2,55,true, true, true);
    }
}
