
/**
 * Filter movies by their length
 */
public class MinutesFilter implements Filter {
    private int minLength;
    private int maxLength;
    
    public MinutesFilter(int min, int max) {
        minLength = min;
        maxLength = max;
    }
    
    /**
     * Return true if the length of the movie specified by @param id is between minLength and maxLength
     */
    @Override
    public boolean satisfies(String id) {
        return (MovieDatabase.getMinutes(id) >= minLength && MovieDatabase.getMinutes(id) <= maxLength);
    }
}
