package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.*;
import kopo.poly.repository.TravelChoiceRepository;
import kopo.poly.repository.TravelRepository;
import kopo.poly.repository.entity.TravelChoiceEntity;
import kopo.poly.repository.entity.TravelEntity;
import kopo.poly.service.IApiService;
import kopo.poly.util.DateUtil;
import kopo.poly.util.UrlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import javax.xml.transform.TransformerFactory;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("ApiService")
@RequiredArgsConstructor
public class ApiService implements IApiService {


    @Override
    public List<TravelResultDTO> mainPageTravel(List<InterestDTO> interestDTOList) throws Exception {
        log.info(this.getClass().getName() + "사용자 관심정보 조회기반 추천 여행지 조회");

        List<TravelResultDTO> pList = new ArrayList<>();
        TravelResultDTO travelResultDTO = null;
        for (int i = 0; i < interestDTOList.size(); i++) {
            String content = interestDTOList.get(i).getInterestContent();

            UrlUtil uu = new UrlUtil();
            String url = "http://13.124.6.224:8000";
            String api = "/keyword_travelInfo";
            String param = "?keyword=";
            String keyWord = content;

            String fullPath = uu.urlReadforString(url + api + param + URLEncoder.encode(keyWord, "UTF-8"));

            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(fullPath);

            for (int j = 0; j < jsonArray.size(); j++) {

                travelResultDTO = new TravelResultDTO();

                JSONObject object = (JSONObject) jsonArray.get(j);
                travelResultDTO.setAddr1(String.valueOf(object.get("addr1")));
                travelResultDTO.setContenttypeid(String.valueOf(object.get("contenttypeid")));
                travelResultDTO.setContentid(String.valueOf(object.get("contentid")));
                travelResultDTO.setReadcount(String.valueOf(object.get("readcount")));
                travelResultDTO.setFirstimage(String.valueOf(object.get("firstimage")));
                travelResultDTO.setFirstimage2(String.valueOf(object.get("firstimage2")));
                travelResultDTO.setTitle(String.valueOf(object.get("title")));

                if (travelResultDTO.getFirstimage() != "null") {
                    pList.add(travelResultDTO);
                }
            }
        }
        return pList;
    }

    @Override /* 지역기반 관광정보 조회 */
    public List<TravelResultDTO> locTravel(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName() + "지역기반 관광정보 조회 시작");

        if (travelDTO == null) {
            travelDTO = new TravelDTO();
        }

        if (travelDTO.getContentTypeId() == "14" && travelDTO.getCat1() == "AA02") {
            travelDTO.setCat1("A02");
        }

        if (travelDTO.getContentTypeId() == "15" && travelDTO.getCat1() == "AAA02") {
            travelDTO.setCat1("A02");
        }

        List<TravelResultDTO> rList = new ArrayList<>();

        UrlUtil uu = new UrlUtil();

        String url = "http://13.124.6.224:8000";
        String api = "/Travel_main";
        String cat1 = "?cat1=" + travelDTO.getCat1();
        String contentTypeId = "&contentTypeId=" + travelDTO.getContentTypeId();
        String areaCode = "&areaCode=" + travelDTO.getAreaCode();
        String sigunguCod = "&sigunguCode=" + travelDTO.getSigunguCode();
        String cat2 = "&cat2=" + travelDTO.getCat2();
        String cat3 = "&cat3=" + travelDTO.getCat3();
        String fullPath = uu.urlReadforString(url + api + cat1 + contentTypeId + areaCode + sigunguCod + cat2 + cat3);
        log.info("fullpath: " + fullPath);
        if (fullPath.length() < 1) {
            rList = new ArrayList<>();
        } else {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(fullPath);

            TravelResultDTO gDTO;
            log.info(String.valueOf(jsonArray.size()));

            log.info("가져온 데이터 사이즈? : " + jsonArray.size());
            for (int i = 0; i < jsonArray.size(); i++) {
                gDTO = new TravelResultDTO();
                JSONObject object = (JSONObject) jsonArray.get(i);
                gDTO.setAddr1(String.valueOf(object.get("addr1")));
                gDTO.setAreacode(String.valueOf(object.get("areacode")));
                gDTO.setContentid(String.valueOf(object.get("contentid")));
                gDTO.setContenttypeid(String.valueOf(object.get("contenttypeid")));
                gDTO.setFirstimage(String.valueOf(object.get("firstimage")));
                gDTO.setFirstimage2(String.valueOf(object.get("firstimage2")));
                gDTO.setMapx(String.valueOf(object.get("mapx")));
                gDTO.setMapy(String.valueOf(object.get("mapy")));
                gDTO.setMlevel(String.valueOf(object.get("mlevel")));
                gDTO.setReadcount(String.valueOf(object.get("readcount")));
                gDTO.setTitle(String.valueOf(object.get("title")));

                if (!gDTO.getFirstimage().equals("null")  && !gDTO.getFirstimage2().equals("null")) {
                    rList.add(gDTO);
                }
            }
        }

