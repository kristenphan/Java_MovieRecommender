/** 
 * An immutable passive data object (PDO) to represent a rating data of a single movie
 */
public class Rating implements Comparable<Rating> {
    private String item; // the ID of an item of interest e.g. movieID, raterID
    private double value; // the item's rating

    public Rating (String anItem, double aValue) {
        item = anItem;
        value = aValue;
    }

    // Returns the IMDB ID of the movie being rated
    public String getItem () {
        return item;
    }

    // Returns the rating of a movie (as a number so it can be used in calculations)
    public double getValue () {
        return value;
    }

    // Returns a string of all the rating information
    public String toString () {
        return "itemID = " + getItem() + "; itemRating = " + getValue();
    }

    // Compare the rating of the current movie to that of another movie
    public int compareTo(Rating other) {
        if (value < other.value) return -1;
        if (value > other.value) return 1;
        
        return 0;
    }
}
