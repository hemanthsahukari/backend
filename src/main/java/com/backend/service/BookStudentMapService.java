package com.backend.service;


import com.backend.model.BookStudentMap;

import java.util.List;

public interface BookStudentMapService {

    void saveBookStudentMap(BookStudentMap bookStudentMap);

    List<BookStudentMap> getBookStudentMap();

    List<BookStudentMap> getBookStudentMapByName(long studentId);

    BookStudentMap getBookStudentMapByBookAndStudent(long bookId, long studentId);

    void deleteBookStudentMap(BookStudentMap bookStudentMap);
}
