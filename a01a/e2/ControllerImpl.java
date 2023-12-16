package a01a.e2;

import java.util.HashSet;
import java.util.Set;

public class ControllerImpl implements Controller {

    private int size;
    private Set<Pair<Integer, Integer>> disabled;
    private Pair<Integer, Integer> firstVert;
    private Pair<Integer, Integer> secondVert;

    @Override
    public boolean contains(final Pair<Integer, Integer> coord){
        if (disabled.contains(coord)){
            return true;
        }
        return false;
    }


    public ControllerImpl(final int size) {
        this.size = size;
        this.disabled = new HashSet<>();
        this.firstVert = new Pair<Integer,Integer>(0, 0);
        this.secondVert = new Pair<Integer,Integer>(0, 0);
    }

    @Override
    public void hitFirst(final Pair<Integer, Integer> coord){
        firstVert = coord;
    }

    @Override
    public void hitSecond(final Pair<Integer, Integer> coord){
        secondVert = coord;
        int maxX = Integer.max(firstVert.getX(), secondVert.getX());
        int minX = Integer.min(firstVert.getX(), secondVert.getX());
        int maxY = Integer.max(firstVert.getY(), secondVert.getY());
        int minY = Integer.min(firstVert.getY(), secondVert.getY());
        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                disabled.add(new Pair<Integer,Integer>(j, i));
            }
        }
    }
    @Override
    public boolean isOver(){
        if (disabled.size() == size*size){
            return true;
        }
        return false;
    }
}