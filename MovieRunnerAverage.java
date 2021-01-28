import java.util.*;

/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
/**
 * Test SecondRatings class
 * E.g. for movieFile ""data/ratedmovies_short.csv" and ratingFile "data/ratings_short.csv",
 * output is as follows:
 * # movies = 5
 * # raters = 5
 * 8.25 Her
 * 9.0 The Godfather
 */
public class MovieRunnerAverage {
    public void printAverageRatings() {
        // read movie & rating data
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", 
                                             "data/ratings.csv");
        
        // print the # movies and # raters
        System.out.println("# movies = " + sr.getMovieSize() + "\n" + 
                           "# raters = " + sr.getRaterSize());
                           
        // print a list of movies and their average ratings 
        // (print its rating first, followed by its title) in sorted order from lowest to highest ratings
        // for all those movies that have at least a specified number of ratings
        int minimalRaters = 12;
        ArrayList<Rating> avgRatings = sr.getAverageRatings(minimalRaters);
        Collections.sort(avgRatings);
        for (Rating r: avgRatings) {
            System.out.println(r.getValue() + " " + sr.getTitle(r.getItem()));
        }
    }
    
    /**
     * Read movie and rating file, and print the average rating for a specific movie title
     * E.g. for movie file "ratedmovies_short.csv", the avg rating for the movie "The Godfather" = 9.0
     */
    public void getAverageRatingOneMovie() {
        // read in dada from movie + rating file
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", 
                                             "data/ratings.csv");
                                             
        // print out the avg ratings for a specifc movie title
        int minimalRaters = 1;
        String targetTitle = "Vacation";
        String targetID = sr.getID(targetTitle);
        if (!targetID.equals("NO SUCH TITLE")) {
        ArrayList<Rating> avgRatings = sr.getAverageRatings(minimalRaters);
            for (Rating r: avgRatings) {
                if (r.getItem().equals(targetID)) {
                    System.out.println(r.getValue());
                    return;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        MovieRunnerAverage runner = new MovieRunnerAverage();
        runner.printAverageRatings();
    }
}








