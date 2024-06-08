package com.NC_RecordShop.RecordShop.Service;

import com.NC_RecordShop.RecordShop.Model.RecordData;

import java.util.List;

public interface RecordManagerService {
    List<RecordData> getAllAlbums();
    RecordData getAlbumById(Long id);
    RecordData postAlbum(RecordData recordData);
    RecordData updateAlbum(long id, RecordData recordData);
    String deleteAlbum(long id);
}
