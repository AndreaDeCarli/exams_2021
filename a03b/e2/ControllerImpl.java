package a03b.e2;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ControllerImpl implements Controller {

    private int size;
    private Pair<Integer, Integer> location;
    private Direction direction;
    private Map<Direction, Direction> dirTable;
    private Set<Pair<Integer, Integer>> enabled;

    public ControllerImpl(final int size){
        this.size = size;
        this.location = new Pair<Integer,Integer>(new Random().nextInt(2, size-2), new Random().nextInt(2, size-3));
        this.direction = Direction.UP;
        this.dirTable = Map.of(
            Direction.UP, Direction.RIGHT,
            Direction.RIGHT, Direction.DOWN,
            Direction.DOWN, Direction.LEFT,
            Direction.LEFT, Direction.UP);
        this.enabled = new HashSet<>();
    }

    public boolean hit(){
        var temp = offset(location, dirTable.get(direction).getOffset());
        if (checkOccupied(temp)){
            location = offset(location, direction.getOffset());
            enabled.add(location);
        }
        else{
            direction = dirTable.get(direction);
            enabled.add(temp);
            location = temp;
        }
        return checkLimits(location) ? true : false;
        
    }

    public Pair<Integer, Integer> getLocation(){
        return location;
    }

    private Pair<Integer, Integer> offset(final Pair<Integer, Integer> initial, final Pair<Integer, Integer> offset){
        return new Pair<Integer,Integer>(initial.getX() + offset.getX(), initial.getY() + offset.getY());
    }

    private boolean checkOccupied(final Pair<Integer, Integer> coord){
        return enabled.contains(coord);
    }

    private boolean checkLimits(final Pair<Integer, Integer> coord){
        return coord.getX() >= 0 && coord.getX()<size && coord.getY()>=0 && coord.getY()<size;
    }



}
