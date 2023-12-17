package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private ControllerImpl controller;
    private int turn;
    
    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50*size, 50*size);
        this.controller = new ControllerImpl();
        this.turn = 1;
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
        	var button = (JButton)e.getSource();
            var position = cells.get(button);
            switch (turn) {
                case 1:
                    if (controller.firstHit(position)) {
                        button.setText("1");
                        button.setEnabled(false); 
                        turn++;
                    }
                    break;
                case 2:
                    if (controller.secondHit(position)){
                        button.setText("2");
                        button.setEnabled(false); 
                        turn++;
                    }
                    break;
                case 3:
                    if (controller.thirdHit(position)){
                        button.setText("3");
                        button.setEnabled(false);
                        drawScene();
                    }
                    break;
                default:
                    break;
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
            if (controller.contains(cells.get(jb)) && jb.isEnabled()){
                jb.setText("*");
            }
            jb.setEnabled(false);
        }
        
    }
    
}
