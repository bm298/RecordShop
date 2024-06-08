package com.NC_RecordShop.RecordShop.Service;

import com.NC_RecordShop.RecordShop.Model.Genre;
import com.NC_RecordShop.RecordShop.Model.RecordData;
import com.NC_RecordShop.RecordShop.Repository.RecordManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordManagerServiceImpl implements RecordManagerService{

    @Autowired
    RecordManagerRepository recordManagerRepository;


    @Override
    public List<RecordData> getAllAlbums() {
        List<RecordData> albums = new ArrayList<>();
        recordManagerRepository.findAll().forEach(albums::add);
        return albums;
    }

    @Override
    public RecordData getAlbumById(Long id) {
        return recordManagerRepository.findById(id).orElse(null);
    }

    @Override
    public RecordData postAlbum(RecordData recordData) {
       return recordManagerRepository.save(recordData);
    }

    @Override
    public RecordData updateAlbum(long id, RecordData recordData) {
        //So find the book by ID you want to update
       RecordData albumToUpdate=recordManagerRepository.findById(id).orElse(null);

       //once found do .sets for all the individual fields
       albumToUpdate.setAlbumName(recordData.getAlbumName());
       albumToUpdate.setArtist(recordData.getArtist());
       albumToUpdate.setReleaseYear(recordData.getReleaseYear());
       albumToUpdate.setStock(recordData.getStock());
       albumToUpdate.setGenre(recordData.getGenre());

        //Done in 1 step, but save updatedValues to DB and then return them so when function called it has the new values available
        return recordManagerRepository.save(albumToUpdate);
    }

    @Override
    public String deleteAlbum(long id) {
        if (recordManagerRepository.existsById(id)){
            recordManagerRepository.deleteById(id);
            return "book with id:" + id+" has been deleted";
        }
        return null;
    }


}
