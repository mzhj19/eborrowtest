package com.mzhj19.eborrow.controller;

import com.mzhj19.eborrow.dto.ResponseSuccessData;
import com.mzhj19.eborrow.dto.UserRegisterReqDto;
import com.mzhj19.eborrow.model.EborrowUser;
import com.mzhj19.eborrow.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void givenNothing_whenFindAll_thenReturnList() throws Exception {
        EborrowUser eborrowUser = EborrowUser.builder()
                .id(1L)
                .email("mzh@gmail.com")
                .password("123456")
                .build();

        List<EborrowUser> eborrowUsers = List.of(eborrowUser);
        BDDMockito.given(userService.getAllUsers()).willReturn(eborrowUsers);

        ResultActions response = mockMvc.perform(get("/api/v1/user/getAllUsers"));

        response.andExpect(status().isOk()) // Verify status is 200 OK
                .andExpect(jsonPath("$.data[0].id").value(1L)) // Corrected JSON Path
                .andExpect(jsonPath("$.data[0].email").value("mzh@gmail.com")) // Verify the first user's email
                .andDo(MockMvcResultHandlers.print()) // Print response for debugging
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("DATA FOUND."));
    }

    @Test
    public void givenId_whenFindById_thenReturnUser() throws Exception {
        String id = "1";
        EborrowUser eborrowUser = EborrowUser.builder()
                .id(1L)
                .email("mzh@gmail.com")
                .password("123456")
                .build();

        List<EborrowUser> eborrowUsers = List.of(eborrowUser);
        BDDMockito.given(userService.getUserById(id)).willReturn(Optional.of(eborrowUser));

        ResultActions response = mockMvc.perform(get("/api/v1/user/getUserById?id=1"));

        response.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("mzh@gmail.com"))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.message").value("DATA FOUND."));
    }

    @Test
    public void givenWrongId_whenFindById_thenReturnNotFound() throws Exception {
        String id = "1";
        EborrowUser eborrowUser = EborrowUser.builder()
                .id(1L)
                .email("mzh@gmail.com")
                .password("123456")
                .build();

        List<EborrowUser> eborrowUsers = List.of(eborrowUser);
        BDDMockito.given(userService.getUserById(id)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/api/v1/user/getUserById?id=1"));

        response.andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.message").value("DATA NOT FOUND."));
    }
}