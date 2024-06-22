package com.NC_RecordShop.RecordShop.Service;
import com.NC_RecordShop.RecordShop.ExceptionHandling.AlbumNotFoundException;
import com.NC_RecordShop.RecordShop.ExceptionHandling.InvalidAlbumInputException;
import com.NC_RecordShop.RecordShop.Model.Genre;
import com.NC_RecordShop.RecordShop.Model.RecordData;
import com.NC_RecordShop.RecordShop.Repository.RecordManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Service
public class RecordManagerServiceImpl implements RecordManagerService{

    @Autowired
    RecordManagerRepository recordManagerRepository;

    @Override
    public List<RecordData> getAllAlbums() {
        List<RecordData> albums = new ArrayList<>();
        recordManagerRepository.findAll().forEach(albums::add);

        if (!albums.isEmpty()){
            return albums;
        }
        return null;
    }

    @Override
    @Cacheable("albums")
    public RecordData getAlbumById(long id) {

        RecordData album = recordManagerRepository.findById(id).orElse(null);
        if (album == null) {
            throw new AlbumNotFoundException("Album not found with id: " + id);
        }
        return album;
    }

    @Override
    public RecordData postAlbum(RecordData recordData) {

        if (recordData.getAlbumName() == null || recordData.getAlbumName().isEmpty()) {
            throw new InvalidAlbumInputException("Please provide Album name");
        }

        if (recordData.getArtist() == null || recordData.getArtist().isEmpty()) {
            throw new InvalidAlbumInputException("Please provide artist name");
        }

        if (recordData.getStock() == null || recordData.getStock() < 0 || recordData.getStock() > 10000 ){
            throw new InvalidAlbumInputException("Please enter valid stock number no greater than 10,000");
        }

        if (recordData.getReleaseYear()==null || recordData.getReleaseYear() < 1900 || recordData.getReleaseYear() > 2024){
            throw new InvalidAlbumInputException("Please enter valid release year");
        }

        if (!EnumSet.allOf(Genre.class).contains(recordData.getGenre())){
            throw new InvalidAlbumInputException("Please input a valid genre");
        }
       return recordManagerRepository.save(recordData);
    }

    @Override
    @CachePut("albums")
    public RecordData updateAlbum(long id, RecordData recordData) {
        System.out.println("made api update call");
        //So find the book by ID you want to update
       RecordData albumToUpdate=recordManagerRepository.findById(id).orElse(null);

        //once found do .sets for all the individual fields
        if (recordData.getAlbumName() == null || recordData.getAlbumName().isEmpty()) {
            throw new InvalidAlbumInputException("Please provide Album name");
        } else {
            albumToUpdate.setAlbumName(recordData.getAlbumName());
        }

        if (recordData.getArtist() == null || recordData.getArtist().isEmpty()) {
            throw new InvalidAlbumInputException("Please provide artist name");
        }
        else {
            albumToUpdate.setArtist(recordData.getArtist());
        }

        if (recordData.getStock() == null || recordData.getStock() < 0 || recordData.getStock() > 10000 ){
            throw new InvalidAlbumInputException("Please enter valid stock number no greater than 10,000");
        }
        else {
            albumToUpdate.setReleaseYear(recordData.getReleaseYear());
        }

        if (recordData.getReleaseYear()==null || recordData.getReleaseYear() < 1900 || recordData.getReleaseYear() > 2024){
            throw new InvalidAlbumInputException("Please enter valid release year");
        }
        else {
            albumToUpdate.setStock(recordData.getStock());
        }

        if (!EnumSet.allOf(Genre.class).contains(recordData.getGenre())){
            throw new InvalidAlbumInputException("Please input a valid genre");
        }
        else {
            albumToUpdate.setGenre(recordData.getGenre());
        }

        //Done in 1 step, but save updatedValues to DB and then return them so when function called it has the new values available
        return recordManagerRepository.save(albumToUpdate);
    }

    @Override
    @CacheEvict("albums")
    public String deleteAlbum(long id) {
        if (recordManagerRepository.existsById(id)){
            recordManagerRepository.deleteById(id);
            return "book with id:" + id+" has been deleted";
        }
        return null;
    }

    @Override
    public List<RecordData> getAlbumsByArtist(String artist) {
        return recordManagerRepository.findAlbumByArtist(artist);
    }

    @Override
    public List<RecordData> getAlbumsByReleaseYear(long releaseYear) {
        return recordManagerRepository.findAlbumByReleaseYear(releaseYear);
    }

    @Override
    public List<RecordData> getAlbumsByGenre(Genre genre) {
        return recordManagerRepository.findAlbumByGenre(genre);
    }

    @Override
    public RecordData getAlbumByAlbumName(String albumName) {
        return recordManagerRepository.findAlbumByAlbumName(albumName);
    }


}
