package com.example.lotto.controller;

import com.example.lotto.object.LottoObj;
import com.example.lotto.object.MostNumberObj;
import com.example.lotto.service.LottoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@Controller
@RequiredArgsConstructor
public class LottoController {

    private final LottoService lottoService;

    @ResponseBody
    @RequestMapping(value = "/api/v1/lotto-number")
    public ResponseEntity<Object> getLottoNumber() {
        List<LottoObj> lottoObjList = lottoService.getLottoNumberList(1);
        return new ResponseEntity<>(lottoObjList, HttpStatus.OK);
    }

    //최근 N차 리스트
    @ResponseBody
    @RequestMapping(value = "/api/v1/lotto-number/{num}", method = RequestMethod.GET)
    public ResponseEntity<List<LottoObj>> getLottoNumberList(@PathVariable int num) {
        return new ResponseEntity<>(lottoService.getLottoNumberList(num), HttpStatus.OK);
    }

    //최빈수와 횟수 리스트
    @ResponseBody
    @RequestMapping(value = "/api/v1/lotto/most-number/{num}")
    public ResponseEntity<Object> getLottoMostNumberList(@PathVariable("num") int num) {
        List<MostNumberObj> mostNumberObjList = lottoService.getMostNumber(num);
        return new ResponseEntity<>(mostNumberObjList, HttpStatus.OK);
    }

    //원하는 순위안에서 랜덤숫자 7개 뽑기
    @ResponseBody
    @RequestMapping(value = "/api/v1/lotto/random-number/{num}/{rank}")
    public ResponseEntity<List<Integer>> getReferralNumber(@PathVariable("num") int num, @PathVariable("rank") int rank) {
        List<Integer> referralNumberList = lottoService.getReferralNumber(num,rank);
        referralNumberList.sort(Comparator.naturalOrder());
        return new ResponseEntity<>(referralNumberList, HttpStatus.OK);
    }

    //myNumber
    @ResponseBody
    @RequestMapping(value = "/api/v1/lotto/random-number/{num}/{firstRank}/{middleRank}/{lastRank}")
    public ResponseEntity<List<Integer>> myNumber(@PathVariable("num") int num, @PathVariable("firstRank") int firstRank,@PathVariable("middleRank") int middleRank, @PathVariable("lastRank") int lastRank) {
        List<Integer> myNumberList = lottoService.myNumber(num, firstRank, middleRank, lastRank);
        myNumberList.sort(Comparator.naturalOrder());
        return new ResponseEntity<>(myNumberList, HttpStatus.OK);
    }

}
