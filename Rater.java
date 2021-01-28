
/**
 * Rater interface
 */

import java.util.*;

public interface Rater {
    // Add a Rating object (incl. the move's IDMB ID & rating) to a specific rater
    public void addRating(String item, double rating);

    // Return true if the rater contains a rating for a specific movie 
    // specified by its IDMB ID @param item.
    // Return false otherwise
    public boolean hasRating(String item);
    
    // Return the ID of the rater
    public String getID();

    // Return the rating of the specified movie (by IMDB ID @param item)
    // If no such rating exists, return -1
    public double getRating(String item);

    // Return the number of ratings assigned by this rater
    public int numRatings();

    // Return a list of the IMDB ID's of all movies rated by the rater
    public ArrayList<String> getItemsRated();
}
