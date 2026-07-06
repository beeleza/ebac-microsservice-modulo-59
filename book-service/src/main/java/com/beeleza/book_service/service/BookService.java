package com.beeleza.book_service.service;

import com.beeleza.book_service.domain.model.Book;
import com.beeleza.book_service.dto.BookDTO;
import com.beeleza.book_service.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO create(BookDTO dto) {
        Book book = new Book(dto.getTitle(), dto.getAuthor(), dto.getIsbn(), dto.getAvailableQuantity());
        return toDTO(bookRepository.save(book));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toDTO(book);
    }

    public List<BookDTO> findByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO update(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setAvailableQuantity(dto.getAvailableQuantity());

        return toDTO(bookRepository.save(book));
    }

    private BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getAvailableQuantity()
        );
    }

}
