 /**
 * ....
 */

import java.util.*;
import java.nio.*;
import java.io.*;

public class FourthRatings {
    /**
     * Return a doule representing the average rating for a specified movie with @param movieID
     * if there are at least @param minimalRaters ratings by all raters in RaterDatabase. Otherwise, return 0.0
     * This method will be called from getAverageRatings() method
     */
    private double getAverageByID(String movieID, int minimalRaters) {
        // Create an array to store all ratings of the movie
        ArrayList<Double> ratings = new ArrayList<Double>();
        
        // Assuming that the RaterDatabase has been initialized
        // Iterate through all raters in RaterDatabase and find ratings by all raters for the movie
        for (Rater rater: RaterDatabase.getRaters()) {
           double rating = rater.getRating(movieID);
            if (rating != -1) {
                ratings.add(rating);
           }
        }
        
        // if there are at least @param minimalRaters, calculate and return the avg rating. 
        // Else, return 0.0
        if (ratings.size() >= minimalRaters) {
            double avg = ratings.stream()
                                .mapToDouble(i -> i)
                                .average()
                                .orElse(1000.0); 
            return avg;
        }
        else {
            return 0.0;
        }
    }
    
    /**
     * Test getAverageByID() method
     * E.g. 
     * input: file "ratings_short.csv", movieID = "0068646", minimalRaters = 1; 
     * output = 9.0
     */
    private void testGetAverageByID() {
        // Initialize the RaterDatabase
        String ratingFile = "data/ratings_short.csv";
        RaterDatabase.initialize(ratingFile);
        System.out.println("read data for " + RaterDatabase.size() + " raters");
                           
        // Calculate average rating
        String movieID = "0068646";
        int minimalRaters = 1;
        double avgRating = getAverageByID(movieID, minimalRaters);
        System.out.println("movieID = " + movieID + "; avgRating = " + avgRating + "; correct avg = 9");   
    }
    
    /**
     * Return an ArrayList of type Rating of all movies which have at least @param minimalRaters
     * Each Rating object stores a movieID and the average rating by all raters in RaterDatabase for that movie
     */
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        // Assuming that the MovieDatabase and RaterDatabase have been initialized
        // Get a list of movieIDs of all movies in MovieDatabase using TrueFiler()
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        
        // Create an ArrayList of type Rating to store movie IDs and their avg ratings
        ArrayList<Rating> movieAvgRatings = new ArrayList<Rating>();
        
        // Iterate through all movies and find those with at least @param minimalRaters and their average ratings
        for (String movieID: movieIDs) {
            double avgRating = getAverageByID(movieID, minimalRaters);
            if (avgRating != 0.0) { // the movie has at least @param minimalRaters
                movieAvgRatings.add(new Rating(movieID, avgRating));
            }
        }
       
