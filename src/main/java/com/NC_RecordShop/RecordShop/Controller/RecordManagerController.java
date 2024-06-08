package com.NC_RecordShop.RecordShop.Controller;

import com.NC_RecordShop.RecordShop.Model.RecordData;
import com.NC_RecordShop.RecordShop.Service.RecordManagerService;
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
    public ResponseEntity<List<RecordData>> getAllAlbums() {
        List<RecordData> albums = recordManagerService.getAllAlbums();
        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordData> getAlbumById(@PathVariable Long id) {
        RecordData album = recordManagerService.getAlbumById(id);
        return new ResponseEntity<>(album, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<RecordData> postAlbum(@RequestBody RecordData recordData ) {
        RecordData album= recordManagerService.postAlbum(recordData);
        return new ResponseEntity<>(album, HttpStatus.CREATED);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<RecordData> updateAlbum(@PathVariable long id, @RequestBody RecordData recordData) {
        RecordData album= recordManagerService.updateAlbum(id,recordData);
        return new ResponseEntity<>(album, HttpStatus.OK);
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






}
