package au.edu.federation.myapplication.ExercisePackage;

import java.util.HashMap;


/**
 * Created by Nick on 30/04/2017.
 *
 * This class will manage all treasures in the game at any given time
 */

public class TreasureManager {


    private HashMap<Integer, Treasure> treasures; //A hashMap to contain all treasures

    public void addTreasure(Treasure treasure){
        treasures.put(treasure.getTreasureID(), treasure);
    }
}
