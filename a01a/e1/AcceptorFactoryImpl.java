package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AcceptorFactoryImpl implements AcceptorFactory{

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return new Acceptor<String,Integer>() {

            private int count = 0;

            @Override
            public boolean accept(String e) {
                if (e.equals("")){
                    count++;
                }
                return true;
            }

            @Override
            public Optional<Integer> end() {
                return Optional.of(count);
            }
            
        };
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return new Acceptor<Integer,String>() {

            private List<Integer> numbers = new ArrayList<>();
            private boolean accepted = true;

            @Override
            public boolean accept(Integer e) {
                if (numbers.isEmpty() || e > numbers.get(numbers.size()-1)){
                    numbers.add(e);
                    return true;
                }else{
                    accepted = false;
                    return false;
                }
            }

            @Override
            public Optional<String> end() {
                if (!accepted){
                    return Optional.empty();
                }
                return numbers.stream().map(n -> Integer.toString(n)).reduce((a, b) -> a+":"+b);
            }
            
        };
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return new Acceptor<Integer,Integer>() {

            private int count = 0;
            private int sum = 0;

            @Override
            public boolean accept(Integer e) {
                count++;
                if (count <= 3 ){
                    sum += e;
                    return true;
                }
                return false;
            }

            @Override
            public Optional<Integer> end() {
                return count == 3 ? Optional.of(sum) : Optional.empty();
            }
            
        };
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E,Pair<O1,O2>>() {

            @Override
            public boolean accept(E e) {
                return a1.accept(e) && a2.accept(e);
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                if (a1.end().isEmpty() || a2.end().isEmpty()){
                    return Optional.empty();
                }
                else{
                    return Optional.of(new Pair<O1,O2>(a1.end().get(), a2.end().get()));
                }
            }
            
        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
                return new Acceptor<E,O>() {

                    private Optional<S> state = Optional.of(initial);

                    @Override
                    public boolean accept(E e) {
                        state = stateFun.apply(e, state.get());
                        return state.isPresent();
                    }

                    @Override
                    public Optional<O> end() {
                        return state.isPresent() ? outputFun.apply(state.get()) : Optional.empty();
                    }
                    
                };
    }
    
}
