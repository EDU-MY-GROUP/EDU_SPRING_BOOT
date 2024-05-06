//package com.example.demo.repository;
//
//
//import com.example.demo.entity.Book;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//class BookRepositoryNamingTest {
//
//
//    @Autowired
//    private BookRepository bookRepository;
//
//    @Test
//    public void testFindByBookName() {
//        // Given
//        Book book1 = new Book(1L, "Book1", "Publisher1", "123456789");
//        Book book2 = new Book(2L, "Book2", "Publisher2", "987654321");
//        bookRepository.save(book1);
//        bookRepository.save(book2);
//
//        // When
//        List<Book> books = bookRepository.findByBookname("Book1");
//
//        // Then  (import static org.assertj.core.api.Assertions.assertThat;)
//        assertThat(books).hasSize(1);
//
//        assertThat(books.get(0).getBookcode()).isEqualTo(1L);
//        assertThat(books.get(0).getPublisher()).isEqualTo("Publisher1");
//    }
//
//    @Test
//    public void testCountByPublisher() {
//        // Given
//        Book book1 = new Book(1L, "Book1", "Publisher1", "123456789");
//        Book book2 = new Book(2L, "Book2", "Publisher1", "987654321");
//        bookRepository.save(book1);
//        bookRepository.save(book2);
//
//        // When
//        int count = bookRepository.countByPublisher("Publisher1");
//
//        // Then
//        assertThat(count).isEqualTo(2);
//    }
//
//    @Test
//    public void testDeleteByBookName() {
//        // Given
//        Book book1 = new Book(1L, "Book1", "Publisher1", "123456789");
//        Book book2 = new Book(2L, "Book2", "Publisher2", "987654321");
//        bookRepository.save(book1);
//        bookRepository.save(book2);
//
//        // When
//        bookRepository.deleteByBookname("Book1");
//
//        // Then
//        List<Book> books = bookRepository.findAll();
//        assertThat(books).hasSize(1);
//        assertThat(books.get(0).getBookcode()).isEqualTo(2L);
//        assertThat(books.get(0).getPublisher()).isEqualTo("Publisher2");
//    }
//
//}