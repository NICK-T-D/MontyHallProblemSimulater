package io.nicktd.etc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * Monty Hall problem : Simulation the probability of contestants  
 * selected the car when she/he always choose another door; 
 * If you don't know the problem , just Google it;
 * </p>
 * 
 * @author ${Author}
 * 
 */
public class MontyHallProblemSimulater {
    private static Random random = new Random();

    private static final List<Door> doorList = new ArrayList<>();
    private static List<Door> tempDoorList = new ArrayList<>();
    private static final Door doorWithCar = new Door(Award.buildCar());
    private static final Door doorWithGoat = new Door(Award.buildGoat());
    private static final Door doorWithAnotherGoat = new Door(Award.buildGoat());

    public static void main(String[] args) {

        prepareDoors();
        int totalTimes = 1 << 20;
        int hitTimes = 0;

        for (int i = 0; i < totalTimes; i++) {
            Door firstChoice = getRandomDoor(doorList);
            Door replacedDoor = getReplacedDoor(firstChoice);
            if (replacedDoor.hasCar()) {
                hitTimes++;
            }
        }

        double hitProbability = hitTimes * 1.0 / totalTimes;
        System.out.println("the probability is " + hitProbability);
    }

    public static void prepareDoors() {
        doorList.add(doorWithCar);
        doorList.add(doorWithGoat);
        doorList.add(doorWithAnotherGoat);
    }

    static Door getReplacedDoor(Door firstChoice) {
        tempDoorList.clear();
        tempDoorList.addAll(doorList);
        tempDoorList.remove(firstChoice);

        if (!firstChoice.hasCar()) {
            Door openedDoor = firstChoice == doorWithGoat ? doorWithAnotherGoat : doorWithGoat;
            tempDoorList.remove(openedDoor);
        }
        return getRandomDoor(tempDoorList);
    }

    static Door getRandomDoor(List<Door> list) {
        int nextInt = random.nextInt(list.size());
        return list.get(nextInt);
    }

}

class Door {
    private Award award;

    public Door(Award award) {
        this.award = award;
    }

    public boolean hasCar() {
        return this.award.isCar();
    }

}

abstract class Award {
    public abstract boolean isCar();

    public static Award buildCar() {
        return new Car();
    }

    public static Award buildGoat() {
        return new Goat();
    }

    private static class Car extends Award {
        @Override
        public boolean isCar() {
            return true;
        }
    }

    private static class Goat extends Award {
        @Override
        public boolean isCar() {
            return false;
        }
    }

}
