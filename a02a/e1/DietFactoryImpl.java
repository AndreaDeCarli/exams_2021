package a02a.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import a02a.e1.Diet.Nutrient;

public class DietFactoryImpl implements DietFactory{

    public Diet general(Predicate<Map<Nutrient, Double>> condition, Predicate<Double> total){
        return new Diet() {

            private Map<String, Map<Nutrient, Integer>> foods = new HashMap<>();
            private Map<Nutrient, Double> calories = new HashMap<>();
            private double sum = 0;

            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                foods.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                for (Nutrient nutrient : Nutrient.values()) {
                    calories.put(nutrient, 0.0);
                }
                for (String foodName : dietMap.keySet()) {
                    Map<Nutrient, Integer> map = foods.get(foodName);
                    double multip = dietMap.get(foodName)/100;
                    for (Nutrient nutrient : map.keySet()) {
                        calories.put(nutrient, calories.get(nutrient) + map.get(nutrient)*multip);
                        sum +=  map.get(nutrient)*multip;
                    }
                }
                return condition.test(calories) && total.test(sum);
            }
            
        };
    }

    @Override
    public Diet standard() {
        return general(i -> true,
            s -> s>= 1500 && s<= 2000);
    }

    @Override
    public Diet lowCarb() {
        return general(i -> i.get(Nutrient.CARBS) <= 300,
            s -> s<= 1500 && s>=1000);
    }

    @Override
    public Diet highProtein() {
        return general(i -> i.get(Nutrient.CARBS)<=300 &&
            i.get(Nutrient.PROTEINS)>=1300,
            s -> s>=2000 && s<= 2500);
    }

    @Override
    public Diet balanced() {
        return general(i -> 
            i.get(Nutrient.PROTEINS)>=600 &&
            i.get(Nutrient.CARBS)>=600 &&
            i.get(Nutrient.FAT)>=400 &&
            i.get(Nutrient.FAT) + i.get(Nutrient.PROTEINS)<=1100,
            s -> s<=2000 && s>=1600); 
    }
    
}
