package com.NC_RecordShop.RecordShop.Controller;

import com.NC_RecordShop.RecordShop.Model.Genre;
import com.NC_RecordShop.RecordShop.Model.RecordData;
import com.NC_RecordShop.RecordShop.Service.RecordManagerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
class RecordManagerControllerTest {

    @Mock
    private RecordManagerServiceImpl mockRecordManagerServiceImpl;

    @InjectMocks
    private RecordManagerController recordManagerController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvcController = MockMvcBuilders.standaloneSetup(recordManagerController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void testGetAllAlbums() throws Exception {

        List<RecordData> albums = new ArrayList<>();
        albums.add(new RecordData(1L, "Get rich or die trying", "50 cent", 2004, 10, Genre.RAP)) ;
        albums.add(new RecordData(2L, "Link Rock Album", "linkin Park", 2006, 16, Genre.ROCK)) ;

        when(mockRecordManagerServiceImpl.getAllAlbums()).thenReturn(albums);

        mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/recordShop"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
//        do extra tests

    }


    @Test
    public void testGetAlbumById () throws Exception {

        RecordData album = new RecordData(1L, "Get rich or die trying", "50 Cent", 2004, 10, Genre.RAP);

        when(mockRecordManagerServiceImpl.getAlbumById(album.getId())).thenReturn(album);

        mockMvcController.perform(
                        MockMvcRequestBuilders.get("/api/v1/recordShop/{id}", album.getId()))
//                            The param that you passed in to your actual function needs to be here ^^^^
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(album.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Get rich or die trying"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist").value("50 Cent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseYear").value(2004))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("RAP"));
    }

    @Test
    public void testPostAlbum() throws Exception {
        RecordData newAlbum = new RecordData(1L, "New Album", "New Artist", 2023, 5, Genre.ROCK);

        when(mockRecordManagerServiceImpl.postAlbum(newAlbum)).thenReturn(newAlbum);

        mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/recordShop")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(newAlbum)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("New Album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist").value("New Artist"));

    }

    @Test
    public void testGetAlbumByArtistName() throws Exception {

        List<RecordData> albums = new ArrayList<>();
        albums.add(new RecordData(1L, "Get rich or die trying", "50 cent", 2004, 10, Genre.RAP)) ;
        albums.add(new RecordData(2L, "The Massacre", "50 cent", 2005, 8, Genre.RAP));

        when(mockRecordManagerServiceImpl.getAlbumsByArtist("50 cent")).thenReturn(albums);

        mockMvcController.perform(
                MockMvcRequestBuilders.get("/api/v1/recordShop/artistName").param("artistName", "50 cent"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].albumName").value("Get rich or die trying"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("The Massacre"));
    }

    @Test
    public void testGetAlbumsByReleaseYear() throws Exception {
        List<RecordData> albums = new ArrayList<>();
        albums.add(new RecordData(1L, "Get rich or die trying", "50 cent", 2004, 10, Genre.RAP)) ;
        albums.add(new RecordData(2L, "The Massacre", "50 cent", 2005, 8, Genre.RAP));
        albums.add(new RecordData(3L, "Discovery", "the game", 2005, 11, Genre.RAP)) ;

        when(mockRecordManagerServiceImpl.getAlbumsByReleaseYear(2005)).thenReturn(albums);

        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/recordShop/releaseYear")
                        .param("releaseYear", "2005"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].releaseYear").value(2005))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].releaseYear").value(2005));
    }

    @Test
    public void testGetAlbumsByGenre() throws Exception {
        List<RecordData> albums = new ArrayList<>();
        albums.add(new RecordData(1L, "Get rich or die trying", "50 cent", 2004, 10, Genre.RAP)) ;
        albums.add(new RecordData(1L, "Thriller", "Michael Jackson", 1982, 10, Genre.POP));

        when(mockRecordManagerServiceImpl.getAlbumsByGenre(Genre.POP)).thenReturn(albums);
        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/recordShop/genre")
                        .param("genre", "POP"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].albumName").value("Thriller"));
    }

    @Test
    public void testGetAlbumByAlbumName() throws Exception {
        RecordData album = new RecordData(1L, "Thriller", "Michael Jackson", 1982, 10, Genre.POP);

        when(mockRecordManagerServiceImpl.getAlbumByAlbumName("Thriller")).thenReturn(album);

        mockMvcController.perform(MockMvcRequestBuilders.get("/api/v1/recordShop/albumName")
                        .param("albumName", "Thriller"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("Thriller"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist").value("Michael Jackson"));
    }

}
