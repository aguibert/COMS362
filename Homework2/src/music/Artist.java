package music;

import java.util.Map;

public interface Artist {

    public int getID();

    public boolean addSong(int songID);

    public boolean updateArtist(Map<String, Object> fieldToValue);

    public boolean addConcert(String concert);

    public boolean deleteConcert(String concertName);
}
