package kopo.poly.util;

public class TravelUtil {

    public static String Travel_type(String keyWord){

        String type = "";

        if(keyWord.equals("힐링")){
            type = "12";
        }
        if(keyWord.equals("놀이공원") ){
            type = "12";
        }
        if(keyWord.equals("자연") ){
            type = "12";
        }
        if(keyWord.equals("역사")){
            type = "12";
        }
        if(keyWord.equals("고궁")){
            type = "12";
        }
        if(keyWord.equals("추억")){
            type = "12";
        }
        if(keyWord.equals("건물")){
            type = "12";
        }
        if(keyWord.equals("빌딩")){
            type = "12";
        }
        if(keyWord.equals("바다")){
            type = "12";
        }
        if(keyWord.equals("스포츠")){
            type = "25";
        }
        if(keyWord.equals("빠지")){
            type = "25";
        }
        if(keyWord.equals("익스트림")){
            type = "25";
        }
        if(keyWord.equals("운동")){
            type = "25";
        }
        if(keyWord.equals("호캉스")){
            type = "32";
        }
        if(keyWord.equals("호텔")){
            type = "32";
        }
        if(keyWord.equals("맛집")){
            type = "39";
        }
        if(keyWord.equals("카페")){
            type = "39";
        }
        return type;
    }
}
