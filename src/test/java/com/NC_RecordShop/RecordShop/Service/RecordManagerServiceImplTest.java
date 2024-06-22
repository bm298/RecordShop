package com.NC_RecordShop.RecordShop.Service;

import com.NC_RecordShop.RecordShop.Controller.RecordManagerController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

class RecordManagerServiceImplTest {
    @Mock
    private RecordManagerService recordManagerService;

    @InjectMocks
    private RecordManagerController recordManagerController;

    @Autowired
    private MockMvc mockMvcController;

    private ObjectMapper mapper;



}