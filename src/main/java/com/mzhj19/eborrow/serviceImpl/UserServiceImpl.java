package com.mzhj19.eborrow.serviceImpl;

import com.mzhj19.eborrow.dto.UserRegisterReqDto;
import com.mzhj19.eborrow.exceptions.EborrowApiBadRequestException;
import com.mzhj19.eborrow.model.EborrowUser;
import com.mzhj19.eborrow.repository.UserRepository;
import com.mzhj19.eborrow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public EborrowUser save(UserRegisterReqDto userRegisterReqDto) {
        //String encodedPassword = passwordEncoder.encode(userRegisterReqDto.getPassword());
        EborrowUser existingUser = userRepository.findByEmail(userRegisterReqDto.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new EborrowApiBadRequestException("USER WITH EMAIL " + userRegisterReqDto.getEmail() + " ALREADY EXISTS!");
        }
        EborrowUser savedUser = userRepository.save(EborrowUser.builder()
                .email(userRegisterReqDto.getEmail())
                //.password(encodedPassword)
                .password(userRegisterReqDto.getPassword())
                .build()
        );
        if(savedUser == null) {
            throw new EborrowApiBadRequestException("USER NOT SAVED! PLEASE TRY AGAIN LATER.");
        }
        return savedUser;
    }

    @Override
    public List<EborrowUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<EborrowUser> getUserById(String id) {
        return userRepository.findById(Long.parseLong(id));
    }

    @Override
    public Optional<EborrowUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
