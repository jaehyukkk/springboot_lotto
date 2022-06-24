package com.example.lotto.service;

import com.example.lotto.object.LottoObj;
import com.example.lotto.object.MostNumberObj;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Log4j2
@Service
public class LottoService {

    //원하는 기간안에 당첨번호 조회 (ex. num 이 10일때 최근 10회 당첨번호 조회)
    public List<LottoObj> getLottoNumberList(int num) {

        try{
            List<LottoObj> lottoNumberList = new ArrayList<>();
            int lastNum = getLastRound();

            //현재 회차보다 많은 회차를 조회하려 할때
            if (num > lastNum) {
                throw new RuntimeException("조회할수있는 회차를 초과하였습니다.");
            }

            int startNum = lastNum - num;
                //1018                 1020
            for (int i = startNum+1 ; i < lastNum+1 ; i++) {
                final String lottoUrl = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + i;
                Connection conn = Jsoup.connect(lottoUrl);
                Document document = conn.get();
                Elements lottoElement = document.getElementsByTag("body");
                LottoObj lottoDto = new Gson().fromJson(html2text(String.valueOf(lottoElement)), LottoObj.class);
                lottoNumberList.add(0,lottoDto);
            }
            return lottoNumberList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //원하는 기간 안 로또번호 통계
    public List<MostNumberObj> getMostNumber(int num) {
        int rank = 1;
        List<LottoObj> lottoDtoList = getLottoNumberList(num);
        List<Integer> lottoNumberList = new ArrayList<>();
        boolean[] noNumCheckArr = new boolean[46];

        HashMap<Integer, Integer> lottoMostNumberMap = new HashMap<>();
        List<MostNumberObj> mostNumberObjList = new ArrayList<>();


        for (LottoObj lottoDto : lottoDtoList) {
            lottoNumberList.add(Integer.parseInt(lottoDto.getDrwtNo1()));
            lottoNumberList.add(Integer.parseInt(lottoDto.getDrwtNo2()));
            lottoNumberList.add(Integer.parseInt(lottoDto.getDrwtNo3()));
            lottoNumberList.add(Integer.parseInt(lottoDto.getDrwtNo4()));
            lottoNumberList.add(Integer.parseInt(lottoDto.getDrwtNo5()));
            lottoNumberList.add(Integer.parseInt(lottoDto.getDrwtNo6()));
            lottoNumberList.add(Integer.parseInt(lottoDto.getBnusNo()));
        }

        //출현한 번호와 몇번 출현했는지를 map 에 저장
        //key = 출현번호 value = 출현횟수
        for (int i = 1; i < 46; i ++) {
            int cnt = 0;
            for (int j = 0; j < lottoNumberList.size(); j++) {
                if (lottoNumberList.get(j) == i) {
                    cnt++;
                    lottoMostNumberMap.put(i, cnt);
                }
            }

        }

        //출현하지 않은번호 카운트 0 으로 집어넣기
        for (Integer key : lottoMostNumberMap.keySet()) {
            noNumCheckArr[key] = true;
        }
        for (int i = 1 ; i < noNumCheckArr.length ; i ++) {
            if (!noNumCheckArr[i]) {
                lottoMostNumberMap.put(i, 0);
            }
        }

        //순위 정렬
        List<Map.Entry<Integer, Integer>> listEntries = new ArrayList<>(lottoMostNumberMap.entrySet());
        listEntries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        //객체화
        for(Map.Entry<Integer, Integer> entry : listEntries) {
            MostNumberObj mostNumberObj = new MostNumberObj(entry.getKey(), entry.getValue(), rank);
            mostNumberObjList.add(mostNumberObj);
            rank++;
        }

        return mostNumberObjList;
    }

    //원하는 기간 중 원하는 순위 안에서 랜덤숫자 뽑기
    public List<Integer> getReferralNumber(int num, int rank) {
        log.info(rank);
        final int MAX_NUM = 6;
        List<Integer> duplicateCheckList = new ArrayList<>();
        List<MostNumberObj> mostNumberObjList = getMostNumber(num);
        List<Integer> randomNumberList = new ArrayList<>();

        for (int i = 1 ; i <= MAX_NUM ; i ++) {

            int index;

            //중복되는 숫자가 없을때까지 무한루프
            while (true) {
                index = (int) (Math.random() * rank + 1);
                if(duplicateCheck(duplicateCheckList, index)){
                    duplicateCheckList.add(index);
                    int mostNum = mostNumberObjList.get(index).getNumber();
                    randomNumberList.add(mostNum);
                    break; //while 종료
                }
            } //while end

        } //for end
        randomNumberList.sort(Comparator.naturalOrder());

        return randomNumberList;
    }



    //리펙토링 필요
    //원하는 기간 (num) 안에 원하는 순위안에 숫자들로 랜덤숫자 추출
    public List<Integer> myNumber(int num, int firstRank, int middleRank, int lastRank) {
        List<Integer> duplicateCheckList = new ArrayList<>();
        List<MostNumberObj> mostNumberObjList = getMostNumber(num);
        List<Integer> randomNumberList = new ArrayList<>();
        int index;

        for (int i = 1 ; i <= 4 ; i ++) {

            while (true) {
                index = (int) (Math.random() * firstRank);
                if(duplicateCheck(duplicateCheckList, index)){
                    duplicateCheckList.add(index);
                    int mostNum = mostNumberObjList.get(index).getNumber();
                    randomNumberList.add(mostNum);
                    break; //while 종료
                }
            } //while end

        } //for end

        for (int i = 1 ; i <= 1 ; i ++) {

            while (true) {
                index = (int) (Math.random() * (middleRank - firstRank)) + firstRank ;
                if(duplicateCheck(duplicateCheckList, index)){
                    duplicateCheckList.add(index);
                    int mostNum = mostNumberObjList.get(index).getNumber();
                    randomNumberList.add(mostNum);
                    break; //while 종료
                }
            } //while end

        } //for end

        for (int i = 1 ; i <= 1 ; i ++) {

            while (true) {
                index = (int) (Math.random() * (lastRank - middleRank)) + middleRank;
                if(duplicateCheck(duplicateCheckList, index)){
                    duplicateCheckList.add(index);
                    int mostNum = mostNumberObjList.get(index).getNumber();
                    randomNumberList.add(mostNum);
                    break; //while 종료
                }
            } //while end

        } //for end
        return randomNumberList;
    }



    //중복이 있다면 false 없다면 true
    public static boolean duplicateCheck(List<Integer> duplicateCheckList, int index) {
        if (duplicateCheckList.size() == 0) {
            return true;
        }
        for (Integer num : duplicateCheckList) {
            if (num == index) {
                return false;
            }
        }
        return true;
    }

    //제일 최근 회차 크롤링해오기
    public static int getLastRound() throws IOException {
        final String url = "https://dhlottery.co.kr/common.do?method=main";
        Connection conn = Jsoup.connect(url);
        Document document = conn.get();
        Element lottoElement = document.getElementById("lottoDrwNo");
        return Integer.parseInt(html2text(String.valueOf(lottoElement)));
    }

    //크롤링 해온 정보에 html 태그 없에기
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