        log.info(this.getClass().getName() + "지역기반 관광정보 조회 종료");

        return rList;
    }

    @Override /* 공통정보 조회*/
    public TravelResultDTO commonTravel(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName()+" 공통정보 조회 시작!!");

        if (travelDTO == null) {
            travelDTO = new TravelDTO();
        }


        UrlUtil uu = new UrlUtil();

        String url = "http://13.124.6.224:8000";
        String api = "/Travel_info";
        String contentId = "?contentId=" + travelDTO.getContentId();
        String contentTypeId = "&contentTypeId=" + travelDTO.getContentTypeId();

        TravelResultDTO gDTO = new TravelResultDTO();

        String fullPath = uu.urlReadforString(url + api + contentId + contentTypeId);
        if (fullPath == "") {
            gDTO = new TravelResultDTO();
        } else {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(fullPath);

            gDTO.setHomepage(String.valueOf(object.get("homepage")));
            gDTO.setTel(String.valueOf(object.get("tel")));
            gDTO.setFirstimage(String.valueOf(object.get("firstimage")));
            gDTO.setFirstimage2(String.valueOf(object.get("firstimage2")));
            gDTO.setTitle(String.valueOf(object.get("title")));
            gDTO.setOverview(String.valueOf(object.get("overview")));
        }

        log.info(this.getClass().getName()+" 공통정보 조회 종료!!");
        return gDTO;
    }

    @Override /* 반복정보 조회 */
    public TravelResultDTO reTravel(TravelDTO travelDTO) throws Exception {

        log.info(this.getClass().getName()+"반복정보 조회 시작!");
        UrlUtil uu = new UrlUtil();

        String url = "http://13.124.6.224:8000";
        String api = "/Travel_Reinfo";
        String contentId = "?contentId=" + travelDTO.getContentId();
        String contentTypeId = "&contentTypeId=" + travelDTO.getContentTypeId();

        TravelResultDTO gDTO = new TravelResultDTO();

        String fullPath = uu.urlReadforString(url + api + contentId + contentTypeId);

        log.info("넘어오는 값의 길이 : " + fullPath.length());
        if (fullPath.length() < 1) {
            gDTO = new TravelResultDTO();
            return gDTO;
        } else {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(fullPath);

            if (travelDTO.getContentTypeId() == "25") {
                gDTO.setSubname(String.valueOf(object.get("subname")));
                gDTO.setSubdetailoverview(String.valueOf(object.get("subdetailoverview")));
                gDTO.setSubdetailimg(String.valueOf(object.get("subdetailimg")));
                gDTO.setSubdetailalt(String.valueOf(object.get("subdetailalt")));
            } else if (travelDTO.getContentTypeId() == "32") {
                gDTO.setRoomtitle(String.valueOf(object.get("roomtitle")));
                gDTO.setRoomsize1(String.valueOf(object.get("roomsize1")));
                gDTO.setRoomcount(String.valueOf(object.get("roomcount")));
                gDTO.setRoombasecount(String.valueOf(object.get("roombasecount")));
                gDTO.setRoommaxcount(String.valueOf(object.get("roommaxcount")));
                gDTO.setRoomoffseasonminfee1(String.valueOf(object.get("roomoffseasonminfee1")));
                gDTO.setRoomoffseasonminfee2(String.valueOf(object.get("roomoffseasonminfee2")));
                gDTO.setRoompeakseasonminfee1(String.valueOf(object.get("roompeakseasonminfee1")));
                gDTO.setRoompeakseasonminfee2(String.valueOf(object.get("roompeakseasonminfee2")));
                gDTO.setRoomintro(String.valueOf(object.get("roomintro")));
                gDTO.setRoombathfacility(String.valueOf(object.get("roombathfacility")));
                gDTO.setRoombath(String.valueOf(object.get("roombath")));
                gDTO.setRoomhometheater(String.valueOf(object.get("roomhometheater")));
                gDTO.setRoomaircondition(String.valueOf(object.get("roomaircondition")));
                gDTO.setRoomtv(String.valueOf(object.get("roomtv")));
                gDTO.setRoompc(String.valueOf(object.get("roompc")));
                gDTO.setRoomcable(String.valueOf(object.get("roomcable")));
                gDTO.setRoominternet(String.valueOf(object.get("roominternet")));
                gDTO.setRoomrefrigerator(String.valueOf(object.get("roomrefrigerator")));
                gDTO.setRoomtoiletries(String.valueOf(object.get("roomtoiletries")));
                gDTO.setRoomsofa(String.valueOf(object.get("roomsofa")));
                gDTO.setRoomcook(String.valueOf(object.get("roomcook")));
                gDTO.setRoomtable(String.valueOf(object.get("roomtable")));
                gDTO.setRoomhairdryer(String.valueOf(object.get("roomhairdryer")));
                gDTO.setRoomimg1(String.valueOf(object.get("roomimg1")));
                gDTO.setRoomimg2(String.valueOf(object.get("roomimg2")));
                gDTO.setRoomimg3(String.valueOf(object.get("roomimg3")));
                gDTO.setRoomimg4(String.valueOf(object.get("roomimg4")));
                gDTO.setRoomimg5(String.valueOf(object.get("roomimg5")));
                gDTO.setRoomimg1alt(String.valueOf(object.get("roomimg1alt")));
                gDTO.setRoomimg2alt(String.valueOf(object.get("roomimg2alt")));
                gDTO.setRoomimg3alt(String.valueOf(object.get("roomimg3alt")));
                gDTO.setRoomimg4alt(String.valueOf(object.get("roomimg4alt")));
                gDTO.setRoomimg5alt(String.valueOf(object.get("roomimg5alt")));
            } else {
                gDTO.setInfoname(String.valueOf(object.get("infoname")));
                gDTO.setInfotext(String.valueOf(object.get("infotext")));
            }
        }


        log.info(this.getClass().getName()+"반복정보 조회 종료!");
        return gDTO;
    }

    @Override /* 소개정보 조회 */
    public TravelResultDTO introduceTravel(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName()+"소개정보 조회 시작!");

        UrlUtil uu = new UrlUtil();

        String url = "http://13.124.6.224:8000";
        String api = "/Travel_moneyinfo";
        String contentId = "?contentId=" + travelDTO.getContentId();
        String contentTypeId = "&contentTypeId=" + travelDTO.getContentTypeId();

        String fullPath = uu.urlReadforString(url + api + contentId + contentTypeId);
        log.info("소개정보 조회 결과" + fullPath);

        TravelResultDTO gDTO = new TravelResultDTO();
        log.info("배열로 넘어오는 값의 길이 : " + fullPath.length());
        if (fullPath.length() < 1) {
            return gDTO;
        } else {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(fullPath);
            if (travelDTO.getContentTypeId().equals("12")) {
                gDTO.setAccomcount(String.valueOf(object.get("accomcount")));
                gDTO.setChkbabycarriage(String.valueOf(object.get("chkbabycarriage")));
                gDTO.setChkcreditcard(String.valueOf(object.get("chkcreditcard")));
                gDTO.setChkpet(String.valueOf(object.get("chkpet")));
                gDTO.setExpagerange(String.valueOf(object.get("expagerange")));
                gDTO.setExpguide(String.valueOf(object.get("expguide")));
                gDTO.setHeritage1(String.valueOf(object.get("heritage1")));
                gDTO.setHeritage2(String.valueOf(object.get("heritage2")));
                gDTO.setHeritage3(String.valueOf(object.get("heritage3")));
                gDTO.setInfocenter(String.valueOf(object.get("infocenter")));
                gDTO.setOpendate(String.valueOf(object.get("opendate")));
                gDTO.setParking(String.valueOf(object.get("parking")));
                gDTO.setRestdate(String.valueOf(object.get("restdate")));
                gDTO.setUseseason(String.valueOf(object.get("useseason")));
                gDTO.setUsetime(String.valueOf(object.get("usetime")));
            } else if (travelDTO.getContentTypeId().equals("14")) {
                gDTO.setAccomcountculture(String.valueOf(object.get("accomcountculture")));
                gDTO.setChkcreditcardculture(String.valueOf(object.get("chkcreditcardculture")));
                gDTO.setChkpetculture(String.valueOf(object.get("chkpetculture")));
                gDTO.setDiscountinfo(String.valueOf(object.get("discountinfo")));
                gDTO.setInfocenterculture(String.valueOf(object.get("infocenterculture")));
                gDTO.setParkingculture(String.valueOf(object.get("parkingculture")));
                gDTO.setParkingfee(String.valueOf(object.get("parkingfee")));
                gDTO.setRestdateculture(String.valueOf(object.get("restdateculture")));
                gDTO.setUsefee(String.valueOf(object.get("usefee")));
                gDTO.setUsetimeculture(String.valueOf(object.get("usetimeculture")));
                gDTO.setScale(String.valueOf(object.get("scale")));
                gDTO.setSpendtime(String.valueOf(object.get("spendtime")));
            } else if (travelDTO.getContentTypeId().equals("15")) {
                gDTO.setAgelimit(String.valueOf(object.get("agelimit")));
                gDTO.setBookingplace(String.valueOf(object.get("bookingplace")));
                gDTO.setDiscountinfofestival(String.valueOf(object.get("discountinfofestival")));
                gDTO.setEventstartdate(String.valueOf(object.get("eventstartdate")));
                gDTO.setEventenddate(String.valueOf(object.get("eventenddate")));
                gDTO.setEventhomepage(String.valueOf(object.get("eventhomepage")));
                gDTO.setEventplace(String.valueOf(object.get("eventplace")));
                gDTO.setFestivalgrade(String.valueOf(object.get("festivalgrade")));
                gDTO.setPlaceinfo(String.valueOf(object.get("placeinfo")));
                gDTO.setPlaytime(String.valueOf(object.get("playtime")));
                gDTO.setProgram(String.valueOf(object.get("program")));
                gDTO.setSpendtimefestival(String.valueOf(object.get("spendtimefestival")));
                gDTO.setSubevent(String.valueOf(object.get("subevent")));
                gDTO.setUsetimefestival(String.valueOf(object.get("usetimefestival")));

                log.info("가져온 이벤트 결과는 ? " + gDTO.getEventstartdate());
                log.info("가져온 이벤트 결과는 ? " + gDTO.getEventenddate());
            } else if (travelDTO.getContentTypeId().equals("25")) {
                gDTO.setDistance(String.valueOf(object.get("distance")));
                gDTO.setInfocentertourcourse(String.valueOf(object.get("infocentertourcourse")));
                gDTO.setSchedule(String.valueOf(object.get("schedule")));
                gDTO.setTaketime(String.valueOf(object.get("taketime")));
                gDTO.setTheme(String.valueOf(object.get("theme")));
            } else if (travelDTO.getContentTypeId().equals("28")) {
                gDTO.setAccomcountleports(String.valueOf(object.get("accomcountleports")));
                gDTO.setChkbabycarriageleports(String.valueOf(object.get("chkbabycarriageleports")));
                gDTO.setChkpetleports(String.valueOf(object.get("chkcreditcardleports")));
                gDTO.setExpagerangeleports(String.valueOf(object.get("expagerangeleports")));
                gDTO.setInfocenterleports(String.valueOf(object.get("infocenterleports")));
                gDTO.setOpenperiod(String.valueOf(object.get("openperiod")));
                gDTO.setParkingfeeleports(String.valueOf(object.get("parkingfeeleports")));
                gDTO.setParkingleports(String.valueOf(object.get("parkingleports")));
                gDTO.setReservation(String.valueOf(object.get("reservation")));
                gDTO.setRestdateleports(String.valueOf(object.get("restdateleports")));
                gDTO.setScaleleports(String.valueOf(object.get("scaleleports")));
                gDTO.setUsefeeleports(String.valueOf(object.get("usefeeleports")));
                gDTO.setUsetimeleports(String.valueOf(object.get("usetimeleports")));
            } else if (travelDTO.getContentTypeId().equals("32")) {
                gDTO.setAccomcountlodging(String.valueOf(object.get("accomcountlodging")));
                gDTO.setBenikia(String.valueOf(object.get("benikia")));
                gDTO.setCheckintime(String.valueOf(object.get("checkintime")));
                gDTO.setCheckouttime(String.valueOf(object.get("checkouttime")));
                gDTO.setChkcooking(String.valueOf(object.get("chkcooking")));
                gDTO.setFoodplace(String.valueOf(object.get("foodpce")));
                gDTO.setGoodstay(String.valueOf(object.get("goodstay")));
                gDTO.setHanok(String.valueOf(object.get("hanok")));
                gDTO.setInfocenterlodging(String.valueOf(object.get("infocenterlodging")));
                gDTO.setParkinglodging(String.valueOf(object.get("parkinglodging")));
                gDTO.setPickup(String.valueOf(object.get("pickup")));
                gDTO.setRoomcount(String.valueOf(object.get("roomcount")));
                gDTO.setReservationlodging(String.valueOf(object.get("reservationlodging")));
                gDTO.setReservationurl(String.valueOf(object.get("reservationurl")));
                gDTO.setRoomtype(String.valueOf(object.get("roomtype")));
                gDTO.setScalelodging(String.valueOf(object.get("scalelodging")));
                gDTO.setSubfacility(String.valueOf(object.get("subfacility")));
                gDTO.setBarbecue(String.valueOf(object.get("barbecue")));
                gDTO.setBeauty(String.valueOf(object.get("beauty")));
                gDTO.setBeverage(String.valueOf(object.get("beverage")));
                gDTO.setBicycle(String.valueOf(object.get("bicycle")));
                gDTO.setCampfire(String.valueOf(object.get("campfire")));
                gDTO.setFitness(String.valueOf(object.get("fitness")));
                gDTO.setKaraoke(String.valueOf(object.get("karaoke")));
                gDTO.setPublicbath(String.valueOf(object.get("publicbath")));
                gDTO.setPublicpc(String.valueOf(object.get("publicpc")));
                gDTO.setSauna(String.valueOf(object.get("sauna")));
                gDTO.setSeminar(String.valueOf(object.get("seminar")));
                gDTO.setSports(String.valueOf(object.get("sports")));

            } else if (travelDTO.getContentTypeId().equals("38")) {
                gDTO.setChkbabycarriageshopping(String.valueOf(object.get("chkbabycarriageshopping")));
                gDTO.setChkcreditcardshopping(String.valueOf(object.get("chkcreditcardshopping")));
                gDTO.setChkpetshopping(String.valueOf(object.get("chkpetshopping")));
                gDTO.setCulturecenter(String.valueOf(object.get("culturecenter")));
                gDTO.setFairday(String.valueOf(object.get("fairday")));
                gDTO.setInfocentershopping(String.valueOf(object.get("infocentershopping")));
                gDTO.setOpendateshopping(String.valueOf(object.get("opendateshopping")));
                gDTO.setOpentime(String.valueOf(object.get("opentime")));
                gDTO.setParkingshopping(String.valueOf(object.get("parkingshopping")));
                gDTO.setRestdateshopping(String.valueOf(object.get("restdateshopping")));
                gDTO.setRestroom(String.valueOf(object.get("restroom")));
                gDTO.setSaleitem(String.valueOf(object.get("saleitem")));
                gDTO.setSaleitemcost(String.valueOf(object.get("saleitemcost")));
                gDTO.setScaleshopping(String.valueOf(object.get("scaleshopping")));
                gDTO.setShopguide(String.valueOf(object.get("shopguide")));
            } else if (travelDTO.getContentTypeId().equals("39")) {
                gDTO.setChkcreditcard(String.valueOf(object.get("chkcreditcardfood")));
                gDTO.setDiscountinfofood(String.valueOf(object.get("discountinfofood")));
                gDTO.setFirstmenu(String.valueOf(object.get("firstmenu")));
                gDTO.setInfocenterfood(String.valueOf(object.get("infocenterfood")));
                gDTO.setKidsfacility(String.valueOf(object.get("kidsfacility")));
                gDTO.setOpentimefood(String.valueOf(object.get("opentimefood")));
                gDTO.setPacking(String.valueOf(object.get("packing")));
                gDTO.setParkingfood(String.valueOf(object.get("parkingfood")));
                gDTO.setRestdatefood(String.valueOf(object.get("restdatefood")));
                gDTO.setReservationfood(String.valueOf(object.get("reservationfood")));
                gDTO.setScalefood(String.valueOf(object.get("scalefood")));
                gDTO.setSeat(String.valueOf(object.get("seat")));
                gDTO.setSmoking(String.valueOf(object.get("smoking")));
                gDTO.setTreatmenu(String.valueOf(object.get("treatmenu")));
            }
        }


        log.info(this.getClass().getName()+"소개정보 조회 종료!");
        return gDTO;
    }

    @Override /* 반복정보 리스트 조회 시작! */
    public List<TravelResultDTO> listTravelReInfo(TravelDTO travelDTO) throws Exception {
        log.info(this.getClass().getName()+"리스트 반복정보 조회 시작!");

        UrlUtil uu = new UrlUtil();

        String url = "http://13.124.6.224:8000";
        String api = "/Travel_listReinfo";
        String contentId = "?contentId=" + travelDTO.getContentId();
        String contentTypeId = "&contentTypeId=" + travelDTO.getContentTypeId();

        TravelResultDTO gDTO = new TravelResultDTO();

        String fullPath = uu.urlReadforString(url + api + contentId + contentTypeId);
        log.info("반복 리스트 조회 결과!!" + fullPath);
        List<TravelResultDTO> rList = new ArrayList<>();

        log.info("넘어오는 값의 길이 : " + fullPath.length());
        if (fullPath.length() < 1) {
            return rList;
        } else {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(fullPath);
            for (int i = 0; i < jsonArray.size(); i++) {
                gDTO = new TravelResultDTO();
                log.info(String.valueOf(jsonArray.size()));
                JSONObject object = (JSONObject) jsonArray.get(i);

                if (travelDTO.getContentTypeId().equals("25")) {
                    gDTO.setSubname(String.valueOf(object.get("subname")));
                    gDTO.setSubdetailoverview(String.valueOf(object.get("subdetailoverview")));
                    gDTO.setSubdetailimg(String.valueOf(object.get("subdetailimg")));
                    gDTO.setSubdetailalt(String.valueOf(object.get("subdetailalt")));
                    rList.add(gDTO);
                } else if (travelDTO.getContentTypeId().equals("32")) {
                    gDTO.setRoomtitle(String.valueOf(object.get("roomtitle")));
                    gDTO.setRoomsize1(String.valueOf(object.get("roomsize1")));
                    gDTO.setRoomcount(String.valueOf(object.get("roomcount")));
                    gDTO.setRoombasecount(String.valueOf(object.get("roombasecount")));
                    gDTO.setRoommaxcount(String.valueOf(object.get("roommaxcount")));
                    gDTO.setRoomoffseasonminfee1(String.valueOf(object.get("roomoffseasonminfee1")));
                    gDTO.setRoomoffseasonminfee2(String.valueOf(object.get("roomoffseasonminfee2")));
                    gDTO.setRoompeakseasonminfee1(String.valueOf(object.get("roompeakseasonminfee1")));
                    gDTO.setRoompeakseasonminfee2(String.valueOf(object.get("roompeakseasonminfee2")));
                    gDTO.setRoomintro(String.valueOf(object.get("roomintro")));
                    gDTO.setRoombathfacility(String.valueOf(object.get("roombathfacility")));
                    gDTO.setRoombath(String.valueOf(object.get("roombath")));
                    gDTO.setRoomhometheater(String.valueOf(object.get("roomhometheater")));
                    gDTO.setRoomaircondition(String.valueOf(object.get("roomaircondition")));
                    gDTO.setRoomtv(String.valueOf(object.get("roomtv")));
                    gDTO.setRoompc(String.valueOf(object.get("roompc")));
                    gDTO.setRoomcable(String.valueOf(object.get("roomcable")));
                    gDTO.setRoominternet(String.valueOf(object.get("roominternet")));
                    gDTO.setRoomrefrigerator(String.valueOf(object.get("roomrefrigerator")));
                    gDTO.setRoomtoiletries(String.valueOf(object.get("roomtoiletries")));
                    gDTO.setRoomsofa(String.valueOf(object.get("roomsofa")));
                    gDTO.setRoomcook(String.valueOf(object.get("roomcook")));
                    gDTO.setRoomtable(String.valueOf(object.get("roomtable")));
                    gDTO.setRoomhairdryer(String.valueOf(object.get("roomhairdryer")));
                    gDTO.setRoomimg1(String.valueOf(object.get("roomimg1")));
                    gDTO.setRoomimg2(String.valueOf(object.get("roomimg2")));
                    gDTO.setRoomimg3(String.valueOf(object.get("roomimg3")));
                    gDTO.setRoomimg4(String.valueOf(object.get("roomimg4")));
                    gDTO.setRoomimg5(String.valueOf(object.get("roomimg5")));
                    gDTO.setRoomimg1alt(String.valueOf(object.get("roomimg1alt")));
                    gDTO.setRoomimg2alt(String.valueOf(object.get("roomimg2alt")));
                    gDTO.setRoomimg3alt(String.valueOf(object.get("roomimg3alt")));
                    gDTO.setRoomimg4alt(String.valueOf(object.get("roomimg4alt")));
                    gDTO.setRoomimg5alt(String.valueOf(object.get("roomimg5alt")));
                    rList.add(gDTO);
                } else {
                    gDTO.setInfoname(String.valueOf(object.get("infoname")));
                    gDTO.setInfotext(String.valueOf(object.get("infotext")));
                    rList.add(gDTO);
                }
            }
        }


        log.info(this.getClass().getName()+"리스트 반복정보 조회 종료!");
        return rList;
    }

    @Override /* 회원 선택여행 조회 시작! */
    public List<TravelResultDTO> userChoiceTravel(List<TravelDTO> travelDTOList) throws Exception {
        log.info(this.getClass().getName()+"회원 여행지 선택 시작!");

        List<TravelResultDTO> pList = new ArrayList<>();

        for (int i = 0; i < travelDTOList.size(); i++) {
            String areaCode = travelDTOList.get(i).getAreaCode();
            String contenttypeId = travelDTOList.get(i).getContentTypeId();
            String keyWord = travelDTOList.get(i).getKeyWord();

            UrlUtil uu = new UrlUtil();
            String url = "http://13.124.6.224:8000";
            String api = "/UserKeyWord_travelInfo";
            String nareaCode = "?areaCode=" + areaCode;
            String nContenttypeId = "&contenttypeId=" + contenttypeId;
            String nkeyWord = "&keyword=";

            TravelResultDTO pDTO;


            String fullPath = uu.urlReadforString(url + api + nareaCode + nContenttypeId + nkeyWord + URLEncoder.encode(keyWord, "UTF-8"));
            log.info("가져온 키워드 조회 결과는 ? " + fullPath);

            JSONParser parser = new JSONParser();

            pDTO = new TravelResultDTO();

            JSONObject object = (JSONObject) parser.parse(fullPath);
            pDTO.setAddr1(String.valueOf(object.get("addr1")));
            pDTO.setContenttypeid(String.valueOf(object.get("contenttypeid")));
            pDTO.setContentid(String.valueOf(object.get("contentid")));
            pDTO.setReadcount(String.valueOf(object.get("readcount")));
            pDTO.setFirstimage(String.valueOf(object.get("firstimage")));
            pDTO.setFirstimage2(String.valueOf(object.get("firstimage2")));
            pDTO.setTitle(String.valueOf(object.get("title")));
            pDTO.setMapx(String.valueOf(object.get("mapx")));
            pDTO.setMapy(String.valueOf(object.get("mapy")));

            if (!pDTO.getFirstimage().equals("null") && !pDTO.getFirstimage2().equals("null")) {
                pList.add(pDTO);
            }
        }

        log.info(this.getClass().getName()+"회원 여행지 선택 시작!!");
        return pList;
    }




}
