package com.example.demo.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ImageBoardFileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "iid",foreignKey = @ForeignKey(name="FK_imagefileInfo_imageBoard",
            foreignKeyDefinition ="FOREIGN KEY(iid) REFERENCES image_board(id) ON DELETE CASCADE ON UPDATE CASCADE" ))
    private ImageBoard imageBoard;
    private String dir;
    private String filename;
}