        return movieAvgRatings; 
    }
    
    /**
     * Test getAverageRatings()
     * Test case:
     * input: file "ratings_short.csv" and "ratedmovies_short.csv", minimalRaters = 3;
     * output: 2 movies -- Her with average rating of 8.25 and The Godfater with 9.0
     */
    private void testGetAverageRatings() {
        // Initialize the RaterDatabase
        String ratingFile = "data/ratings_short.csv";
        RaterDatabase.initialize(ratingFile);
        System.out.println("read data for " + RaterDatabase.size() + " raters");
                           
        // Initialize the MovieDatabase
        String movieFile = "data/ratedmovies_short.csv";
        MovieDatabase.initialize(movieFile);
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        // Calculate the average ratings for all movies in MovieDatabase with at least @param minimalRaters by any of the raters in RaterDatabase
        int minimalRaters = 3;
        ArrayList<Rating> movieAvgRatings = getAverageRatings(minimalRaters);
        System.out.println("found " + movieAvgRatings.size() + " movies");
        
        // Sort the movies by their average ratings (from lowest to highest) and print them 
        Collections.sort(movieAvgRatings);
        for (Rating r: movieAvgRatings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem())); // E.g. "movieAvgRating movieName" 
        }
    }
    
    /**
     * Return an ArrayList of type Rating of all movies which have at least @param minimalRaters ratings and satisfies @param filterCriteria 
     * Each Rating object stores a movieID and the average rating by all raters in the RaterDatabase for that movie
     */
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        // Assuming that the MovieDatabase and RaterDatabase have been initialized
        // Create the ArrayList of type String of movie IDs from the MovieDatabase using @param filterCriteria
        ArrayList<String> filteredMovieIDs = MovieDatabase.filterBy(filterCriteria);
        
        // create an ArrayList of type Rating to store movie IDs and their avg ratings
        ArrayList<Rating> movieAvgRatingsByFilter = new ArrayList<Rating>();
        
        // Iterate through all movies and find those with at least @param minimalRaters and their ratings
        // add those movies and avg ratings to the output avgRatings
        for (String movieID: filteredMovieIDs) {
            double avgRating = getAverageByID(movieID, minimalRaters);
            if (avgRating != 0.0) { // the movie has at least @minimalRaters
                movieAvgRatingsByFilter.add(new Rating(movieID, avgRating));
            }
        }
        
        return movieAvgRatingsByFilter; 
    }
    
    /**
     * Test getAverageRatingsByFilter() method
     * Test case:
     * input: file "ratings_short.csv" and "ratedmovies_short.csv", minimalRaters = 3; GenreFilter for "Romance"
     * output: 1 movie -- Her with average rating of 8.25
     */
    private void testGetAverageRatingsByFilter() {
        // Initialize the RaterDatabase
        String ratingFile = "data/ratings_short.csv";
        RaterDatabase.initialize(ratingFile);
        System.out.println("read data for " + RaterDatabase.size() + " raters");
                           
        // Initialize the MovieDatabase
        String movieFile = "data/ratedmovies_short.csv";
        MovieDatabase.initialize(movieFile);
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        // Create a GenreFilter
        String targetGenre = "Romance";
        GenreFilter gf = new GenreFilter(targetGenre);
        
        // Calculate the average ratings for all movies in MovieDatabase with at least @param minimalRaters by any of the raters in RaterDatabase
        int minimalRaters = 3;
        ArrayList<Rating> movieAvgRatings = getAverageRatingsByFilter(minimalRaters, gf);
        System.out.println("found " + movieAvgRatings.size() + " movies");
        
        // Sort the movies by their average ratings (from lowest to highest) and print them 
        Collections.sort(movieAvgRatings);
        for (Rating r: movieAvgRatings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem())); // E.g. "movieAvgRating movieName" 
        }
    }
    
    /**
     * Transforms the ratings stored by @param Rater me and r and calculates the dot product of the two raters as a measure of their similarity
     * This method will be called from getSimilarities()
     */
    private int dotProduct(Rater me, Rater r) {
        // Create a new variable dotProduct that measures the similarity between Rater me and Rater r
        int dotProduct = 0;
        
        // Iterate through all movies in @param me
        for (String currMovieID: me.getItemsRated()) {
            if (r.hasRating(currMovieID)) { // if both raters rate the movie, update the dotProduct reflect the ratings
                dotProduct += (me.getRating(currMovieID) - 5) * (r.getRating(currMovieID) - 5); 
            }
        }
        
        return dotProduct;
    }
    
    /**
     * Test dotProduct() method with sample input and correct output shown below - CORRECT OUTPUT FOR QUIZ
     */
    private void testDotProduct() {
        //Rater me = new PlainRater("r1");
        //me.addRating("m1", 7);
        //me.addRating("m2", 3);
        //me.addRating("m3", 10);
        //Rater r = new PlainRater("r2"); 
        //r.addRating("m2", 7);
        //r.addRating("m3", 1);
        //System.out.println("dotProduct = " + dotProduct(me, r) + "\n" + 
        //                   "correct output = -24");
                           
        Rater me = new PlainRater("15");
        me.addRating("2354", 10);
        me.addRating("3285", 6);
        me.addRating("1297", 2);
        me.addRating("5804", 8);
        Rater r = new PlainRater("20"); 
        r.addRating("3285", 4);
        r.addRating("1297", 7);
        r.addRating("6574", 10);
        r.addRating("2354", 9);
        System.out.println("dotProduct = " + dotProduct(me, r));
    }
    
    /**
     * Compute a similarity rating for each rater in the RaterDatabase (except the rater with the ID
     * given by the parameter) to see how similar they are to the Rater whose ID is the parameter
     * Returns an ArrayList of type Rating sorted by ratings from highest to lowest rating 
     * with the highest rating rating first and only including those raters who have a positive similarity rating
     * since those with negative values are not similar in any way
     * Note hat in each Rating object, the item field is raterID and the value field is the dot product
     * comparision between that rater and the rater whose ID is the parameter
     * Be sure not to sue the dotProduct() with the parameter id and itself
     */
    private ArrayList<Rating> getSimilarities(String targetRaterID) {
        // Create an ArrayList of type Rating - each Rating object stores a raiderID and its similarity rating in relation to the @param targetRaterID
        ArrayList<Rating> raterRatings = new ArrayList<Rating>();
        
        // Iterate through all raters in the RaterDatabase and calculate the dot product as a measure of similarity between that rater and the target rater with @param targetRaterID
        for (Rater r: RaterDatabase.getRaters()) {
            String currRaterID = r.getID();
            if (!currRaterID.equals(targetRaterID)) { // calculate the dot product for all pairs of raters except for the target rater with @param targetRaterID with itself
                int similarityRating = dotProduct(RaterDatabase.getRater(targetRaterID), RaterDatabase.getRater(currRaterID));
                raterRatings.add(new Rating(currRaterID, similarityRating)); 
            }
        }
        
        return raterRatings;
    }
    
    /**
     * Test getSimilarities() method with the test case outlined in code below
     */
    private void testGetSimilarities() {
        // Initialize RaterDatabase
        String ratingFile = "data/ratings_short.csv";
        RaterDatabase.initialize(ratingFile);
        
        // Calculate the similarity ratings for all raters in RaterDatabase in relation to the target rater
        String targetRaterID = "1";
        ArrayList<Rating> raterRatings = getSimilarities(targetRaterID);
        
        // Print raterRatings
        for (Rating r: raterRatings) {
            System.out.println(r);
        }
        System.out.println("Correct output: r2 = 20; r3 = 0; r4 = 15; r5 = 20");
    }
    
        
    /**
     * Return the first @param numSimilarRaters from a list of raters @param raterRatings
     */
    private ArrayList<Rating> chooseSimilarRaters(ArrayList<Rating> raterRatings, int numSimilarRaters) {
        // Assuming that raters in raterRatings are already sorted by their similarity ratings from highest to lowest
        // Choose the first @numSimilarRaters 
        ArrayList<Rating> similarRaters = new ArrayList<Rating>();
        
        // Choose the first @param numSimilarRaters from the list of raters @param raterRatings or the entire list if there are fewer than @numSimilarRaters in the list
        for (int i = 0; i < Math.min(numSimilarRaters, raterRatings.size()); i++) { 
            if (raterRatings.get(i).getValue() > 0) { // Choose only raters with a positive similarity rating
                similarRaters.add(raterRatings.get(i));
            }
        } 
        
        return similarRaters;
    }
    
    /**
     * Calculate weighted ratings for all movies which are rated by raters in @param similarRaters and included in @param filterMovieIDs
     */
    private HashMap<String, ArrayList<Double>> calculateWeightedRating(ArrayList<Rating> similarRaters, ArrayList<String> filteredMovieIDs) {
        // Create a new HashMap to store movieID (key) and an ArrayList of type Double for that movie's weighted ratings by different raters
        HashMap<String, ArrayList<Double>> movieWeightedRatingMap = new HashMap<String, ArrayList<Double>>();
        
        // Iterate through all movies rated by the @param numSimilarRaters selected raters and calculate their weighted ratings by different raters
        // Each weighted rating is calculated by multiplying the similarity rating of a particular rater and the movie rating by that rater
        for (Rating rater: similarRaters) {
            String raterID = rater.getItem();
            double raterSimilarityRating = rater.getValue();
            ArrayList<String> movieIDsByRater = RaterDatabase.getRater(raterID).getItemsRated();
            for (String movieID: movieIDsByRater) {
                if (filteredMovieIDs.contains(movieID)) {
                    double movieRating = RaterDatabase.getRater(raterID).getRating(movieID);
                    double movieWeightedRating = raterSimilarityRating * movieRating;
                    if (movieWeightedRatingMap.containsKey(movieID)) { // if movieID already exists in map, add weighted rating to map
                        movieWeightedRatingMap.get(movieID).add(movieWeightedRating);
                    }
                    else { // if a movie does not exist in map, add movie and its weighted rating to map
                        movieWeightedRatingMap.put(movieID, new ArrayList<Double>());
                        movieWeightedRatingMap.get(movieID).add(movieWeightedRating);
                    }
                }
            }
        }
        
        return movieWeightedRatingMap;
    }
    
    private ArrayList<Rating> calculateWeightedAverageRating(HashMap<String, ArrayList<Double>> movieWeightedRatingMap, int minimalRaters) {
        // Create an ArrayList of type Rating
        // Each Rating object stores a movieID and its weighted average rating
        ArrayList<Rating> similarMovieRatings = new ArrayList<Rating>();
        
        // Select only movies with at least @param minimalRaters and subsequently calculate their weighted average ratings
        // A movie's weighted average rating is calculated by averaging all weighted ratings for that movie. 
        // Each movie should have only 1 weighted average rating
        for (String movieID: movieWeightedRatingMap.keySet()) {
            ArrayList<Double> weightedRatings = movieWeightedRatingMap.get(movieID);
            if (weightedRatings.size() >= minimalRaters) { // only calculate weighted average rating for movies with at least @param minimalRaters
                double weightedAverageRating = weightedRatings.stream().mapToDouble(i -> i).sum() / weightedRatings.size();
                similarMovieRatings.add(new Rating(movieID, weightedAverageRating));
            }
        }
        
        return similarMovieRatings;
    }
    
    /**
     * Return an ArrayList of type Rating, of movies and their weighted average ratings using only 
     * the top numSimilarRaters with positive ratings and including only those movies that have 
     * at least minimalRaters ratings from those most similar raters 
     * (not just minimalRaters ratings overall). 
     * For example, if minimalRaters is 3 and a movie has 4 ratings but only 2 of those ratings were made by raters in the top numSimilarRaters, 
     * that movie should not be included.
     * This method calls getSimilarRatingsByFilter() method and uses a TrueFilter as the filterCriteria to qualify all movies in the MovieDatabase for recommendations
     */
    public ArrayList<Rating> getSimilarRatings(String targetRaterID, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> similarMovieRatings = getSimilarRatingsByFilter(targetRaterID, numSimilarRaters, minimalRaters, new TrueFilter());
        return similarMovieRatings;
    }
    
    /**
     * Test chooseSimilarRaters(), calculatedWeightedRating(), calculateweightedAverageRating(), getSimilarRatings() method
     * Test case 2: 
     * file "ratings.csv", "ratedmoviesfull.csv", targetRaterID = "65"; numSimilarRaters = 20; minimalRaters = 5
     * output: “The Fault in Our Stars” as the 1st recommendation
     */
    private void testGetSimilarRatings() {
        // Initialize RaterDatabase + MovieDatabase
        String ratingFile = "data/ratings.csv";
        RaterDatabase.initialize(ratingFile);
        String movieFile = "data/ratedmoviesfull.csv";
        MovieDatabase.initialize(movieFile);
        
        // Initialize
        // String targetRaterID = "1";
        String targetRaterID = "65";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        ArrayList<Rating> similarMovieRatings = getSimilarRatings(targetRaterID, numSimilarRaters, minimalRaters);
        System.out.println("# recs = " + similarMovieRatings.size());
        for (Rating r: similarMovieRatings) { 
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    /**
     * Uses that filter to access and rate only those movies that match the filter criteria
     * TODO: 1) finish this method 2) refactor getSimilarRatings() by calling this method
     */
    public ArrayList<Rating> getSimilarRatingsByFilter(String targetRaterID, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        // Initialize the RaterDatabase
        String ratingFile = "data/ratings.csv";
        RaterDatabase.initialize(ratingFile);
        
        // Initialize and filter MovieDatabase using @param filterCriteria
        String movieFile = "data/ratedmoviesfull.csv";
        MovieDatabase.initialize(movieFile);
        
        // Filter MovieDatabase by @param filterCriteria and get a list of all qualified movies
        ArrayList<String> filteredMovieIDs = MovieDatabase.filterBy(filterCriteria);
        
        // Calculate the similarity ratings for all raters in RaterDatabase
        // Each Rating object stores the raiderID of a rater and and its similarity rating in relation to the target rater with @param targetRaterID 
        ArrayList<Rating> raterRatings = getSimilarities(targetRaterID);
        
        // Sort raters by similarity ratings from highest to lowest
        Collections.sort(raterRatings, Collections.reverseOrder());
        
        // Choose @param numSimilarRaters of raters with positive similarity ratings from raterRatings
        ArrayList<Rating> similarRaters = chooseSimilarRaters(raterRatings, numSimilarRaters);
        
        // Calculate the weighted ratings for all movies rated by any of the raters in similarRaters
        HashMap<String, ArrayList<Double>> movieWeightedRatingMap = calculateWeightedRating(similarRaters, filteredMovieIDs);
        
        // Keep only movies rated by at least @param minimalRaters from similarRaters and calculate their weighted average ratings
        ArrayList<Rating> similarMovieRatings = calculateWeightedAverageRating(movieWeightedRatingMap, minimalRaters);
               
        // Sort movies by their weighted average ratings from highest to lowest
        Collections.sort(similarMovieRatings, Collections.reverseOrder());
        
        return similarMovieRatings;
    }
    
    /**
     * Test getSimilarRatingsByFilter() method 
     * Test case:
     * input: files "ratedmoviesfull.csv" and "ratings.csv", targetGenre = "Action",  targetRaterID = "65", minimalRaters = 5, numSimilarRaters = 20
     * output: the movie returned with the top rated average is “Rush”.
     */
    private void testGetSimilarRatingsByFilter() {
        String targetRaterID = "65";
        int numSimilarRaters = 20;
        int minimalRaters = 5;
        String targetGenre = "Action";
        Filter filterCriteria = new GenreFilter(targetGenre);
        ArrayList<Rating> similarMovieRatings = getSimilarRatingsByFilter(targetRaterID, numSimilarRaters, minimalRaters, filterCriteria);
        System.out.println("# recs = " + similarMovieRatings.size());
        for (Rating r: similarMovieRatings) { 
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public static void main(String[] args) {
        FourthRatings fr = new FourthRatings();
        fr.testDotProduct();
    }
}











