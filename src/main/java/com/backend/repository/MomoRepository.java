package com.backend.repository;
import com.backend.model.Momo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MomoRepository extends CrudRepository<Momo, Long> {
    @Query(value = "select * from momo c ORDER BY id DESC LIMIT 1",nativeQuery = true)
    Momo getCurrentMomo();
}