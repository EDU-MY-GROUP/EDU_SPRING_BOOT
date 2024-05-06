package com.example.demo.domain.repository;


import com.example.demo.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {




    @Query(value = "SELECT * FROM bookdb.board ORDER BY no DESC LIMIT :amount OFFSET :offset", nativeQuery = true)
    List<Board> findBoardAmountStart(@Param("amount") int amount,@Param("offset") int offset);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE Board b SET b.title = :title, b.content = :content, b.regdate = :regdate, b.count = :count, b.filename = :filename, b.filesize = :filesize WHERE b.no = :no")
    Integer updateBoard(
            @Param("title") String title,
            @Param("content") String content,
            @Param("regdate") LocalDateTime regdate,
            @Param("count") Long count,
            @Param("filename") String filename,
            @Param("filesize") String filesize,
            @Param("no") Long no
    );


}
