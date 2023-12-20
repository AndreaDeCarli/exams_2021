package a01c.e2;

public interface Controller {

    boolean contains(Pair<Integer, Integer> coord);

    boolean hit(Pair<Integer, Integer> coord);

    void setPosition(Pair<Integer, Integer> position);

}