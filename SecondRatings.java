/**
 * ....
 */

import java.util.*;
import java.nio.*;
import java.io.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<EfficientRater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String movieFile, String ratingsFile) {
        FirstRatings fr = new FirstRatings();
        try {
            myMovies = fr.loadMovies(movieFile);
            myRaters = fr.loadRaters(ratingsFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Return the number of movies that were read in and stored in myMovies
     */
    public int getMovieSize() {
        return myMovies.size();
    }
    
    /**
     * Return the number of raters that were read in and stored in myRaters
     */
    public int getRaterSize() {
        return myRaters.size();
    }
    
    /**
     * Return a doule representing the average rating for a specified movie 
     * (by @param id i.e. movie's IMDB id) if there are at least @param minimalRaters ratings.
     * If there are not @param minimalRaters ratings, return 0.0
     */
    private double getAverageByID(String id, int minimalRaters) {
        // create an array to store all ratings of the movie
        ArrayList<Double> ratings = new ArrayList<Double>();
        
        // iterate through all raters and find all ratings for the movie
        for (EfficientRater rater: myRaters) {
            double rating = rater.getRating(id);
            if (rating != -1) {
                ratings.add(rating);
            }
        }
        
        // if there are at least @param minimalRaters ratings, return the avg rating. else, return 0.0
        if (ratings.size() >= minimalRaters) {
            double avg = ratings.stream()
                                .mapToDouble(i -> i)
                                .average()
                                .orElse(0.0); 
            return avg;
        }
        else {
            return 0.0;
        }
    }
    
    /**
     * Return an ArrayList of Rating that contains the average rating for every movie 
     * with @param minialRaters ratings
     */
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        // create an ArrayList of Ratings to store movie id's and their avg ratings
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        
        // iterate through all movies and find those with at least @param minimalRaters and their ratings
        // add those movies and avg ratings to the output avgRatings
        for (Movie movie: myMovies) {
            String id = movie.getID();
            double avgRating = getAverageByID(id, minimalRaters);
            if (avgRating != 0.0) {
                avgRatings.add(new Rating(id, avgRating));
            }
        }
        
        return avgRatings; 
    }
    
    /**
     * Return the title of the movie with the @param id. If no such movie exists, return "NO SUCH ID"
     */
    public String getTitle(String id) {
        for (Movie movie: myMovies) {
            if (movie.getID().equals(id)) {
                return movie.getTitle();
            }
        }
        
        return "NO SUCH ID";
    }
    
    /**
     * Return the IMDB id of the movie with the @param title. If no such movie exists, return "NO SUCH TITLE"
     */
    public String getID(String title) {
        for (Movie movie: myMovies) {
            if (movie.getTitle().equals(title)) {
                return movie.getID();
            }
        }
        
        return "NO SUCH TITLE";
    }
}











