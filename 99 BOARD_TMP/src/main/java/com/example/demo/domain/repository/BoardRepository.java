//package com.example.demo.domain.repository;
//
//
//import com.example.demo.domain.dto.BoardDto;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//
//@Repository
//public class BoardRepository {
//
//    @Query("select * from tbl_board order by no desc limit #{startno},#{amount}")
//    List<BoardDto> SelectAll(@Param("startno")int startno, @Param("amount")int amount);
//
//    @Query("select count(*) from tbl_board")
//    int getAmount();
//
//    @Query("insert into tbl_board values(null,#{email},#{title},#{content},now(),0,#{dirpath},#{filename},#{filesize})")
//    int Insert(BoardDto bdto);
//
//    @Query("select * from tbl_board where no=#{bno}")
//    BoardDto Select(@Param("bno")int bno);
//
//    @Query("update tbl_board set count=count+1 where no=#{bno}")
//    public int Update(@Param("bno")int bno);
//
//    @Query("insert into tbl_reply values(null,#{bno},#{email},#{content},now())")
//    int InsertReply(ReplyDto rdto);
//
//    @Query("select * from tbl_reply where bno=#{bno} order by rno desc")
//    List<ReplyDto> ReplySelectAll(@Param("bno")int bno);
//
//    @Query("select count(*) from tbl_reply where bno=#{bno}")
//    int getReplyAmount(@Param("bno")int bno);
//
//    @Update("update tbl_board set filename=#{filename},filesize=#{filesize} where no=#{bno}")
//    int RemoveFile(@Param("bno")int bno  ,@Param("filename") String filename, @Param("filesize")String filesize);
//
//    @Update("update tbl_board set title=#{title},content=#{content} where no=#{no}")
//    int UpdateBoard(BoardDto dto);
//
//    @Delete("delete from tbl_board where no=#{no}")
//    int Delete(@Param("no")String bno);
//}
