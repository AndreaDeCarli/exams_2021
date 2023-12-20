package a01c.e2;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class ControllerImpl implements Controller {

    private Set<Pair<Integer, Integer>> enabled;
    private Pair<Integer, Integer> currentLocation;
    private Direction currentDirection;

    public ControllerImpl(){
        this.enabled = new HashSet<>();
        this.currentDirection = Direction.HORIZONTAL;
    }

    @Override
    public boolean contains(final Pair<Integer, Integer> coord){
        return enabled.contains(coord);
    }

    @Override
    public boolean hit(final Pair<Integer, Integer> coord){
        if (currentDirection.condition.apply(currentLocation, coord)){
            inBetween(coord);
            currentLocation = coord;
            changeDirection();
            return true;
        }
        return false;
    }

    private void changeDirection(){
        if (currentDirection.equals(Direction.HORIZONTAL)){
            currentDirection = Direction.VERTICAL;
        }else{
            currentDirection = Direction.HORIZONTAL;
        }
    }

    private void inBetween(Pair<Integer, Integer> coord){
        if (currentDirection.equals(Direction.HORIZONTAL)){
            int min = Integer.min(currentLocation.getY(), coord.getY());
            int max = Integer.max(currentLocation.getY(), coord.getY());
            for (int i = min; i <= max; i++) {
                enabled.add(new Pair<Integer,Integer>(coord.getX(), i));
            }
        }else{
            int min = Integer.min(currentLocation.getX(), coord.getX());
            int max = Integer.max(currentLocation.getX(), coord.getX());
            for (int i = min; i <= max; i++) {
                enabled.add(new Pair<Integer,Integer>(i, coord.getY()));
            }
        }
    }

    @Override
    public void setPosition(Pair<Integer, Integer> position) {
        this.currentLocation = position;
        enabled.add(position);
    }
    
    enum Direction{
        VERTICAL((i,l) -> i.getY() == l.getY()),
        HORIZONTAL((i,l) -> i.getX() == l.getX()),
        ANY((i,l) -> true);

        private BiFunction<Pair<Integer, Integer>, Pair<Integer, Integer>, Boolean> condition;

        private Direction(BiFunction<Pair<Integer, Integer>, Pair<Integer, Integer>, Boolean> condition){
            this.condition = condition;
        }
    }

    

}
