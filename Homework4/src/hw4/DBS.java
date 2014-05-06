package hw4;

import java.util.Set;

public interface DBS {
 
	/** Iteration 1 **/
	public int login(String username, String pass);
	public boolean putStory(Story s);
	public Story getStory(int sid);
	public boolean removeStory(int sid);
	
	/** Iteration 2 **/
	public Set<Story> getStoriesByTitle(String title);
	public Set<Story> getStoriesByAuthor(String author);
	public Set<Story> searchByTopic(String topic);
}
