package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.plaf.ScrollPaneUI;
import model.Slot;
//class for map of allocated resources
public class MapOfR extends JFrame {

    public Slot[] slots;
    public FieldButton[][] b;

    public MapOfR(Slot[] slots,int time,int nRB,String name,int M) {

       
        this.slots = slots;

        b = new FieldButton[nRB][time];
        
        this.slots = slots;

        GridLayout g = new GridLayout(nRB, time);//столбцы// строки

        JPanel panels = new JPanel(g);
        panels.setLocation(0, 0);
        panels.setSize(1100, 700);
        // panels.setBackground(new Color(0, 200, 200));
         
         
        JFrame mainf = new JFrame("Map of resources  " + name + M);
        mainf.setSize(1100, 700);
        
        JScrollPane scrl = new JScrollPane(panels);
        scrl.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
       // scrl.setViewportView(panels);
       // scrl.getViewport().add(panels);
       // scrl.setLayout(new ScrollPaneLayout());
        //scrl.setSize(500, 500);

        for (int i = 0; i < nRB; i++) {//столбцы 

            for (int j = 1; j < time; j++) {//строки

                panels.add(new FieldButton(i, j, this.slots[j].rb[i].setted));

            }
        }
        
        mainf.add(scrl);
        mainf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainf.setVisible(true);

    }

    public class FieldButton extends JButton {

        public int timeSlot;
        public int freqSlot;

        FieldButton(int timeSlot, int freqSlot, int num) {
            super();

           
            if(num == 1){
            this.setBackground(Color.DARK_GRAY);
             JLabel lbl = new JLabel("" + (num + 1));
              this.add(lbl);
            }
             if(num == 0){
            this.setBackground(Color.GRAY);
             JLabel lbl = new JLabel("" + (num + 1));
              this.add(lbl);
            }
            if(num == -1){
             this.setBackground(Color.black);
             return;
            }
             JLabel lbl1 = new JLabel("" + (num + 1));
           
            this.add(lbl1);
            this.timeSlot = timeSlot;
            this.freqSlot = freqSlot;
          

            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                }
            });
        }

    }

}
