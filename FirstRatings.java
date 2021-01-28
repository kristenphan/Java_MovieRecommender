import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.nio.*;

/**
 * Process the movie and ratings data and answer questions about them
 */
public class FirstRatings {
    
    /**
     * Process every movie from the CSV file whose name is @param fileName, 
     * and return an ArrayList of type Movie with all of the movie data from the file
     * The header of the CSV file includes:
     * id (i.e. IMDB id), title, year, country, genre, director, minutes, poster (i.e. poster URL)
     * @throws IOException when the CSVParser object cannot be instantiated from a file with @param fileName
     */
    public ArrayList<Movie> loadMovies(String fileName) throws IOException {
        // create a new ArrayList of Movies called output
        ArrayList<Movie> output = new ArrayList<Movie>();
        
        // read csv file
        CSVParser parser = new CSVParser(new FileReader(fileName), CSVFormat.DEFAULT.withHeader());
        
        // add movies to output
        for (CSVRecord rec: parser) {
            String id = rec.get("id");
            String title = rec.get("title");
            String year = rec.get("year");
            String genres = rec.get("genre");
            String director = rec.get("director");
            String country = rec.get("country");
            String poster = rec.get("poster");
            int minutes = Integer.parseInt(rec.get("minutes"));
            Movie movie = new Movie (id, title, year, genres, director, country, poster, minutes);
            output.add(movie);
        }
        parser.close();
        
        return output;
    }
    
