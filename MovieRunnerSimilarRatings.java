import java.util.*;

/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerSimilarRatings {
    /**
     * Print the average ratings for all movies which have at least @param minimalRaters by any of the raters in RaterDatabase
     */
    public void printAverageRatings() {
        // Initialize the RaterDatabase
        String ratingFile = "data/ratings_short.csv";
        RaterDatabase.initialize(ratingFile);
        System.out.println("read data for " + RaterDatabase.size() + " raters");
                           
        // Initialize the MovieDatabase
        String movieFile = "data/ratedmovies_short.csv";
        MovieDatabase.initialize(movieFile);
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        // Create a FourthRatings object
        FourthRatings fr = new FourthRatings();
        
        // Calculate the average ratings for all movies in MovieDatabase with at least @param minimalRaters by any of the raters in RaterDatabase
        int minimalRaters = 35;
        ArrayList<Rating> movieAvgRatings = fr.getAverageRatings(minimalRaters);
        System.out.println("found " + movieAvgRatings.size() + " movies");
        
        // Sort the movies by their average ratings (from lowest to highest) and print them 
        Collections.sort(movieAvgRatings);
        for (Rating r: movieAvgRatings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem())); // E.g. "movieAvgRating movieName" 
        }
    }
    
    /**
     * Print a list of movies and their average ratings for all movies in MovieDatabase which have at least @param minimalRaters
     * by any of the raters in RaterDatabase and satisfy all filter criterias @param af
     */
    public void printAverageRatingsByYearAfterAndGenre() {
        // Initialize the RaterDatabase
        String ratingFile = "data/ratings.csv";
        RaterDatabase.initialize(ratingFile);
        System.out.println("read data for " + RaterDatabase.size() + " raters");
                           
        // Initialize the MovieDatabase
        String movieFile = "data/ratedmoviesfull.csv";
        MovieDatabase.initialize(movieFile);
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        // Create an AllFilters that is comprised of a YearAfterFilter and a GenreFilter
        int targetYear = 1990; 
        YearAfterFilter yf = new YearAfterFilter(targetYear);
        String targetGenre = "Drama";
        GenreFilter gf = new GenreFilter(targetGenre);
        AllFilters af = new AllFilters();
        af.addFilter(yf);
        af.addFilter(gf);
        
        // Create a FourthRatings object
        FourthRatings fr = new FourthRatings();
        
        // Call getAverageRatingsByFilter() to get an ArrayList of type Rating of all movies which have at least @param minimalRaters by any of the raters in RaterDatabase
        // and meet all filter criteria @param af
        int minimalRaters = 8;
        ArrayList<Rating> filteredMovieRatings = fr.getAverageRatingsByFilter(minimalRaters, af);     
        System.out.println(filteredMovieRatings.size() + " movie(s) matched"); // // Print the # of movies found
        
        // Sort the movies by their average rating from lowest to highest
        Collections.sort(filteredMovieRatings);
        
        // Print the result e.g. "movieAverageRating movieYear movieTitle movieGenre"
        for (Rating r: filteredMovieRatings) {
            System.out.println(r.getValue() + " " + 
                               MovieDatabase.getYear(r.getItem()) + " " + 
                               MovieDatabase.getTitle(r.getItem()) + "\n\t " +  
                               MovieDatabase.getGenres(r.getItem()));
        }
    }
    
    /**
     * Print a list of recommended movies tailored for a particular rater with @param targetRaterID, has at least @param minimalRaters, 
     * and chosen based on @param numSimilarRaters who share similar interests with the target rater from all movies in MovieDatabase
     * Test case:
     * input: files "ratedmoviesfull.csv" and "ratings.csv", targetGenre = "Action",  targetRaterID = "65", minimalRaters = 5, numSimilarRaters = 20
     * output: the movie returned with the top rated average is “Rush”.
     */
    public void printSimilarRatings() {
        // create a new FourthRatings object
        FourthRatings fr = new FourthRatings();
        
        // Get a list of recommendations as ArrayList of type Rating storing movieIDs and their weighted average ratings
        String targetRaterID = "71"; // tailors the recommendations based on the ratings of raters who share similar interests with the target rater with targetRaterID
        int numSimilarRaters = 20; // influences the calcualation of movie weighted averages 
        int minimalRaters = 5; // filters out movies with too few ratings
        ArrayList<Rating> recommendations = fr.getSimilarRatings(targetRaterID, numSimilarRaters, minimalRaters); // recommendations sorted by weighted average ratings from highest to lowest
        
        // print the recommendations
        System.out.println("# recs = " + recommendations.size());
        for (Rating rec: recommendations) {
            System.out.println(MovieDatabase.getTitle(rec.getItem()) + " " + rec.getValue());
        }
    }
    
    /**
     * Print a list of recommended movies which falls into @param targetGenere, 
     * tailored for a particular rater with @param targetRaterID, has at least @param minimalRaters, 
     * and chosen based on @param numSimilarRaters who share similar interests with the target rater
     * Test case:
     * input: 
     * file ratedmoviesful.csv and ratings.csv, 
     * targetGenre "Action";
     * raterID = "65", 
     * minimalRaters "5", 
     * numSimilarRaters = "20";
     * output:
     * the movie return with the top rated average is "Rush"
     */
    public void printSimilarRatingsByGenre() {
        // create a new FourthRatings object
        FourthRatings fr = new FourthRatings();
        
        // create a GenreFilter
        String targetGenre = "Mystery";
        GenreFilter gf = new GenreFilter(targetGenre);
        
        // Get a list of recommendations as ArrayList of type Rating storing movieIDs and their weighted average ratings
        String targetRaterID = "964"; // tailors the recommendations based on the ratings of raters who share similar interests with the target rater with targetRaterID
        int minimalRaters = 5; // filters out movies with too few ratings
        int numSimilarRaters = 20; // influences the calcualation of movie weighted averages 
        // recommendations are filtered by genre and sorted by weighted average ratings from highest to lowest 
        ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(targetRaterID, numSimilarRaters, minimalRaters, gf);
  
        // for each recommendation, print the title, the weighted average rating, the genres 
        for (Rating rec: recommendations) { 
            System.out.println(MovieDatabase.getTitle(rec.getItem()) + " " + rec.getValue() + "\n" + 
                                                      MovieDatabase.getGenres(rec.getItem()));
        }
    }
    
    /**
     * Print a list of recommended movies which were directed by at least of the @param targetDirectors, 
     * tailored for a particular rater with @param targetRaterID, has at least @param minimalRaters, 
     * and chosen based on @param numSimilarRaters who share similar interests with the target rater
     * Test case:
     * input: 
     * using file ratedmoviesfull.csv and ratings.csv, 
     * targetDirectors = "Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone", 
     * targetRaterID = "1034", 
     * minimalRaters = 3,
     * numSimilarRaters = 10;
     * output:  
     * the movie returned with the top rated average is “Unforgiven”
     */
    public void printSimilarRatingsByDirector() {
        // create a new FourthRatings object
        FourthRatings fr = new FourthRatings();
        
        // create a DirectorsFilter
        String targetDirectors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        DirectorsFilter df = new DirectorsFilter(targetDirectors);
        
        // Get a list of recommendations as ArrayList of type Rating storing movieIDs and their weighted average ratings
        String targetRaterID = "120"; // tailors the recommendations based on the ratings of raters who share similar interests with the target rater with targetRaterID
        int minimalRaters = 2; // filters out movies with too few ratings
        int numSimilarRaters = 10; // influences the calcualation of movie weighted averages 
        // recommendations are filtered by genre and sorted by weighted average ratings from highest to lowest 
        ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(targetRaterID, numSimilarRaters, minimalRaters, df);
  
        // for each recommendation, print the movie and its similarity rating on one line and its directors on a separate line below it
        for (Rating rec: recommendations) { 
            System.out.println(MovieDatabase.getTitle(rec.getItem()) + " " + rec.getValue() + "\n" + 
                                                      MovieDatabase.getDirector(rec.getItem()));
        }
    }
    
    /**
     * Print a list of recommended movies which fall into @param targetGenre and whose lengths are between @param minLen and @param maxLen (inclusive), 
     * tailored for a particular rater with @param targetRaterID, has at least @param minimalRaters, 
     * and chosen based on @param numSimilarRaters who share similar interests with the target rater
     * Test case:
     * input: 
     * using file ratedmoviesfull.csv and ratings.csv, 
     * targetGenre = "Adventure";
     * minLen = 100 (inclusive);
     * maxLen = 200 (inclusive);
     * targetRaterID = "65", 
     * minimalRaters = 5,
     * numSimilarRaters = 10;
     * output:  
     * the movie returned with the top rated average is “Interstellar”.
     */
    public void printSimilarRatingsByGenreAndMinutes() {
        // create a new FourthRatings object
        FourthRatings fr = new FourthRatings();
        
        // create a AllFilters comprised of a GenreFilter and a MinutesFilter
        String targetGenre = "Drama";
        GenreFilter gf = new GenreFilter(targetGenre);
        int minLen = 80;
        int maxLen = 160;
        MinutesFilter mf = new MinutesFilter(minLen, maxLen);
        AllFilters af = new AllFilters();
        af.addFilter(gf);
        af.addFilter(mf);
        
        // Get a list of recommendations as ArrayList of type Rating storing movieIDs and their weighted average ratings
        String targetRaterID = "168"; // tailors the recommendations based on the ratings of raters who share similar interests with the target rater with targetRaterID
        int minimalRaters = 3; // filters out movies with too few ratings
        int numSimilarRaters = 10; // influences the calcualation of movie weighted averages 
        // recommendations are filtered by genre and sorted by weighted average ratings from highest to lowest 
        ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(targetRaterID, numSimilarRaters, minimalRaters, af);
  
        // for each recommendation, print the movie, its minutes, and its similarity rating on one line and its genres on a separate line below it.
        for (Rating rec: recommendations) { 
            System.out.println(MovieDatabase.getTitle(rec.getItem()) + " " + MovieDatabase.getMinutes(rec.getItem()) + " " + rec.getValue() + "\n" + 
                                                      MovieDatabase.getGenres(rec.getItem()));
        }
    }
    
    /**
     * Print a list of recommended movies which were produced on or after @param targetYear and whose lengths are between @param minLen and @param maxLen (exclusive), 
     * tailored for a particular rater with @param targetRaterID, has at least @param minimalRaters, 
     * and chosen based on @param numSimilarRaters who share similar interests with the target rater
     * Test case:
     * input: 
     * using file ratedmoviesfull.csv and ratings.csv, 
     * targetYear = 2000;
     * minLen = 100 (inclusive);
     * maxLen = 200 (inclusive);
     * targetRaterID = "65", 
     * minimalRaters = 5,
     * numSimilarRaters = 10;
     * output:  
     * the movie returned with the top rated average is “The Grand Budapest Hotel”.
     */
    public void printSimilarRatingsByYearAfterAndMinutes() {
        // create a new FourthRatings object
        FourthRatings fr = new FourthRatings();
        
        // create a AllFilters comprised of a YearAfterFilter and a MinutesFilter
        int targetYear = 1975;
        YearAfterFilter yaf = new YearAfterFilter(targetYear);
        int minLen = 70;
        int maxLen = 200;
        MinutesFilter mf = new MinutesFilter(minLen, maxLen);
        AllFilters af = new AllFilters();
        af.addFilter(yaf);
        af.addFilter(mf);
        
        // Get a list of recommendations as ArrayList of type Rating storing movieIDs and their weighted average ratings
        String targetRaterID = "314"; // tailors the recommendations based on the ratings of raters who share similar interests with the target rater with targetRaterID
        int minimalRaters = 5; // filters out movies with too few ratings
        int numSimilarRaters = 10; // influences the calcualation of movie weighted averages 
        // recommendations are filtered by genre and sorted by weighted average ratings from highest to lowest 
        ArrayList<Rating> recommendations = fr.getSimilarRatingsByFilter(targetRaterID, numSimilarRaters, minimalRaters, af);
  
        // for each recommendation, print the movie, its year, its minutes, and its similarity rating on one line
        for (Rating rec: recommendations) { 
            System.out.println(MovieDatabase.getTitle(rec.getItem()) + " " + MovieDatabase.getYear(rec.getItem()) + " " + MovieDatabase.getMinutes(rec.getItem()) + " " + rec.getValue());
        }
    }
    
    public static void main(String[] args) {
        MovieRunnerSimilarRatings runner = new MovieRunnerSimilarRatings();
        runner.printSimilarRatingsByYearAfterAndMinutes();
    }
}



















































