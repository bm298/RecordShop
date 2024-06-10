package com.NC_RecordShop.RecordShop.Service;

import com.NC_RecordShop.RecordShop.Model.Genre;
import com.NC_RecordShop.RecordShop.Model.RecordData;

import java.util.List;

public interface RecordManagerService {
    List<RecordData> getAllAlbums();
    RecordData getAlbumById(long id);
    RecordData postAlbum(RecordData recordData);
    RecordData updateAlbum(long id, RecordData recordData);
    String deleteAlbum(long id);

    List<RecordData> getAlbumsByArtist(String artist);
    List<RecordData> getAlbumsByReleaseYear(long releaseYear);
    List<RecordData> getAlbumsByGenre(Genre genre);
    RecordData getAlbumByAlbumName(String albumName);

}
