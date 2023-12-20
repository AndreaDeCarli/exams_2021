package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<Pair<Integer, Integer>,JButton> cells = new HashMap<>();
    private ControllerImpl controller;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        this.controller = new ControllerImpl(size, generateLocation(size));
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            controller.hit();
            drawScene();
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton(" ");
                this.cells.put(pos ,jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        drawScene();
        this.setVisible(true);
    }

    private void drawScene(){
        for (Pair<Integer, Integer> coord : cells.keySet()) {
            cells.get(coord).setText(" ");
            if (controller.isInLeft(coord)){
                cells.get(coord).setText("L");
            }
            else if(controller.isInRight(coord)){
                cells.get(coord).setText("R");
            }
            if(controller.getCurrentLocation().equals(coord)){
                cells.get(coord).setText("*");
            }
        }
    }

    private Pair<Integer, Integer> generateLocation(final int size){
        return new Pair<Integer,Integer>(new Random().nextInt(size-1), size-1);
    }
    
}