    /**
     * Test loadMovies() method
     * E.g. 
     * for fileName "ratedmovies_short.csv":
     * # movies = 5
     * # comedy = 1
     * # movies at 150+ min = 2
     * for fileName "ratedmoviesfull.csv":
     * # movies = 3143
     * # comedy = 960
     * # movies at 150+ min = 132
     * max # movies by any director = 23
     * directors with max # of movies = [Woody Allen]
     */
    private void testLoadMovies() {
        String fileName = "data/ratedmoviesfull.csv";
        
        try {
            ArrayList<Movie> movies = loadMovies(fileName);
            System.out.println("# movies = " + movies.size());
            //for (Movie movie: movies) {
            //    System.out.println(movie);
            //}
            int countComedy = 0;
            for (Movie movie: movies) {
                if (movie.getGenres().contains("Comedy")) {
                    countComedy++;
                }
            }
            System.out.println("# comedy = " + countComedy);
            int count150 = 0;
            for (Movie movie: movies) {
                if (movie.getMinutes() > 150) {
                    count150++;
                }
            }
            System.out.println("# movies at 150+ min = " + count150);
            HashMap<String, Integer> dirMovies = new HashMap<String, Integer>();
            for (Movie movie: movies) {
                String title = movie.getTitle();
                String[] directors = movie.getDirector().split(", ");
                for (String director: directors) {
                    if (dirMovies.containsKey(director)) {
                        dirMovies.put(director, dirMovies.get(director) + 1);
                    }
                    dirMovies.put(director, 1);
                }
            }
            
            // Determine the maximum number of movies by any director and the name of the director()
            HashMap<String, ArrayList<String>> dirMovieMap = new HashMap<String, ArrayList<String>>();
            for (Movie m: movies) {
                String dir = m.getDirector();
                for (String dirName: dir.split(", ")) {
                    if (dirMovieMap.containsKey(dirName)) {
                        dirMovieMap.get(dirName).add(m.getTitle());
                    }
                    else {
                        dirMovieMap.put(dirName, new ArrayList());
                        dirMovieMap.get(dirName).add(m.getTitle());
                    }
                }
            }
            int maxCount = 0;
            ArrayList<String> directors = new ArrayList<String>();
            for (String dirName: dirMovieMap.keySet()) {
                if (dirMovieMap.get(dirName).size() > maxCount) {
                    maxCount = dirMovieMap.get(dirName).size();
                    directors = new ArrayList<String>();
                    directors.add(dirName);
                }
                else if (dirMovieMap.get(dirName).size() == maxCount) {
                    directors.add(dirName);
                }
            }
            System.out.println("max # movies by any director = " + maxCount);
            System.out.println("directors with max # of movies = " + directors);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Read a CSV file containing the ratings of movies by different raters
     * and return an ArrayList of Raters - each Rater has a unique rater id and an ArrayList of ratings by that rater
     * The head of a rating CSV file includes:
     * rater_id, movie_id, rating, time
     * @throws IOException when the CSVParser object cannot be instantiated from a file with @param fileName
     */
    public ArrayList<EfficientRater> loadRaters(String fileName) throws IOException {
        // create a new HashMap to store the output: key = raterId, value = Rater object
        HashMap<String, EfficientRater> raterMap = new HashMap<String, EfficientRater>();
        
        // read csv file
        CSVParser parser = new CSVParser(new FileReader(fileName), CSVFormat.DEFAULT.withHeader());
        
        // add movies to output
        for (CSVRecord rec: parser) {
            String raterId = rec.get("rater_id");
            String movieId = rec.get("movie_id");
            String rating = rec.get("rating");
            // if raterMap is empty or Rater does not exist in raterMap, add it to raterMap
            if (raterMap.isEmpty() || !raterMap.containsKey(raterId)) {
                raterMap.put(raterId, new EfficientRater(raterId));
                raterMap.get(raterId).addRating(movieId, Double.parseDouble(rating));
            }
            // if Rater already exists in raterMap, update the Rater to reflect the current movie
            else {
                raterMap.get(raterId).addRating(movieId, Double.parseDouble(rating));
            }
        }
        
        // convert raterMap to an ArrayList
        ArrayList<EfficientRater> raterArr = new ArrayList<EfficientRater>();
        for (EfficientRater r: raterMap.values()) {
            raterArr.add(r);
        }
        
        return raterArr;
    }
    
    /*
     * Test loadRaters() method
     * e.g 
     * for file "ratings_shorts.csv"
     * # of raters = 5
     * # of ratings for rater_id 2 = 3
     * max # ratings by any rater = = 3, 
     * raterIds of raters with max # ratings = 2
     * # ratings for movieId 1798709 = 4
     * # different movies rated by all raters = 4;
     * for file "ratings.csv"
     * # of raters = 1048
     */
    public void testLoadRaters() {
        // Load rater data
        String fileName = "data/ratings_short.csv";
        
        try {
            ArrayList<EfficientRater> raters = loadRaters(fileName);
            
            // Print the number of raters
            System.out.println("# raters = " + raters.size());
            
            // Find the number of ratings for a particular rater
            int raterId = 193;
            for (EfficientRater r: raters) {
                if (Integer.valueOf(r.getID()) == raterId) {
                    System.out.println("# ratings for raterId " + raterId + " = " + r.numRatings());
                    break;
                }
            }
            
            // Find the max number of ratings by any rater
            int maxNumRatings = 0;
            ArrayList<String> ids = new ArrayList<String>();
            for (EfficientRater r: raters) {
                if (r.numRatings() > maxNumRatings) {
                    maxNumRatings = r.numRatings();
                    ids = new ArrayList<String>();
                    ids.add(r.getID());
                }
                else if (r.numRatings() == maxNumRatings) {
                    ids.add(r.getID());
                }
                else {
                    continue;
                }
            }
            System.out.println("max # ratings by any rater = " + maxNumRatings);
            System.out.println("raterIds of raters with max # ratings = " + ids.toString());
        
            // Find the number of ratings a particular movie has
            String movieId = "1798709";
            int ratingCount = 0;
            for (EfficientRater r: raters) {
                if (r.hasRating(movieId)) {
                    ratingCount++;
                }
            }
            System.out.println("# ratings for movieId " + movieId + " = " + ratingCount);
            
            // Determine how many different movies have been rated by all raters
            ArrayList<String> movieIds = new ArrayList<String>();
            for (EfficientRater r: raters) {
                ArrayList<String> currMovieIdList = r.getItemsRated();
                for (String currId: currMovieIdList) {
                   if (!movieIds.contains(currId)) {
                       movieIds.add(currId);
                   }
                }
            }
            System.out.println("# different movies rated by all raters = " + String.valueOf(movieIds.size()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        FirstRatings fr = new FirstRatings();
        fr.testLoadRaters();
    }
}
