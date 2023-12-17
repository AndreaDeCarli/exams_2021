package a01c.e1;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventHistoryFactoryImpl implements EventHistoryFactory{

    @Override
    public <E> EventHistory<E> fromMap(Map<Double, E> map) {
        return fromIterators(map.keySet().stream().sorted().collect(Collectors.toList()).iterator(), 
            map.keySet().stream().sorted().map(i -> map.get(i)).collect(Collectors.toList()).iterator());
    }

    @Override
    public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
        return new EventHistory<E>() {

            @Override
            public double getTimeOfEvent() {
                return times.next();
            }

            @Override
            public E getEventContent() {
                return content.next();
            }

            @Override
            public boolean moveToNextEvent() {
                return times.hasNext() && content.hasNext();
            }
            
        };
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
        return new EventHistory<E>() {

            private int index = 0;

            @Override
            public double getTimeOfEvent() {
                return initial + (delta*index);
            }

            @Override
            public E getEventContent() {
                return content.get(index);
            }

            @Override
            public boolean moveToNextEvent() {
                index++;
                if (index >= content.size()){
                    return false;
                }
                return true;
            }
            
        };
    }

    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        return new EventHistory<E>() {

            private int index = 0;
            private double time = 0;

            @Override
            public double getTimeOfEvent() {
                return time;
            }

            @Override
            public E getEventContent() {
                return content.get();
            }

            @Override
            public boolean moveToNextEvent() {
                if (index < size -1){
                    index++;
                    time+= Math.random();
                    return true;
                }
                return false;
            }
            
        };
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromFile'");
    }
    
}
