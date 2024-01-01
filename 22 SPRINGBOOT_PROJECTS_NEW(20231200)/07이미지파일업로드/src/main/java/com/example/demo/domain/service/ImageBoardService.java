package com.example.demo.domain.service;


import com.example.demo.domain.dto.ImageBoardDto;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.repository.ImageBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ImageBoardService {

    private String imageBoardPath ="C:\\imageboard"; //OS에 로그인한 사용자폴더에 저장

    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Autowired
    private ResourceLoader resourceLoader;


    @Transactional(rollbackFor = Exception.class)
    public void addImageBoard(ImageBoardDto dto) throws IOException {
        System.out.println("ImageBoardService's addImage....");

        //DB에 Inser한다음 Id값을 받아와서 처리할것

        ImageBoard imageBoard = new ImageBoard();

        imageBoard.setSeller(dto.getSeller());
        imageBoard.setProductname(dto.getProductname());
        imageBoard.setCategory(dto.getCategory());
        imageBoard.setBrandname(dto.getBrandname());
        imageBoard.setItemdetals(dto.getItemdetals());
        imageBoard.setAmount(dto.getAmount());
        imageBoard.setSize(dto.getSize());
        imageBoard.setCreatedAt(LocalDateTime.now());


        imageBoardRepository.save(imageBoard);
        System.out.println("저장확인 ID : " + imageBoard.getId());

        //저장 폴더 지정
        String uploadPath = imageBoardPath + File.separator + dto.getSeller()+File.separator + dto.getCategory()+File.separator+ imageBoard.getId();
        File dir = new File(uploadPath);
        System.out.println("SavedFolder : " + uploadPath);
        System.out.println("SavedFolder : " + dir.getPath());
        if(!dir.exists()) {
            dir.mkdirs();
        }

        //게시물당 파일은 5장까지만
        List<String> boardlist = new ArrayList<>();

        for(MultipartFile file : dto.getFiles())
        {
            System.out.println("--------------------");
            System.out.println("FILE NAME : " + file.getOriginalFilename());
            System.out.println("FILE SIZE : " + file.getSize() + " Byte");
            System.out.println("--------------------");
            boardlist.add(file.getOriginalFilename());

            File fileobj = new File(uploadPath,file.getOriginalFilename());
            System.out.println("fileobj : " + fileobj.getPath());

            file.transferTo(fileobj);   //파일저장

            //섬네일(https://kimvampa.tistory.com/218) - 섬네일은 css로 받기
            File thumbnailFile = new File(uploadPath, "s_" + file.getOriginalFilename());
            BufferedImage bo_image = ImageIO.read(fileobj);
            BufferedImage bt_image = new BufferedImage(300, 500, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D graphic = bt_image.createGraphics();
            graphic.drawImage(bo_image, 0, 0,300,500, null);

            ImageIO.write(bt_image, "jpg", thumbnailFile);
        }

        imageBoard.setFiles(boardlist);
        imageBoardRepository.save(imageBoard);

    }
    @Transactional(rollbackFor = Exception.class)
    public List<ImageBoard> getImageboardList(){
        //Desc Sorting return
        return imageBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(rollbackFor = Exception.class)
    public ImageBoard getImageboard(Long id) {

        Optional<ImageBoard> optionalImageBoard = imageBoardRepository.findById(id);
        if(!optionalImageBoard.isEmpty()) {

            return optionalImageBoard.get();
        }
        return null;
    }
}
