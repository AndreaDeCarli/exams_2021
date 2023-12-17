package a01b.e2;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class ControllerImpl {

    private Pair<Integer, Integer> firstCoord;
    private Pair<Integer, Integer> secondCoord;
    private Pair<Integer, Integer> thirdCoord;
    private Predicate<Pair<Integer,Integer>> condition;
    private Set<Pair<Integer, Integer>> enabled;

    public ControllerImpl(){
        this.condition = i -> true;
        this.enabled = new HashSet<>();
    }

    public boolean contains(final Pair<Integer, Integer> coord){
        return enabled.contains(coord);
    }

    public boolean firstHit(final Pair<Integer,Integer> coord){
        firstCoord = coord;
        return true;
    }
    
    public boolean secondHit(final Pair<Integer,Integer> coord){
        if (control(firstCoord, coord)){
            secondCoord = coord;
            inBetween(firstCoord, secondCoord);
            return true;
        }
        return false;  
    }

    public boolean thirdHit(final Pair<Integer, Integer> coord){
        if (condition.test(coord)){
            thirdCoord = coord;
            inBetween(firstCoord, thirdCoord);
            return true;
        }
        return false;
    }

    private boolean control(Pair<Integer, Integer> current, Pair<Integer, Integer> toCheck){
        if (current.getX() == toCheck.getX()){
            this.condition = i -> i.getY() == firstCoord.getY();
            return true;
        }
        else if(current.getY() == toCheck.getY()){
            this.condition = i -> i.getX() == firstCoord.getX();
            return true;
        }
        return false;
    }

    public void inBetween(Pair<Integer, Integer> first, Pair<Integer, Integer> second){
        if (first.getX() == second.getX()){
            int min = Integer.min(first.getY(), second.getY());
            int max = Integer.max(first.getY(), second.getY());
            for (int i = min; i < max; i++) {
                enabled.add(new Pair<Integer,Integer>(first.getX(), i));
            }
        }
        else if (first.getY() == second.getY()){
            int min = Integer.min(first.getX(), second.getX());
            int max = Integer.max(first.getX(), second.getX());
            for (int i = min; i < max; i++) {
                enabled.add(new Pair<Integer,Integer>(i, first.getY()));
            }
        }
    }
}
