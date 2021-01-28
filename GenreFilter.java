
/**
 * Write a description of GenreFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GenreFilter implements Filter {
    private String myGenre; 
    
    public GenreFilter(String targetGenre) {
        myGenre = targetGenre;
    }
    
    /**
     * Return true if the movie specified by movie id (@param id) falls into the target genre
     * Otherwise, return false
     */
    @Override
    public boolean satisfies(String id) {
       return MovieDatabase.getGenres(id).contains(myGenre);
    }
}
