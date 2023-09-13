package com.backend.service;

import com.backend.model.History;
import com.backend.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public History saveHistory(History history) {
        return historyRepository.save(history);
    }
}
