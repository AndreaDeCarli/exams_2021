package a03a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Controller controller;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        this.controller = new ControllerImpl(size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var button = (JButton)e.getSource();
            var position = cells.get(button);
            controller.hit(position);
            drawScene();
            if (controller.IsOver()) {
                finish();
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton(" ");
                this.cells.put(jb, pos);
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
            }
        }
    }

    private void finish(){
        for (JButton jb : cells.keySet()) {
            jb.setEnabled(false);
        }
    }
    
}
