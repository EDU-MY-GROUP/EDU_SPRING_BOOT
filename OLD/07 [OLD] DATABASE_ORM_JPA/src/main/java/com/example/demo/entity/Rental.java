package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "rental")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rental implements Serializable {

    @Id
    private Long rid;


    //Book--1:1--Rental ----------------------------

    @ManyToOne
    @JoinColumn(name = "bookcode",foreignKey = @ForeignKey(name = "FK_rental_book",
            foreignKeyDefinition = "FOREIGN KEY (bookcode) REFERENCES book (bookcode) ON DELETE CASCADE ON UPDATE CASCADE") ) //FK설정\
    private Book book;
    //Book--1:1--Rental ----------------------------


    //Rental--n:1--User -----------------------------
    @ManyToOne
    @JoinColumn(name = "id" ,foreignKey = @ForeignKey(name = "FK_rental_user",
            foreignKeyDefinition = "FOREIGN KEY (id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user; // FK
    //Rental--n:1--User -----------------------------

    private LocalDate regDate;

    private LocalDate returnDate;


}
