package music;

public interface Song {

    public int getID();

    public boolean addArtist(int artistID);

    public boolean updateSong(String fieldName, String fieldValue);
}

/*
 * For iteration 1
public interface Song {

    public int getID();

    public boolean addArtist(int artistID);
}
*/