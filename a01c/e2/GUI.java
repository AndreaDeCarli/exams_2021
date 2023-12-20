package a01c.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Controller controller;
    private boolean first;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        this.controller = new ControllerImpl();
        this.first = true;
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
            var position = cells.get(button);
            if(first){  
                controller.setPosition(position);
                drawScene();
                first = false;
            }
            else{
                if (controller.hit(position)){
                    drawScene();
                }
            }

            
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer,Integer>(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void drawScene(){
        for (JButton jb : cells.keySet()) {
            if (controller.contains(cells.get(jb))){
                jb.setText("*");
                jb.setEnabled(false);
            }
        }
    }
    
}
