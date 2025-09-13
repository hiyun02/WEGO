package kopo.poly.util;

public class ConvertAreaUtil {
    public static String convertLocationCode(String location) {
        String returnLocation = "";
        switch (location) {
            case "1":
                returnLocation = "서울";
                break;
            case "2":
                returnLocation = "인천";
                break;
            case "3":
                returnLocation = "대전";
                break;
            case "4":
                returnLocation = "대구";
                break;
            case "5":
                returnLocation = "광주";
                break;
            case "6":
                returnLocation = "부산";
                break;
            case "7":
                returnLocation = "울산";
                break;
            case "31":
                returnLocation = "경기도";
                break;
            case "33":
                returnLocation = "충청북도";
                break;
            case "34":
                returnLocation = "충청남도";
                break;
            case "35":
                returnLocation = "경상북도";
                break;
            case "36":
                returnLocation = "경상남도";
                break;
            case "37":
                returnLocation = "전라북도";
                break;
            case "38":
                returnLocation = "전라남도";
                break;
            case "39":
                returnLocation = "제주도";
                break;
        }
        return returnLocation;
    }

    public static String convertCityCode(String city, String location) {
        String returnCity = "";
        switch (location) {
            case "서울":
                switch (city) {
                    case "1":
                        returnCity = "강남구";
                        break;
                    case "2":
                        returnCity = "강동구";
                        break;
                    case "3":
                        returnCity = "강북구";
                        break;
                    case "4":
                        returnCity = "강서구";
                        break;
                    case "5":
                        returnCity = "관악구";
                        break;
                    case "6":
                        returnCity = "광진구";
                        break;
                    case "7":
                        returnCity = "구로구";
                        break;
                    case "8":
                        returnCity = "금천구";
                        break;
                    case "9":
                        returnCity = "노원구";
                        break;
                    case "10":
                        returnCity = "도봉구";
                        break;
                    case "11":
                        returnCity = "동대문구";
                        break;
                    case "12":
                        returnCity = "동작구";
                        break;
                    case "13":
                        returnCity = "마포구";
                        break;
                    case "14":
                        returnCity = "서대문구";
                        break;
                    case "15":
                        returnCity = "서초구";
                        break;
                    case "16":
                        returnCity = "성동구";
                        break;
                    case "17":
                        returnCity = "성북구";
                        break;
                    case "18":
                        returnCity = "송파구";
                        break;
                    case "19":
                        returnCity = "양천구";
                        break;
                    case "20":
                        returnCity = "영등포구";
                        break;
                    case "21":
                        returnCity = "용산구";
                        break;
                    case "22":
                        returnCity = "은평구";
                        break;
                    case "23":
                        returnCity = "종로구";
                        break;
                    case "24":
                        returnCity = "중구";
                        break;
                    case "25":
                        returnCity = "중랑구";
                        break;
                }
                return returnCity;
            case "인천":
                switch (city) {
                    case "1":
                        returnCity = "강화군";
                        break;
                    case "2":
                        returnCity = "계양구";
                        break;
                    case "3":
                        returnCity = "미추홀구";
                        break;
                    case "4":
                        returnCity = "남동구";
                        break;
                    case "5":
                        returnCity = "동구";
                        break;
                    case "6":
                        returnCity = "부평구";
                        break;
                    case "7":
                        returnCity = "서구";
                        break;
                    case "8":
                        returnCity = "연수구";
                        break;
                    case "9":
                        returnCity = "옹진군";
                        break;
                    case "10":
                        returnCity = "중구";
                        break;
                }
                return returnCity;
            case "대전":
                switch (city) {
                    case "1":
                        returnCity = "대덕구";
                        break;
                    case "2":
                        returnCity = "동구";
                        break;
                    case "3":
                        returnCity = "서구";
                        break;
                    case "4":
                        returnCity = "유성구";
                        break;
                    case "5":
                        returnCity = "중구";
                        break;
                }
                return returnCity;
            case "대구":
                switch (city) {
                    case "1":
                        returnCity = "남구";
                        break;
                    case "2":
                        returnCity = "달서구";
                        break;
                    case "3":
                        returnCity = "달성군";
                        break;
                    case "4":
                        returnCity = "동구";
                        break;
                    case "5":
                        returnCity = "북구";
                        break;
                    case "6":
                        returnCity = "서구";
                        break;
                    case "7":
                        returnCity = "수성구";
                        break;
                    case "8":
                        returnCity = "중구";
                        break;
                }
                return returnCity;
            case "광주":
                switch (city) {
                    case "1":
                        returnCity = "광산구";
                        break;
                    case "2":
                        returnCity = "남구";
                        break;
                    case "3":
                        returnCity = "동구";
                        break;
                    case "4":
                        returnCity = "북구";
                        break;
                    case "5":
                        returnCity = "서구";
                        break;
                }
                return returnCity;
            case "부산":
                switch (city) {
                    case "1":
                        returnCity = "강서구";
                        break;
                    case "2":
                        returnCity = "금정구";
                        break;
                    case "3":
                        returnCity = "기장군";
                        break;
                    case "4":
                        returnCity = "남구";
                        break;
                    case "5":
                        returnCity = "동구";
                        break;
                    case "6":
                        returnCity = "동래구";
                        break;
                    case "7":
                        returnCity = "부산진구";
                        break;
                    case "8":
                        returnCity = "북구";
                        break;
                    case "9":
                        returnCity = "사상구";
                        break;
                    case "10":
                        returnCity = "사하구";
                        break;
                    case "11":
                        returnCity = "서구";
                        break;
                    case "12":
                        returnCity = "수영구";
                        break;
                    case "13":
                        returnCity = "연제구";
                        break;
                    case "14":
                        returnCity = "영도구";
                        break;
                    case "15":
                        returnCity = "중구";
                        break;
                    case "16":
                        returnCity = "해운대구";
                        break;
                }
                return returnCity;
            case "울산":
                switch (city) {
                    case "1":
                        returnCity = "중구";
                        break;
                    case "2":
                        returnCity = "남구";
                        break;
                    case "3":
                        returnCity = "동구";
                        break;
                    case "4":
                        returnCity = "북구";
                        break;
                    case "5":
                        returnCity = "울주군";
                        break;
                }
                return returnCity;
            case "경기도":
                switch (city) {
                    case "1":
                        returnCity = "가평군";
                        break;
                    case "2":
                        returnCity = "고양시";
                        break;
                    case "3":
                        returnCity = "과천시";
                        break;
                    case "4":
                        returnCity = "광명시";
                        break;
                    case "5":
                        returnCity = "광주시";
                        break;
                    case "6":
                        returnCity = "구리시";
                        break;
                    case "7":
                        returnCity = "군포시";
                        break;
                    case "8":
                        returnCity = "김포시";
                        break;
                    case "9":
                        returnCity = "남양주시";
                        break;
                    case "10":
                        returnCity = "동두천시";
                        break;
                    case "11":
                        returnCity = "부천시";
                        break;
                    case "12":
                        returnCity = "성남시";
                        break;
                    case "13":
                        returnCity = "수원시";
                        break;
                    case "14":
                        returnCity = "시흥시";
                        break;
                    case "15":
                        returnCity = "안산시";
                        break;
                    case "16":
                        returnCity = "안성시";
                        break;
                    case "17":
                        returnCity = "안양시";
                        break;
                    case "18":
                        returnCity = "양주시";
                        break;
                    case "19":
                        returnCity = "양평군";
                        break;
                    case "20":
                        returnCity = "여주시";
                        break;
                    case "21":
                        returnCity = "연천군";
                        break;
                    case "22":
                        returnCity = "오산시";
                        break;
                    case "23":
                        returnCity = "용인시";
                        break;
                    case "24":
                        returnCity = "의왕시";
                        break;
                    case "25":
                        returnCity = "의정부시";
                        break;
                    case "26":
                        returnCity = "이천시";
                        break;
                    case "27":
                        returnCity = "파주시";
                        break;
                    case "28":
                        returnCity = "평택시";
                        break;
                    case "29":
                        returnCity = "포천시";
                        break;
                    case "30":
                        returnCity = "하남시";
                        break;
                    case "31":
                        returnCity = "화성시";
                        break;
                }
                return returnCity;
            case "충청북도":
                switch (city) {
                    case "1":
                        returnCity = "괴산군";
                        break;
                    case "2":
                        returnCity = "단양군";
                        break;
                    case "3":
                        returnCity = "보은군";
                        break;
                    case "4":
                        returnCity = "영동군";
                        break;
                    case "5":
                        returnCity = "옥천군";
                        break;
                    case "6":
                        returnCity = "음성군";
                        break;
                    case "7":
                        returnCity = "제천시";
                        break;
                    case "8":
                        returnCity = "진천군";
                        break;
                    case "9":
                        returnCity = "청원군";
                        break;
                    case "10":
                        returnCity = "청주시";
                        break;
                    case "11":
                        returnCity = "충주시";
                        break;
                    case "12":
                        returnCity = "증평군";
                        break;
                }
                return returnCity;
            case "충청남도":
                switch (city) {
                    case "1":
                        returnCity = "공주시";
                        break;
                    case "2":
                        returnCity = "금산군";
                        break;
                    case "3":
                        returnCity = "논산시";
                        break;
                    case "4":
                        returnCity = "당진시";
                        break;
                    case "5":
                        returnCity = "보령시";
                        break;
                    case "6":
                        returnCity = "부여군";
                        break;
                    case "7":
                        returnCity = "서산시";
                        break;
                    case "8":
                        returnCity = "서천군";
                        break;
                    case "9":
                        returnCity = "아산시";
                        break;
                    case "10":
                        returnCity = "예산군";
                        break;
                    case "11":
                        returnCity = "천안시";
                        break;
                    case "12":
                        returnCity = "청양군";
                        break;
                    case "13":
                        returnCity = "태안군";
                        break;
                    case "14":
                        returnCity = "홍성군";
                        break;
                    case "15":
                        returnCity = "계룡시";
                        break;
                }
                return returnCity;
            case "경상북도":
                switch (city) {
                    case "1":
                        returnCity = "경산시";
                        break;
                    case "2":
                        returnCity = "경주시";
                        break;
                    case "3":
                        returnCity = "고령군";
                        break;
                    case "4":
                        returnCity = "구미시";
                        break;
                    case "5":
                        returnCity = "군위군";
                        break;
                    case "6":
                        returnCity = "김천시";
                        break;
                    case "7":
                        returnCity = "문경시";
                        break;
                    case "8":
                        returnCity = "봉화군";
                        break;
                    case "9":
                        returnCity = "상주시";
                        break;
                    case "10":
                        returnCity = "성주군";
                        break;
                    case "11":
                        returnCity = "안동시";
                        break;
                    case "12":
                        returnCity = "영덕군";
                        break;
                    case "13":
                        returnCity = "영양군";
                        break;
                    case "14":
                        returnCity = "영주시";
                        break;
                    case "15":
                        returnCity = "영천시";
                        break;
                    case "16":
                        returnCity = "예천군";
                        break;
                    case "17":
                        returnCity = "울릉군";
                        break;
                    case "18":
                        returnCity = "울진군";
                        break;
                    case "19":
                        returnCity = "의성군";
                        break;
                    case "20":
                        returnCity = "청도군";
                        break;
                    case "21":
                        returnCity = "청송군";
                        break;
                    case "22":
                        returnCity = "칠곡군";
                        break;
                    case "23":
                        returnCity = "포항시";
                        break;
                }
                return returnCity;
            case "경상남도":
                switch (city) {
                    case "1":
                        returnCity = "거제시";
                        break;
                    case "2":
                        returnCity = "거창군";
                        break;
                    case "3":
                        returnCity = "고성군";
                        break;
                    case "4":
                        returnCity = "김해시";
                        break;
                    case "5":
                        returnCity = "남해군";
                        break;
                    case "6":
                        returnCity = "마산시";
                        break;
                    case "7":
                        returnCity = "밀양시";
                        break;
                    case "8":
                        returnCity = "사천시";
                        break;
                    case "9":
                        returnCity = "산청군";
                        break;
                    case "10":
                        returnCity = "양산시";
                        break;
                    case "11":
                        returnCity = "의령군";
                        break;
                    case "12":
                        returnCity = "진주시";
                        break;
                    case "13":
                        returnCity = "진해시";
                        break;
                    case "14":
                        returnCity = "창녕군";
                        break;
                    case "15":
                        returnCity = "창원시";
                        break;
                    case "16":
                        returnCity = "통영시";
                        break;
                    case "17":
                        returnCity = "하동군";
                        break;
                    case "18":
                        returnCity = "함안군";
                        break;
                    case "19":
                        returnCity = "함양군";
                        break;
                    case "20":
                        returnCity = "합천군";
                        break;
                }
                return returnCity;
            case "전라북도":
                switch (city) {
                    case "1":
                        returnCity = "고창군";
                        break;
                    case "2":
                        returnCity = "군산시";
                        break;
                    case "3":
                        returnCity = "김제시";
                        break;
                    case "4":
                        returnCity = "남원시";
                        break;
                    case "5":
                        returnCity = "무주군";
                        break;
                    case "6":
                        returnCity = "부안군";
                        break;
                    case "7":
                        returnCity = "순창군";
                        break;
                    case "8":
                        returnCity = "완주군";
                        break;
                    case "9":
                        returnCity = "익산시";
                        break;
                    case "10":
                        returnCity = "임실군";
                        break;
                    case "11":
                        returnCity = "장수군";
                        break;
                    case "12":
                        returnCity = "전주시";
                        break;
                    case "13":
                        returnCity = "정읍시";
                        break;
                    case "14":
                        returnCity = "진안군";
                        break;
                }
                return returnCity;
            case "전라남도":
                switch (city) {
                    case "1":
                        returnCity = "강진군";
                        break;
                    case "2":
                        returnCity = "고흥군";
                        break;
                    case "3":
                        returnCity = "곡성군";
                        break;
                    case "4":
                        returnCity = "광양시";
                        break;
                    case "5":
                        returnCity = "구례군";
                        break;
                    case "6":
                        returnCity = "나주시";
                        break;
                    case "7":
                        returnCity = "담양군";
                        break;
                    case "8":
                        returnCity = "목포시";
                        break;
                    case "9":
                        returnCity = "무안군";
                        break;
                    case "10":
                        returnCity = "보성군";
                        break;
                    case "11":
                        returnCity = "순천시";
                        break;
                    case "12":
                        returnCity = "신안군";
                        break;
                    case "13":
                        returnCity = "여수시";
                        break;
                    case "14":
                        returnCity = "영광군";
                        break;
                    case "15":
                        returnCity = "영암군";
                        break;
                    case "16":
                        returnCity = "완도군";
                        break;
                    case "17":
                        returnCity = "장성군";
                        break;
                    case "18":
                        returnCity = "장흥군";
                        break;
                    case "19":
                        returnCity = "진도군";
                        break;
                    case "20":
                        returnCity = "함평군";
                        break;
                    case "21":
                        returnCity = "해남군";
                        break;
                    case "22":
                        returnCity = "화순군";
                        break;
                }
                return returnCity;
            case "제주도":
                switch (city) {
                    case "1":
                        returnCity = "남제주군";
                        break;
                    case "2":
                        returnCity = "북제주군";
                        break;
                    case "3":
                        returnCity = "서귀포시";
                        break;
                    case "4":
                        returnCity = "제주시";
                        break;
                }
                return returnCity;
        }
        return "";
    }
}
