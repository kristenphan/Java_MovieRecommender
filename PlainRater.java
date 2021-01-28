
/**
 * Keep track of one rater and all their ratings 
 * with each rating representing a movie by its IMDB id and rating
 * Note that a movie might have different ratings assigned by different movie raters
 */

import java.util.*;

public class PlainRater implements Rater {
    private String myID;
    private ArrayList<Rating> myRatings;

    public PlainRater(String id) {
        myID = id;
        myRatings = new ArrayList<Rating>();
    }

    // Add a Rating object (incl. the move's IDMB ID & rating) to a specific rater
    public void addRating(String item, double rating) {
        myRatings.add(new Rating(item,rating));
    }

    // Return true if the rater contains a rating for a specific movie 
    // specified by its IDMB ID @param item.
    // Return false otherwise
    public boolean hasRating(String item) {
        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return true;
            }
        }
        
        return false;
    }
    
    // Return the ID of the rater
    public String getID() {
        return myID;
    }

    // Return the rating of the specified movie (by IMDB ID @param item)
    // If no such rating exists, return -1
    public double getRating(String item) {
        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return myRatings.get(k).getValue();
            }
        }
        
        return -1;
    }

    // Return the number of ratings assigned by this rater
    public int numRatings() {
        return myRatings.size();
    }

    // Return a list of the IMDB ID's of all movies rated by the rater
    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }
        
        return list;
    }
}
