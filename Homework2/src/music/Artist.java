package music;


public interface Artist {

    public int getID();

    public boolean addSong(int songID);

    public boolean updateArtist(String fieldName, String fieldValue);

    public boolean addConcert(String concert);

    public boolean deleteConcert(String concertName);
}

// For iteration 1
/*
public interface Artist {

    public int getID();

    public boolean addSong(int songID);

    public boolean addConcert(String concert);

    public boolean deleteConcert(String concertName);
}
*/