import java.util.*;

/**
 * Filter movies by directors
 */
public class DirectorsFilter implements Filter {
    String[] targetDirectors;
    
    /**
     * Initialize the filter
     * @param directors is a list of target directors separated by comma e.g. "Charles Chaplin,Michael Mann,Clint Eastwood"
     */
    public DirectorsFilter(String directors) {
        targetDirectors = directors.split(",");
    }
    
    /**
     * Return true if the movie was directed by at least one of the targetDirectors
     */
    public boolean satisfies(String id) {
        String actualDirectors = MovieDatabase.getDirector(id);
        
        for (String dir: targetDirectors) {
            if (actualDirectors.contains(dir)) {
                return true;
            }
        }
        
        return false;
    }
}
