/**
 * Return all movies which were produced on or after @param year
 */
public class YearAfterFilter implements Filter {
	private int targetYear;
	
	public YearAfterFilter(int year) {
		targetYear = year;
	}
	
	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getYear(id) >= targetYear;
	}

}
