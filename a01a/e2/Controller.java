package a01a.e2;

public interface Controller {

    boolean contains(Pair<Integer, Integer> coord);

    void hitFirst(Pair<Integer, Integer> coord);

    void hitSecond(Pair<Integer, Integer> coord);

    boolean isOver();

}