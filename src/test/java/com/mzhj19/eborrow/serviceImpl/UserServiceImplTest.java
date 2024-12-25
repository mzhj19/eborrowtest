package com.mzhj19.eborrow.serviceImpl;

import com.mzhj19.eborrow.dto.UserRegisterReqDto;
import com.mzhj19.eborrow.exceptions.EborrowApiBadRequestException;
import com.mzhj19.eborrow.model.EborrowUser;
import com.mzhj19.eborrow.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

//    @BeforeEach
//    void setUp() {
//        userRepository = Mockito.mock(UserRepository.class);
//        userService = new UserServiceImpl(userRepository);
//    }

    @Test
    void giveUserObject_whenSave_thenReturnUser() {
        EborrowUser eborrowUser = EborrowUser.builder()
                .id(1L)
                .email("mzh@gmail.com")
                .password("123456")
                .build();

        UserRegisterReqDto userRegisterReqDto = new UserRegisterReqDto("mzh@gmail.com", "123456");

        Mockito.when(userRepository.save(Mockito.any(EborrowUser.class))).thenReturn(eborrowUser);

        EborrowUser result = userService.save(userRegisterReqDto);

        assertNotNull(result);
        assertEquals(eborrowUser, result);
    }


    @Test
    void giveUserObject_whenSave_threwException() {
        EborrowUser eborrowUser = EborrowUser.builder()
                .id(1L)
                .email("mzh@gmail.com")
                .password("123456")
                .build();

        UserRegisterReqDto userRegisterReqDto = new UserRegisterReqDto("mzh@gmail.com", "123456");

        Mockito.when(userRepository.findByEmail(userRegisterReqDto.getEmail())).thenReturn(Optional.of(eborrowUser));

        Assertions.assertThrows(EborrowApiBadRequestException.class, () -> userService.save(userRegisterReqDto));

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(EborrowUser.class));

    }

    @Test
    void givenNothing_whenFindAll_thenReturnList() {
        EborrowUser eborrowUser = EborrowUser.builder()
                .id(1L)
                .email("mzh@gmail.com")
                .password("123456")
                .build();
        EborrowUser eborrowUser2 = EborrowUser.builder()
                .id(2L)
                .email("mzh@gmail.com")
                .password("123456")
                .build();

        Mockito.when(userRepository.findAll()).thenReturn(List.of(eborrowUser, eborrowUser2));

        List<EborrowUser> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void givenId_whenFindById_thenReturnUser() {
        String id = "1";
        EborrowUser eborrowUser = EborrowUser.builder()
                .id(Long.parseLong(id))
                .email("mzh@gmail.com")
                .password("123456")
                .build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(eborrowUser));

        Optional<EborrowUser> result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals(eborrowUser, result.get());
    }

    @Test
    void givenEmail_whenFindByEmail_thenReturnUser() {
        String email = "mzh@gmail.com";
        EborrowUser eborrowUser = EborrowUser.builder()
                .id(1L)
                .email(email)
                .password("123456")
                .build();

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(eborrowUser));

        Optional<EborrowUser> result = userService.findByEmail(email);

        assertNotNull(result);
        assertEquals(eborrowUser, result.get());
    }
}