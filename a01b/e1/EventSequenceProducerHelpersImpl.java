package a01b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventSequenceProducerHelpersImpl implements EventSequenceProducerHelpers{

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                if (iterator.hasNext()){
                    return iterator.next();
                }
                else{
                    throw new NoSuchElementException();
                }
            }
            
        };
    }

    public <E> Stream<Pair<Double, E>> toStream(EventSequenceProducer<E> sequence){
        List<Pair<Double, E>> result = new ArrayList<>();
        boolean control = true;
        while(control){
            try {
                result.add(sequence.getNext());
            } catch (Exception e) {
                control = false;
            }
        }
        System.out.println(result);
        return result.stream();

    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {
        return toStream(sequence)
            .filter(elem -> elem.get1() > fromTime && elem.get1() < toTime)
            .map(elem -> elem.get2())
            .collect(Collectors.toList());

    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        return toStream(sequence).map(elem -> elem.get2()).collect(Collectors.toList());
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(EventSequenceProducer<E> sequence, double time) {
        List<Pair<Double, E>> reuslt = toStream(sequence).filter(elem -> elem.get1() > time).collect(Collectors.toList());
        return reuslt.isEmpty() ? Optional.empty() : Optional.of(reuslt.get(0));
    }

    @Override
    public <E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                Pair<Double, E> result = sequence.getNext();
                if (predicate.test(result.get2())){
                    return result;
                }
                else{
                    throw new NoSuchElementException();
                }
            }
            
        };
    }
    
}
