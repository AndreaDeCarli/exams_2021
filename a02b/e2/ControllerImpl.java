package a02b.e2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ControllerImpl {

    private int size;
    private Pair<Integer, Integer> currentLocation;
    
    private Directions currentDirection;
    private Set<Pair<Integer, Integer>> rightCells;
    private Set<Pair<Integer, Integer>> leftCells;
    private Map<Directions, Directions> turnRight;
    private Map<Directions, Directions> turnLeft;
    
    public ControllerImpl(final int size, final Pair<Integer, Integer> initial){
        this.size = size;
        this.currentLocation = initial;
        this.currentDirection = Directions.UP;
        this.rightCells = generateRandomSet(10, Set.of());
        this.leftCells = generateRandomSet(10, rightCells);
        this.turnRight = new HashMap<>(Map.of(
            Directions.UP, Directions.RIGHT,
            Directions.RIGHT, Directions.DOWN,
            Directions.DOWN, Directions.LEFT,
            Directions.LEFT, Directions.UP));
        this.turnLeft = new HashMap<>(Map.of(
            Directions.UP, Directions.LEFT,
            Directions.LEFT, Directions.DOWN,
            Directions.DOWN, Directions.RIGHT,
            Directions.RIGHT, Directions.UP));
        }

    public Pair<Integer, Integer> getCurrentLocation() {
        return currentLocation;
    }
            
    public void hit(){
        Pair<Integer, Integer> temp = offset(currentLocation, currentDirection.getStep());
        if (checkLimits(temp)){
            if (rightCells.contains(temp)){
                currentDirection = turnRight.get(currentDirection);
                currentLocation = temp;
            }
            else if(leftCells.contains(temp)){
                currentDirection = turnLeft.get(currentDirection);
                currentLocation = temp;
            }
            else{
                currentLocation = temp;
            }
        }
        else{
            System.exit(0);
        }
    }

    private Pair<Integer, Integer> offset(Pair<Integer, Integer> coord, Pair<Integer, Integer> offset){
        return new Pair<Integer,Integer>(coord.getX() + offset.getX(), coord.getY() + offset.getY());
    }

    private boolean checkLimits(Pair<Integer, Integer> coord){
        return coord.getX() >= 0 && coord.getX()<size && coord.getY() >= 0 && coord.getY() <size;
    }

    public boolean isInRight(Pair<Integer, Integer> coord){
        return rightCells.contains(coord);
    }

    public boolean isInLeft(Pair<Integer, Integer> coord){
        return leftCells.contains(coord);
    }
    
    enum Directions{
        UP(0,-1),
        DOWN(0,1),
        LEFT(-1,0),
        RIGHT(1,0);

        private Pair<Integer, Integer> step;

        private Directions(final int x, final int y){
            this.step = new Pair<Integer,Integer>(x, y);
        }
        public Pair<Integer, Integer> getStep(){
            return this.step;
        }
    }

    private Set<Pair<Integer, Integer>> generateRandomSet(final int times, final Set<Pair<Integer, Integer>> toCheck){
        Set<Pair<Integer, Integer>> result = new HashSet<>();
        int count = 0;
        while(count!=times){
            Pair<Integer, Integer> newCoord = new Pair<Integer,Integer>(new Random().nextInt(size), new Random().nextInt(size));
            if (checkLimits(newCoord) && !toCheck.contains(newCoord)){
                result.add(newCoord);
                count++;
            }
        }
        return result;
    }
    
}
