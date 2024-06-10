package com.NC_RecordShop.RecordShop.Repository;

import com.NC_RecordShop.RecordShop.Model.Genre;
import com.NC_RecordShop.RecordShop.Model.RecordData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordManagerRepository extends CrudRepository<RecordData, Long> {
    List<RecordData> findAlbumByArtist(String artist);
    List<RecordData> findAlbumByReleaseYear(long releaseYear);
    List<RecordData> findAlbumByGenre(Genre genre);
    RecordData findAlbumByAlbumName(String albumName);
}