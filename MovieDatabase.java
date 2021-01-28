import java.util.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;
import java.io.*;

/**
 * Store movie information in a HashMap for fast lookup of movie info given a movie ID
 * All methods and fields in the class are static
 */
public class MovieDatabase {
    // Map a movie ID string to a Movie object with all movie info
    private static HashMap<String, Movie> ourMovies; 
    
    /**
     * Initialize the movie database using the movie file name (@param movieFile)
     */
    public static void initialize(String movieFile) {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies(movieFile);
        }
    }
    
    /**
     * Safety check to initialize the movie database if no movie file has been loaded
     */
    private static void initialize() {
        if (ourMovies == null) {
            ourMovies = new HashMap<String,Movie>();
            loadMovies("data/ratedmoviesfull.csv");
        }
    }   

    /**
     * Builds the HashMap ourMovies. This method will be called from intialize(movieFile) and initialize() method
     */
    private static void loadMovies(String fileName) {
        FirstRatings fr = new FirstRatings();
        try {
            ArrayList<Movie> list = fr.loadMovies(fileName);
            for (Movie m : list) {
                ourMovies.put(m.getID(), m);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return true if a movie specified by movie id @param id is in the database. 
     * Otherwise, return false
     */
    public static boolean containsID(String id) {
        initialize();
        return ourMovies.containsKey(id);
    }

    /**
     * Return the year of the movie specified by movie id @param id
     */
    public static int getYear(String id) {
        initialize();
        return ourMovies.get(id).getYear();
    }

    /**
     * Return the genre(s) of the movie specified by movie id @param id
     */
    public static String getGenres(String id) {
        initialize();
        return ourMovies.get(id).getGenres();
    }

    /**
     * Return the title of the movie specified by movie id @param id
     */
    public static String getTitle(String id) {
        initialize();
        return ourMovies.get(id).getTitle();
    }

    /**
     * Return a Movie object of the movie specified by movie id @param id
     */
    public static Movie getMovie(String id) {
        initialize();
        return ourMovies.get(id);
    }

    /**
     * Return the poster URL of the movie specified by movie id @param id
     */
    public static String getPoster(String id) {
        initialize();
        return ourMovies.get(id).getPoster();
    }

    /**
     * Return the duration in minutes of the movie specified by movie id @param id
     */
    public static int getMinutes(String id) {
        initialize();
        return ourMovies.get(id).getMinutes();
    }

    /**
     * Return the country of the movie specified by movie id @param id
     */
    public static String getCountry(String id) {
        initialize();
        return ourMovies.get(id).getCountry();
    }

    /**
     * Return the name of the director of the movie specified by movie id @param id
     */
    public static String getDirector(String id) {
        initialize();
        return ourMovies.get(id).getDirector();
    }

    /**
     * Return the number of movies in the database
     */
    public static int size() {
        return ourMovies.size();
    }

    /**
     * Return an ArrayList of type String of movie IDs that match the filtering criteria
     */
    public static ArrayList<String> filterBy(Filter f) {
        initialize();
        ArrayList<String> list = new ArrayList<String>();
        for(String id : ourMovies.keySet()) {
            if (f.satisfies(id)) {
                list.add(id);
            }
        }
        
        return list;
    }

    public static void main(String[] args) {
        MovieDatabase.initialize();
        System.out.println("size = " + MovieDatabase.size());
        System.out.println("size again = " + MovieDatabase.size());
        System.out.println("trying to read new data file");
        MovieDatabase.initialize("data/ratedmovies_short.csv");
        System.out.println("size = " + MovieDatabase.size());
        System.out.println("size again = " + MovieDatabase.size());
    }
}
