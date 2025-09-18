package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.BoardDTO;
import kopo.poly.dto.Board_PostDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service("BoardService")
public class BoardService implements IBoardService {

    private final RestTemplate restTemplate;

    @Override
    public Long insertBoardInfo(BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertBoardInfo Start !!");
        log.info(bDTO.getStartDay());
        log.info(bDTO.getCity());
        log.info(bDTO.getEndDay());
        log.info(bDTO.getGrade());
        log.info(bDTO.getHashtag());
        log.info(bDTO.getLocation());
        log.info(bDTO.getUserName());


        /*헤더 설정*/
        HttpHeaders httpHeaders = new HttpHeaders();
        //요청할 데이터 타입 설정 => json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        /*바디 설정*/

        /*HttpEntity에 값 담기(요청 값)*/
        HttpEntity requestEntity = new HttpEntity(bDTO, httpHeaders);

        /*HttpEntity에 값 담기(응답 값)*/
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:11000/board/insertBoardInfo", HttpMethod.POST,requestEntity, String.class);
        log.info("값 발송 !!");
        /*파싱*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        Long boardSeq = objectMapper.readValue(responseEntity.getBody(), Long.class);


        log.info(this.getClass().getName() + ".insertBoardInfo End !!");
        return boardSeq;
    }

    @Override
    public int insertBoardPostInfo(List<Board_PostDTO> bpList) throws Exception {
        log.info(this.getClass().getName() + ".insertBoardPostInfo Start !!");

        /*헤더 설정*/
        HttpHeaders httpHeaders = new HttpHeaders();
        //요청할 데이터 타입 설정 => json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        /*바디 설정*/

        /*HttpEntity에 값 담기(요청 값)*/
        HttpEntity requestEntity = new HttpEntity(bpList, httpHeaders);

        /*HttpEntity에 값 담기(응답 값)*/
        HttpEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/insertBoardPostInfo", requestEntity, String.class);
        log.info("값 발송 !!");
        /*파싱*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        int result = objectMapper.readValue(responseEntity.getBody(), Integer.class);


        log.info(this.getClass().getName() + ".insertBoardPostInfo End !!");
        return result;
    }

    @Override
    public List<BoardDTO> getBoardList(int pageSize) throws Exception {
        log.info(this.getClass().getName() + "getBoardList start !!");

        /*헤더 설정*/
        HttpHeaders httpHeaders = new HttpHeaders();
        //요청할 데이터 타입 설정 => json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        /*바디 설정*/

        Map<String, Integer> pageSizeMap = new HashMap<>();
        pageSizeMap.put("pageSize", pageSize);
        /*HttpEntity에 값 담기(요청 값)*/
        HttpEntity requestEntity = new HttpEntity(pageSizeMap,httpHeaders);

        /*HttpEntity에 값 담기(응답 값)*/
        HttpEntity<List> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/getBoardList", requestEntity, List.class);
        log.info("값 발송 !!");
        /*파싱*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        List<BoardDTO> result = new ObjectMapper().convertValue(responseEntity.getBody(),
                new TypeReference<List<BoardDTO>>() {
                });

        log.info("result size : " + result.size());
        log.info(this.getClass().getName() + "getBoardList End !!");
        return result;
    }

    @Override
    public List<BoardDTO> getHashBoardList(String hashTag) throws Exception {
        log.info(this.getClass().getName() + " .getHashBoardList Start !!");

        /*헤더 설정*/
        HttpHeaders httpHeaders = new HttpHeaders();
        //요청할 데이터 타입 설정 => json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        /*바디 설정*/
        /*HttpEntity에 값 담기(요청 값)*/
        HttpEntity requestEntity = new HttpEntity(hashTag, httpHeaders);

        /*HttpEntity에 값 담기(응답 값)*/
        HttpEntity<List> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/getHashBoardList", requestEntity, List.class);
        log.info("값 발송 !!");
        /*파싱*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        List<BoardDTO> result = new ObjectMapper().convertValue(responseEntity.getBody(),
                new TypeReference<List<BoardDTO>>() {
                });
        log.info(this.getClass().getName() + "getHashBoardList End !!");
        return result;
    }

    @Override
    public BoardDTO getBoard(BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + "getBoardList start !!");

        /*헤더 설정*/
        HttpHeaders httpHeaders = new HttpHeaders();
        //요청할 데이터 타입 설정 => json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        /*바디 설정*/

        /*HttpEntity에 값 담기(요청 값)*/
        HttpEntity requestEntity = new HttpEntity(bDTO, httpHeaders);

        /*HttpEntity에 값 담기(응답 값)*/
        HttpEntity<BoardDTO> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/getBoard", requestEntity, BoardDTO.class);
        log.info("값 발송 !!");
        /*파싱*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        BoardDTO result = new ObjectMapper().convertValue(responseEntity.getBody(),BoardDTO.class);

        log.info(this.getClass().getName() + "getBoardList End !!");

        return result;
    }

    @Override
    public List<Board_PostDTO> getBoardPostList(Board_PostDTO bpDTO) throws Exception {
        log.info(this.getClass().getName() + "getBoardPostList Start !!");
        /*헤더 설정*/
        HttpHeaders httpHeaders = new HttpHeaders();
        //요청할 데이터 타입 설정 => json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        /*바디 설정*/

        /*HttpEntity에 값 담기(요청 값)*/
        HttpEntity requestEntity = new HttpEntity(bpDTO, httpHeaders);

        /*HttpEntity에 값 담기(응답 값)*/
        HttpEntity<List> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/getBoardPostList", requestEntity, List.class);
        log.info("값 발송 !!");
        /*파싱*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        List<Board_PostDTO> result = new ObjectMapper().convertValue(responseEntity.getBody(),
                new TypeReference<List<Board_PostDTO>>() {
                });
        log.info(this.getClass().getName() + "getBoardPostList End !!");
        return result;
    }

    @Override
    public Long updateBoardInfo(BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateBoardInfo Start !!");
        /*헤더 설정*/
        HttpHeaders httpHeaders = new HttpHeaders();
        //요청할 데이터 타입 설정 => json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        /*바디 설정*/

        /*HttpEntity에 값 담기(요청 값)*/
        HttpEntity requestEntity = new HttpEntity(bDTO, httpHeaders);

        /*HttpEntity에 값 담기(응답 값)*/
        HttpEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/updateBoardInfo", requestEntity, String.class);
        log.info("값 발송 !!");
        /*파싱*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        Long boardSeq = objectMapper.readValue(responseEntity.getBody(), Long.class);


        log.info(this.getClass().getName() + ".updateBoardInfo End !!");
        return boardSeq;

    }

    @Override
    public int updateBoardPostInfo(List<Board_PostDTO> bpList) throws Exception {
        log.info(this.getClass().getName() + ".updateBoardPostInfo Start !!");
        /*헤더 설정*/
        HttpHeaders httpHeaders = new HttpHeaders();
        //요청할 데이터 타입 설정 => json
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        /*바디 설정*/

        /*HttpEntity에 값 담기(요청 값)*/
        HttpEntity requestEntity = new HttpEntity(bpList, httpHeaders);

        /*HttpEntity에 값 담기(응답 값)*/
        HttpEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/updateBoardPostInfo", requestEntity, String.class);
        log.info("값 발송 !!");
        /*파싱*/
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        int result = objectMapper.readValue(responseEntity.getBody(), Integer.class);


        log.info(this.getClass().getName() + ".updateBoardPostInfo End !!");
        return result;
    }

    @Override
    public int boardDelete(BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".boardDelete Start !!");

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity(bDTO, httpHeaders);

        HttpEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/boardDelete", requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        int result = objectMapper.readValue(responseEntity.getBody(), Integer.class);

        log.info(this.getClass().getName() + ".boardDelete End !!");
        return result;
    }

    @Override
    public int boardPostDelete(Board_PostDTO bpDTO) throws Exception {
        log.info(this.getClass().getName() + ".boardPostDelete Start !!");

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity(bpDTO, httpHeaders);

        HttpEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:11000/board/boardPostDelete", requestEntity, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        int result = objectMapper.readValue(responseEntity.getBody(), Integer.class);
        log.info(this.getClass().getName() + ".boardPostDelete End !!");
        return result;
    }

    @Override
    public String getUserName(String user_name) throws Exception {
        log.info(this.getClass().getName() + " getUserName Start !!");
        UserDTO uDTO = new UserDTO();


        log.info(this.getClass().getName() + " getUserName End !!");
        return null;
    }


}
