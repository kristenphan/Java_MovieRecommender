/**
 * ....
 */

import java.util.*;
import java.nio.*;
import java.io.*;

public class ThirdRatings {
    private ArrayList<EfficientRater> myRaters;
        
    public ThirdRatings() {
        // default constructor
        this("data/ratings.csv");
    }
    
    public ThirdRatings(String ratingsFile) {
        FirstRatings fr = new FirstRatings();
        try {
            myRaters = fr.loadRaters(ratingsFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
        // get all movies from MovieDatabase class and store them in an ArrayList of movie IDs
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        
        // create an ArrayList of type Rating to store movie IDs and their avg ratings
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        
        // iterate through all movies and find those with at least @param minimalRaters and their ratings
        // add those movies and avg ratings to the output avgRatings
        for (String id: movies) {
            double avgRating = getAverageByID(id, minimalRaters);
            if (avgRating != 0.0) {
                avgRatings.add(new Rating(id, avgRating));
            }
        }
        
        return avgRatings; 
    }
    
    /**
     * Return an ArrayList of type Rating of all the movies which have at least @param minimalRaters ratings
     * and satisfies @param filterCriteria
     */
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        // create the ArrayList of type String of movie IDs from the MovieDatabase using the filterBy()
        ArrayList<String> filteredMovies = MovieDatabase.filterBy(filterCriteria);
        
        // create an ArrayList of type Rating to store movie IDs and their avg ratings
        ArrayList<Rating> avgRatings = new ArrayList<Rating>();
        
        // iterate through all movies and find those with at least @param minimalRaters and their ratings
        // add those movies and avg ratings to the output avgRatings
        for (String id: filteredMovies) {
            double avgRating = getAverageByID(id, minimalRaters);
            if (avgRating != 0.0) {
                avgRatings.add(new Rating(id, avgRating));
            }
        }
        
        return avgRatings; 
    }
}











