package hw4;

import java.util.List;
import java.util.Set;

public interface LibraryController {

	/** Iteration 1 **/
	public int login(String username, String pass);
	public boolean addStory(String title, int sid, List<String> name, String date, List<String> topics, String text, List<String> reviews);
	public boolean deleteStory(int sid);
	public boolean addReview(int sid, int pid, String review);
	public boolean removeReview(int sid, int pid);
	public boolean addTopic(int id, String topic);
	
	/** Iteration 2 **/
	public Set<Story> purchaseCart(int pid, int credit);
	public Set<Story> searchByTitle(String title);
	public Story searchByID(int sid);
	public Set<Story> searchByAuthor(String author);
	public Set<Story> searchByTopic(String topic);
}
