package com.NC_RecordShop.RecordShop.Repository;

import com.NC_RecordShop.RecordShop.Model.RecordData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordManagerRepository extends CrudRepository<RecordData, Long> {

}