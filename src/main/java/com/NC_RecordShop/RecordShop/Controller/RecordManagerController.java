package com.NC_RecordShop.RecordShop.Controller;

import com.NC_RecordShop.RecordShop.ExceptionHandling.AlbumNotFoundException;
import com.NC_RecordShop.RecordShop.ExceptionHandling.InvalidAlbumInputException;
import com.NC_RecordShop.RecordShop.Model.Genre;
import com.NC_RecordShop.RecordShop.Model.RecordData;
import com.NC_RecordShop.RecordShop.Service.RecordManagerService;
//import jakarta.validation.Valid;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recordShop")

public class RecordManagerController {

    @Autowired
    RecordManagerService recordManagerService;

    @GetMapping
    public ResponseEntity<?> getAllAlbums() {

        try {
            List<RecordData> albums = recordManagerService.getAllAlbums();

            if (albums == null || albums.isEmpty()) {
                String message = "No albums in list, please post to add.";
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(albums, HttpStatus.OK);
            }
        } catch (Exception e) {
            String errMessage = "An error occurred ...try again.";
            return new ResponseEntity<>(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlbumById(@PathVariable Long id) {
        try {
            RecordData album = recordManagerService.getAlbumById(id);
            return new ResponseEntity<>(album, HttpStatus.OK);
        } catch (AlbumNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> postAlbum(@RequestBody RecordData recordData ) {

        try{
            RecordData album= recordManagerService.postAlbum(recordData);
            return new ResponseEntity<>(album, HttpStatus.CREATED);
        }
        catch(InvalidAlbumInputException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            String message= "Check you've entered the right types for each section";
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PutMapping ("/{id}")
    public ResponseEntity<?> updateAlbum(@PathVariable long id, @RequestBody RecordData recordData) {

        try{
            RecordData album= recordManagerService.updateAlbum(id,recordData);
            return new ResponseEntity<>(album, HttpStatus.OK);
        }
        catch (InvalidAlbumInputException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity <String> deleteAlbum(@PathVariable long id){
        String albumToDelete= recordManagerService.deleteAlbum(id);

        if (albumToDelete != null){
            return new ResponseEntity<>(albumToDelete,HttpStatus.OK);
        }
        else {
            String message= "Album id:" + id + " not found try again!";
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/artistName")
    public ResponseEntity<List<RecordData>> getAlbumsByArtist(@RequestParam String artistName){
    List<RecordData> listOfalbums= recordManagerService.getAlbumsByArtist(artistName);

    return new ResponseEntity<>(listOfalbums, HttpStatus.OK);
    }

    @GetMapping("/releaseYear")
    public ResponseEntity<List<RecordData>> getAlbumsByreleaseYear(@RequestParam long releaseYear){
        List<RecordData> listOfalbums= recordManagerService.getAlbumsByReleaseYear(releaseYear);

        return new ResponseEntity<>(listOfalbums, HttpStatus.OK);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<RecordData>> getAlbumsByGenre(@RequestParam Genre genre){
        List<RecordData> listOfalbums= recordManagerService.getAlbumsByGenre(genre);

        return new ResponseEntity<>(listOfalbums, HttpStatus.OK);
    }

    @GetMapping("/albumName")
    public ResponseEntity<RecordData> getAlbumByAlbumName(@RequestParam String albumName){
        RecordData album= recordManagerService.getAlbumByAlbumName(albumName);

        return new ResponseEntity<>(album, HttpStatus.OK);
    }






}
