package com.backend.service;
import com.backend.model.Momo;
import com.backend.repository.MomoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MomoServiceImpl implements MomoService {

    @Autowired
    private MomoRepository momoRepository;


    @Override
    public void saveMomo (Momo momo) {
        momoRepository.save(momo);
    }

    @Override
    public String getCurrentMomo(){
        Momo momoList = momoRepository.getCurrentMomo();
        return momoList.getUsername();
    }
}


