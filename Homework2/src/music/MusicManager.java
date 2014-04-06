package music;

import java.util.List;

public interface MusicManager {

    /* Song operations */
    public Song createSong(List<Artist> artists, String name, String genre, String lable, byte[] audio);

    public Song updateSong(int songID, String fieldName, String fieldValue);

    public Song deleteSong(int songID);

    public List<Song> searchSongBySongName(String songName);

    public List<Song> searchSongByGenre(String genre);

    public List<Song> searchSongByLabel(String label);

    public Song searchSongByID(int songID);

    /* Artist operations */
    public Artist createArtist(String artistName, List<Song> songs, List<String> concerts);

    public Artist updateArtist(int artistID, String fieldName, String fieldValue);

    public boolean addSongToArtist(int songID, int artistID);

    public boolean addConcertToArtist(int artistID, String concert);

    public Artist deleteArtist(int artistID);

    public boolean deleteConcert(int artistID, String concertName);

    public List<Artist> searchArtistByArtistName(String artistName);
}
