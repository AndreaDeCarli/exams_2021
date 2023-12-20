package a03b.e2;

public interface Controller {

    enum Direction {
        UP(0,-1),
        DOWN(0,1),
        RIGHT(1,0),
        LEFT(-1,0);
    
        private Pair<Integer, Integer> offset;
    
        private Direction(final int x, final int y){
            this.offset = new Pair<Integer,Integer>(x, y);
        }
    
        public Pair<Integer, Integer> getOffset(){
            return this.offset;
        }
    }

    boolean hit();

    Pair<Integer, Integer> getLocation();

}