package au.edu.federation.myapplication.ExercisePackage;

/**
 * Created by Nick on 30/04/2017.
 *
 * The treasure class is to serve as a container for both a geofence, and a circle
 * that will comprise the treasures location, and Icon on the map.
 */

public class Treasure {

    private int treasureID;
    private boolean activated;

    public boolean activateTreasure(){

        if (activated){
            return false;
        }
        activated = true;
        return true;
    }

    public Treasure(int treasureId){
        this.treasureID = treasureId;
    }

    public int getTreasureID(){
        return  treasureID;
    }
}
