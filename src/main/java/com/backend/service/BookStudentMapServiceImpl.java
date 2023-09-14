package com.backend.service;

import com.backend.model.BookStudentMap;
import com.backend.repository.BookStudentMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookStudentMapServiceImpl implements BookStudentMapService {
    @Autowired
    private BookStudentMapRepository bookStudentMapRepository;
    @Override
    public void saveBookStudentMap (BookStudentMap bookStudentMap) {
        bookStudentMapRepository.save(bookStudentMap);
    }

    @Override
    public List<BookStudentMap> getBookStudentMapByName(long studentId){
        return bookStudentMapRepository.findByStudentId(studentId);
    }

    @Override
    public List<BookStudentMap> getBookStudentMap(){

        return (List<BookStudentMap>) bookStudentMapRepository.findAll();

    }

    @Override
    public BookStudentMap getBookStudentMapByBookAndStudent(long bookId, long studentId){
        return bookStudentMapRepository.findByBookIdAndStudentId(bookId, studentId);
    }

    @Override
    public void deleteBookStudentMap(BookStudentMap bookStudentMap){
        bookStudentMapRepository.delete(bookStudentMap);
    }
}
