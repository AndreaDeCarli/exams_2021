package a02b.e1;

import static a02b.e1.UniversityProgram.Sector.COMPUTER_ENGINEERING;
import static a02b.e1.UniversityProgram.Sector.COMPUTER_SCIENCE;
import static a02b.e1.UniversityProgram.Sector.MATHEMATICS;
import static a02b.e1.UniversityProgram.Sector.PHYSICS;
import static a02b.e1.UniversityProgram.Sector.THESIS;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import a02b.e1.UniversityProgram.Sector;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory{

    public UniversityProgram general(Predicate<Map<Sector, Integer>> condition, Predicate<Integer> total){
        return new UniversityProgram() {

            private Map<String, Pair<Sector, Integer>> courses = new HashMap<>();
            private Map<Sector, Integer> credits = new HashMap<>();
            private int sum = 0;

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                courses.put(name, new Pair<Sector,Integer>(sector, credits));
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                sum = 0;
                for (Sector sec : Sector.values()) {
                    credits.put(sec, 0);
                }
                for (String string : courseNames) {
                    Sector sector = courses.get(string).get1();
                    Integer cred = courses.get(string).get2();
                    sum += cred;
                    System.err.println(sum);
                    credits.put(sector, credits.get(sector) + cred);
                }
                return condition.test(credits) && total.test(sum);
            }
            
        };
    }

    @Override
    public UniversityProgram flexible() {
        return general(i -> true,
            s -> s==60);
    }

    @Override
    public UniversityProgram scientific() {
        return general(i -> 
            i.get(MATHEMATICS)>=12 &&
            i.get(COMPUTER_SCIENCE)>=12 &&
            i.get(PHYSICS)>=12,
            s -> s==60);
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return general(i -> 
            i.get(COMPUTER_SCIENCE) + i.get(COMPUTER_ENGINEERING)>=30,
            s -> s>=48);
    }

    @Override
    public UniversityProgram realistic() {
        return general(i -> 
            i.get(COMPUTER_SCIENCE) + i.get(COMPUTER_ENGINEERING)>=60 &&
            i.get(MATHEMATICS) + i.get(PHYSICS) <= 18 &&
            i.get(THESIS) == 24,
            s -> s == 120);
    }
    
}
