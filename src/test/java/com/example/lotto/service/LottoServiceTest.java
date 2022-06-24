package com.example.lotto.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LottoServiceTest {
    @InjectMocks
    private LottoService lottoService;

    @Test
    public void 최신회차조회_테스트() throws IOException {
        int lottoNumber = lottoService.getLastRound();
        assertEquals(1020,lottoNumber);
    }


}