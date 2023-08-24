package com.example.demo.repository;

import com.example.demo.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class C01BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void jpa_test01_basic_function() {
        // Create
        Book book = new Book();
        book.setBookcode(1L);
        book.setBookname("Book1");
        book.setPublisher("Publisher1");
        book.setIsbn("123456789");
        bookRepository.save(book);  //기본 insert함수

        // Read
        Book savedBook = bookRepository.findById(1L).orElse(null);
        assertThat(savedBook).isNotNull(); //Null이 아님을 기대  Null이 아니면 테스트 통과
        assertThat(savedBook.getBookname()).isEqualTo("Book1");
        System.out.println(savedBook);

        // Update
        savedBook.setBookname("Updated Book1");
        bookRepository.save(savedBook);

        // Verify Update
        Book updatedBook = bookRepository.findById(1L).orElse(null);
        assertThat(updatedBook).isNotNull();
        assertThat(updatedBook.getBookname()).isEqualTo("Updated Book1");

        // Delete
        bookRepository.deleteById(1L);

        // Verify Delete
        Book deletedBook = bookRepository.findById(1L).orElse(null);
        assertThat(deletedBook).isNull();
    }


}