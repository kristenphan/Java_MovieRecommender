import java.util.*;
import java.lang.System.*;

/**
 * Implement the interface Recommender 
 */
public class RecommendationRunner implements Recommender {
    /**
     * Return an ArrayList of type String of movie IDs of the movies randomly chosen from the MovieDatabase. 
     * These movies will be presented to a user for rating, and the results will be used to profile said user.
     * Note that all movie IDs chosen should be unique so that a user will be asked to rate all unique movies
     * This method will be called by RecommenderRunner.getItemsToRate() method
     */
    public static ArrayList<String> getRandomMovieIDs(int numSampleMovies) {
        // get all movie IDs in MovieDatabase using a TrueFilter
        ArrayList<String> allMovieIDs = MovieDatabase.filterBy(new TrueFilter());
        // create randomMovieIDs to store the IDs of randomly select movies from MovieDatabase
        ArrayList<String> randomMovieIDs = new ArrayList<String>();
        // create a random object
        Random rand = new Random();
        // randomly select @param numSampleMovies movies from MovieDatabase and add them to randomMoviesIDs
        // all movie IDs in movieIDs should be unique
        for (int i = 0; i < numSampleMovies; i++) {
            String newMovieID = allMovieIDs.get(rand.nextInt(allMovieIDs.size()));
            while (randomMovieIDs.contains(newMovieID)) { // if the movie has been selected previously, randomly choose a different movie
                newMovieID = allMovieIDs.get(rand.nextInt(allMovieIDs.size()));
            }
            randomMovieIDs.add(newMovieID);
        }
        
        return randomMovieIDs; 
    }
    
    /**
     * This method returns a list of movie IDs that will be used to look up 
     * the movies in the MovieDatabase and present them to users to rate. 
     * The movies to be rated will be chosen randomly from the MovieDatabase.
     *
     * The ratings for these movies will make the profile for a new Rater 
     * that will be used to compare to for finding recommendations.
     */
    public ArrayList<String> getItemsToRate() {
        // set the the number of movies to be rated by a user for profiling purposes
        int numSampleMovies = 10;
        // store the IDs of all movies to be rated
        ArrayList<String> movieIDs = getRandomMovieIDs(numSampleMovies);
        
        return movieIDs;
    }

    /**
     * This method returns nothing, but prints out an HTML table of the 
     * movies recommended for the given rater.
     * 
     * The HTML printed will be displayed on a web page, so the number you
     * choose to display may affect how long the page takes to load.  For 
     * example, you may want to limit the number printed to only the top 
     * 20-50 movies recommended or to movies not rater by the given rater.
     * 
     * You may also include CSS styling for your table using the &lt;style&gt;
     * tag before you print the table.  There are no restrictions on which 
     * movies you print, what order you print them in, or what information
     * you include about each movie. 
     * 
     * @param webRaterID the ID of a new Rater that has been already added to 
     *        the RaterDatabase with ratings for the movies returned by the 
     *        method getItemsToRate
     */
    public void printRecommendationsFor (String webRaterID) {
        // set the number of recommendations to be displayed for the target rater with @param webRaterID
        int numRecommendations = 20; 
        
        // create a FourthRatings object
        FourthRatings fr = new FourthRatings();
        
        // initialize the MovieDatabase and Raterdatabase
        MovieDatabase.initialize("data/ratedmoviesfull.csv");
        RaterDatabase.initialize("data/ratings.csv");
        
        // get the recommended movies for the target rater 
        int numSimilarRaters = 10; // the number of raters whose movie ratings will be used to derive a list of recommended movies for the target rater
        int minimalRaters = 5; // the minimum number of ratings each of the recommended movies should have
        ArrayList<Rating> similarRatings = fr.getSimilarRatings(webRaterID, numSimilarRaters, minimalRaters); // each Rating object in similarRating consists of movieID and similarityRating calculated for that movie based on the target rater's interests
        
        // if no movie was recommended, print an error message
        if (similarRatings.size() == 0) {
            System.out.println("No matching movies were found");
            System.exit(0);
        }
        
        // get a list of all movies rated by the target rater 
        Rater targetRater = RaterDatabase.getRater(webRaterID);
        
        // print the top numRecommendations except for the movies the target rater already rated
        int count = 0;
        System.out.println("<html>" + 
                                "<head>"+
                                    "<style>" + 
                                        "#movies{font-family: Arial, Helvetica, sans-serif; border-collapse: collapse; width: 100%;}" +
                                        "#movies td, #customers th {border: 1px solid #ddd; padding: 8px;}" +        
                                        "#movies tr:nth-child(even){background-color: #f2f2f2;}" +
                                        "#movies tr:hover {background-color: #ddd;}" + 
                                        "#movies th {padding-top: 12px; padding-bottom: 12px; text-align: left; background-color: #4CAF50; color: white;}" + 
                                    "</style>" + 
                                "</head>" + 
                                "<body>" + 
                                    "<table id=\"movies\">" + 
                                        "<tr>" + 
                                            "<th>#</th>" + 
                                            "<th>Movie Title</th>" + 
                                            "<th>Year</th>" + 
                                            "<th>Genres</th>" + 
                                            "<th>Similarity Rating</th>" + 
                                        "</tr>");
        for (int i = 0; i < similarRatings.size(); i++) {
            if (count < numRecommendations) {
                String currMovieID = similarRatings.get(i).getItem();
                if(!targetRater.hasRating(currMovieID)) {
                    // print this movie as part of a html table...
                    count++;
                    System.out.println("<tr><td>" + count + "</td>" + 
                                           "<td>" + MovieDatabase.getTitle(currMovieID) + "</td>" + 
                                           "<td>" + MovieDatabase.getYear(currMovieID) + "</td>" + 
                                           "<td>" + MovieDatabase.getGenres(currMovieID) + "</td>" + 
                                           "<td>" + Math.round(similarRatings.get(i).getValue()) + "</td>");
                }    
            }
            else {
                break;
            }
        }
        
        System.out.println("</table></body></html>");
    }
}