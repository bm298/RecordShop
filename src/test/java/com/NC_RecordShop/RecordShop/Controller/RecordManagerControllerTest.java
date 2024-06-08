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
        RecordData newAlbum = new RecordData(null, "New Album", "New Artist", 2023, 5, Genre.ROCK);
        RecordData createdAlbum = new RecordData(1L, "New Album", "New Artist", 2023, 5, Genre.ROCK);

        when(mockRecordManagerServiceImpl.postAlbum(newAlbum)).thenReturn(createdAlbum);

        mockMvcController.perform(
                        MockMvcRequestBuilders.post("/api/v1/recordShop")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(newAlbum)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.albumName").value("New Album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.artist").value("New Artist"));

    }


}
