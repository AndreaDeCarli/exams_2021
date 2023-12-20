package a03a.e2;

import java.util.HashSet;
import java.util.Set;

public class ControllerImpl implements Controller {
    
    private int size;
    private Pair<Integer, Integer> firstVert;
    private Pair<Integer, Integer> secondVert;
    private Set<Pair<Integer, Integer>> verts;

    public ControllerImpl(final int size){
        this.size = size;
        this.firstVert = new Pair<Integer,Integer>(0, 0);
        this.secondVert = new Pair<Integer, Integer>(size, size);
        this.verts = new HashSet<>();
    }

    @Override
    public void hit(final Pair<Integer, Integer> coord){
        if(checkLimits(coord)){
            firstVert = coord;
            Pair<Integer, Integer> temp1 = new Pair<Integer,Integer>(correspondent(coord.getX()), correspondent(coord.getY()));
            secondVert = temp1;
            verts.add(temp1);
            draw();
        }
    }

    private boolean checkLimits(Pair<Integer, Integer> coord) {
        return coord.getX()> firstVert.getX() && coord.getX()< secondVert.getX() && coord.getY()>firstVert.getX() && coord.getY()<secondVert.getY();
    }

    private int correspondent(final int initial){
        int result = 0;
        result = (size/2) + ((size/2) - initial)-1;
        return result;
    }

    @Override
    public boolean contains(Pair<Integer, Integer> pair) {
        return verts.contains(pair);
    }

    private void draw(){
        for (int i = firstVert.getX(); i < secondVert.getX(); i++) {
            verts.add(new Pair<Integer,Integer>(i, firstVert.getY()));
            verts.add(new Pair<Integer,Integer>(i, secondVert.getY()));
        }
        for (int i = firstVert.getY(); i < secondVert.getY(); i++) {
            verts.add(new Pair<Integer,Integer>(firstVert.getX(), i));
            verts.add(new Pair<Integer,Integer>(secondVert.getX(), i));
        }
    }

    @Override
    public boolean IsOver(){
        return firstVert.getX()+1 == secondVert.getX() && firstVert.getY()+1 == secondVert.getY();
    }
}
