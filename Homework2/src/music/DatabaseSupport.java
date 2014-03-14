package music;

import java.util.List;

public interface DatabaseSupport {

    public boolean putSong(Song s);

    public Song getSong(int songID);

    public List<Song> searchBySongName(String name);

    public List<Song> searchSongByGenre(String genre);

    public List<Song> searchSongByLabel(String label);

    public Song deleteSong(int songID);

    public boolean putArtist(Artist a);

    public Artist getArtist(int artistID);

    public Artist deleteArtist(int artistID);

    public List<Artist> searchArtistByArtistName(String artistName);
}
