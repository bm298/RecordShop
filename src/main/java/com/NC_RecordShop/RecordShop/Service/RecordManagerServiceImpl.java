package com.NC_RecordShop.RecordShop.Service;

import com.NC_RecordShop.RecordShop.Repository.RecordManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RecordManagerServiceImpl implements RecordManagerService{

    @Autowired
    RecordManagerRepository recordManagerRepository;



}
