package music;

import java.util.Map;

public interface Song {

    public int getID();

    public boolean addArtist(int artistID);

    public boolean updateSong(Map<String, Object> fieldToValue);
}
