package com.backend.service;
import com.backend.model.Momo;

public interface MomoService {
    void saveMomo(Momo momo);
    String getCurrentMomo();
}
