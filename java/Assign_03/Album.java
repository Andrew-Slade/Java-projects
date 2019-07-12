

public class Album {

	// declare data members
	String albumName, artistName, genre, thumbnailUrl;

	// generate constructor
	public Album(String albumName, String artistName, String genre, String url) {
		super();
		this.albumName = albumName;
		this.artistName = artistName;
		this.genre = genre;
		this.thumbnailUrl = url;
	}

	// accessor methods to get values of each of data members
	public String getAlbumName() {
		return albumName;
	}

	public String getArtistName() {
		return artistName;
	}

	public String getGenre() {
		return genre;
	}
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	// overiding toString() method
	@Override
	public String toString() {
		return albumName + ':' + artistName + ':' + genre;
	}

}
