import java.util.*;

/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerWithFilters {
    public void printAverageRatings() {
        // read rating data and print the # of raters
        String ratingFile = "data/ratings.csv";
        ThirdRatings tr = new ThirdRatings(ratingFile);
        System.out.println("read data for " + tr.getRaterSize() + " raters");
                           
        // set up the movie database and print the # of movies in the database
        String movieFile = "data/ratedmoviesfull.csv";
        MovieDatabase.initialize(movieFile);
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        
        // get an ArrayList of type Rating for movies with a minimal number of raters
        // print out the number of movies returned
        int minimalRaters = 35;
        ArrayList<Rating> avgRatings = tr.getAverageRatings(minimalRaters);
        System.out.println("found " + avgRatings.size() + " movies");
        
        // sort the movies by ratings (from lowest to highest) and print them 
        Collections.sort(avgRatings);
        for (Rating r: avgRatings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfter() {
        // read rating data
        String ratingFile = "data/ratings.csv";
        ThirdRatings tr = new ThirdRatings(ratingFile);
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        
        // create a YearAfterFilter
        int targetYear = 2000;
        YearAfterFilter yf = new YearAfterFilter(targetYear);
        
        // call getAverageRatingsByFilter to get an ArrayList of type Rating of all movies 
        // that have a specified number of minimal ratings and came out in the specified year or later
        int minimalRaters = 20;
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(minimalRaters, yf); 
        
        // print the # of movies found
        System.out.println("found " + filteredRatings.size() + " movies");
        
        // sort the movies by rating from lowest to highest
        Collections.sort(filteredRatings);
        
        // for each movie, print its rating, its year, and its title
        for (Rating r: filteredRatings) {
            System.out.println(r.getValue() + " " + 
                               MovieDatabase.getYear(r.getItem()) + " " +
                               MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public void printAverageRatingsByGenre() {
        // read rating data
        String ratingFile = "data/ratings.csv";
        ThirdRatings tr = new ThirdRatings(ratingFile);
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        
        // create a GenreFilter
        String targetGenre = "Comedy";
        GenreFilter gf = new GenreFilter(targetGenre);
        
        // call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies
        // that have a specified number of minimal ratings and include a specified genre
        int minimalRaters = 20;
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(minimalRaters, gf);
        
        // print the # of movies found
        System.out.println("found " + filteredRatings.size() + " movies");
        
        // sort the movies by rating from lowest to highest
        Collections.sort(filteredRatings);
        
        // for each movie, print its rating, its year, and its title
        for (Rating r: filteredRatings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()) + "\n\t" + 
                               MovieDatabase.getGenres(r.getItem()));
        }
    }
    
    public void printAverageRatingsByMinutes() {
        // read rating data
        String ratingFile = "data/ratings.csv";
        ThirdRatings tr = new ThirdRatings(ratingFile);
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        
        // create a MinutesFilter
        int min = 105;
        int max = 135;
        MinutesFilter mf = new MinutesFilter(min, max);
        
        // call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies
        // that have a specified number of minimal ratings and include a specified genre
        int minimalRaters = 5;
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(minimalRaters, mf);
        
        // print the # of movies found
        System.out.println("found " + filteredRatings.size() + " movies");
        
        // sort the movies by rating from lowest to highest
        Collections.sort(filteredRatings);
        
        // for each movie, print its rating, its year, and its title
        //for (Rating r: filteredRatings) {
        //    System.out.println(r.getValue() + " Time: " + MovieDatabase.getMinutes(r.getItem()) + " " +  
        //                       MovieDatabase.getTitle(r.getItem()));
        //}
    }
    
    public void printAverageRatingsByDirectors() {
        // read rating data
        String ratingFile = "data/ratings.csv";
        ThirdRatings tr = new ThirdRatings(ratingFile);
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        
        // create a DirectorsFilter
        String targetDirectors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        DirectorsFilter df = new DirectorsFilter(targetDirectors);
        
        // call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies
        // that have a specified number of minimal ratings and include a specified genre
        int minimalRaters = 4;
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(minimalRaters, df);
        
        // print the # of movies found
        System.out.println("found " + filteredRatings.size() + " movies");
        
        // sort the movies by rating from lowest to highest
        Collections.sort(filteredRatings);
        
        // for each movie, print its rating, its year, and its title
        for (Rating r: filteredRatings) {
            System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem()) + "\n\t " +  
                               MovieDatabase.getDirector(r.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        // read rating data
        String ratingFile = "data/ratings.csv";
        ThirdRatings tr = new ThirdRatings(ratingFile);
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        
        // create an AllFilters that is comprised of a YearAfterFilter and a GenreFilter
        int targetYear = 1990; 
        YearAfterFilter yf = new YearAfterFilter(targetYear);
        String targetGenre = "Drama";
        GenreFilter gf = new GenreFilter(targetGenre);
        AllFilters af = new AllFilters();
        af.addFilter(yf);
        af.addFilter(gf);
        
        // call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies
        // that have a specified number of minimal ratings and include a specified genre
        int minimalRaters = 8;
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(minimalRaters, af);
        
        // print the # of movies found
        System.out.println(filteredRatings.size() + " movie(s) matched");
        
        // sort the movies by rating from lowest to highest
        Collections.sort(filteredRatings);
        
        // for each movie, print its rating, its year, and its title
        //for (Rating r: filteredRatings) {
        //    System.out.println(r.getValue() + " " + 
        //                       MovieDatabase.getYear(r.getItem()) + " " + 
        //                       MovieDatabase.getTitle(r.getItem()) + "\n\t " +  
        //                       MovieDatabase.getGenres(r.getItem()));
        //}
    }
    
    public void printAverageRatingsByDirectorsAndMinutes() {
        // read rating data
        String ratingFile = "data/ratings.csv";
        ThirdRatings tr = new ThirdRatings(ratingFile);
        System.out.println("read data for " + tr.getRaterSize() + " raters");
        
        // create an AllFilters that is comprised of a DirectorsFilter and a MinutesFilter
        String targetDirectors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        DirectorsFilter df = new DirectorsFilter(targetDirectors);
        int min = 90;
        int max = 180;
        MinutesFilter mf = new MinutesFilter(min, max);
        AllFilters af = new AllFilters();
        af.addFilter(df);
        af.addFilter(mf);
        
        // call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies
        // that have a specified number of minimal ratings and include a specified genre
        int minimalRaters = 3;
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(minimalRaters, af);
        
        // print the # of movies found
        System.out.println(filteredRatings.size() + " movie(s) matched");
        
        // sort the movies by rating from lowest to highest
        Collections.sort(filteredRatings);
        
        // for each movie, print its rating, its year, and its title
        //for (Rating r: filteredRatings) {
        //    System.out.println(r.getValue() + " Time: " + 
        //                       MovieDatabase.getMinutes(r.getItem()) + " " + 
        //                       MovieDatabase.getTitle(r.getItem()) + "\n\t " +  
        //                       MovieDatabase.getDirector(r.getItem()));
        //}
    }
    
    public static void main(String[] args) {
        MovieRunnerWithFilters runner = new MovieRunnerWithFilters();
        runner.printAverageRatingsByDirectorsAndMinutes();
    }
}
