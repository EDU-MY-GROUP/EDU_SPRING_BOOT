package com.example.demo.domain.service;

import com.example.demo.config.auth.PrincipalDetails;
import com.example.demo.domain.dto.CartDto;
import com.example.demo.domain.entity.Cart;
import com.example.demo.domain.entity.ImageBoard;
import com.example.demo.domain.entity.User;
import com.example.demo.domain.repository.CartRepository;
import com.example.demo.domain.repository.ImageBoardRepository;
import com.example.demo.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@Slf4j
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ImageBoardRepository imageBoardRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor=Exception.class)
    public boolean addCart(CartDto dto) throws Exception{

      ImageBoard imageBoard =   imageBoardRepository.findById(dto.getProduct_id()).get();
      Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();

      String username =   authentication.getName();


      User user =  userRepository.findById(username).get();


      Cart cart = new Cart();
      cart.setUser(user);
      cart.setImageBoard(imageBoard);
      cart.setRegdate(LocalDateTime.now());

      cartRepository.save(cart);


      return true;
    }
}
