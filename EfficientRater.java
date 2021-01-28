
/**
 * Keep track of one rater and all their ratings 
 * with each rating representing a movie by its IMDB id and rating
 * Note that a movie might have different ratings assigned by different movie raters
 */

import java.util.*;

public class EfficientRater implements Rater {
    private String myID; // raterID
    private HashMap<String, Rating> myRatings; // Mapping itemIDs (type String) to Rating objects. For each Rating object, anItem = itemID; anValue = itemRating
    
    public EfficientRater(String raterID) {
        myID = raterID;
        myRatings = new HashMap<String, Rating>();
    }

    // Add a Rating object comprised of itemID and itemRating to the rater
    public void addRating(String itemID, double itemRating) {
        myRatings.put(itemID, new Rating(itemID, itemRating));
    }

    // Return true if the rater contains a rating for a specific item with @param itemID
    // Return false otherwise
    public boolean hasRating(String itemID) {
        return myRatings.containsKey(itemID);
    }
    
    // Return the ID of the rater
    public String getID() {
        return myID;
    }

    // Return the rating of the specified item @param itemID
    // If no such rating exists, return -1
    public double getRating(String itemID) {
        if (hasRating(itemID)) {
            return myRatings.get(itemID).getValue();
        }
        return -1;
    }

    // Return the number of ratings assigned by this rater
    public int numRatings() {
        return myRatings.size();
    }

    // Return a list of the IMDB ID's of all movies rated by the rater
    public ArrayList<String> getItemsRated() {
        return new ArrayList<> (myRatings.keySet());
    }
    
    public void printRaterData() {
        for (Rating r: myRatings.values()) {
            System.out.println(r);
        }
    }
}
