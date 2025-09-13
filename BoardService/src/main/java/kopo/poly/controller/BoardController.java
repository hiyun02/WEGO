package kopo.poly.controller;

import kopo.poly.dto.BoardDTO;
import kopo.poly.dto.Board_PostDTO;
import kopo.poly.dto.NlpDataDTO;
import kopo.poly.service.IBoarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final IBoarService boardService;

    @PostMapping(value = "/board/insertBoardInfo")
    @ResponseBody
    public ResponseEntity<?> insertBoardInfo(@RequestBody BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertBoardInfo Start !!");

        log.info("start_day : " + bDTO.getStartDay());
        log.info("endDay : " + bDTO.getEndDay());
        log.info("location : " + bDTO.getLocation());
        log.info("city : " + bDTO.getCity());
        log.info("regId : " + bDTO.getRegId());
        log.info("regDt : " + bDTO.getRegDt());

        Long boardSeq = boardService.insertBoardInfo(bDTO);
        log.info("boardSeq : " + boardSeq);


        log.info(this.getClass().getName() + ".insertBoardInfo End !!");
        return new ResponseEntity<>(boardSeq, HttpStatus.OK);
    }

    @PostMapping(value = "/board/insertBoardPostInfo")
    @ResponseBody
    public ResponseEntity<?> insertBoardPostInfo(@RequestBody List<Board_PostDTO> bpList) throws Exception {
        log.info(this.getClass().getName() + ".insertBoardPostInfo Start !!");
        log.info("bpList size : " + bpList.size());
        /*잘 받는지 로그 찍기*/
        for (Board_PostDTO bpDTO : bpList) {
            log.info("boardSeq : " + bpDTO.getBoardSeq());
            log.info("title : " + bpDTO.getTitle());
            log.info("content : " + bpDTO.getContent());
            log.info("grade : " + bpDTO.getGrade());
            log.info("place : " + bpDTO.getPlace());
            log.info("hashtag : " + bpDTO.getHashtag());
            log.info("regId : " + bpDTO.getRegId());
            log.info("saveFileName : " + bpDTO.getSaveFileName());
            log.info("saveFilePath : " + bpDTO.getSaveFilePath());
        }

        int result = boardService.insertBoardPostInfo(bpList);
        log.info("result : " + result);


        log.info(this.getClass().getName() + ".insertBoardPostInfo End !!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/board/getBoardList")
    @ResponseBody
    public ResponseEntity<?> getBoardList(@RequestBody Map<String, Integer> pageSizeMap) throws Exception {
        log.info(this.getClass().getName() + ".getBoardList Start !!");

        int pageSize = pageSizeMap.get("pageSize");

        log.info("pageSize : " + pageSize);
        ;
        List<BoardDTO> result = boardService.getBoardList(pageSize);
        log.info("result : " + result);

        log.info(this.getClass().getName() + ".getBoardList End !!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/board/getBoardPostList")
    public ResponseEntity<?> getBoardPostList(@RequestBody Board_PostDTO bpDTO) throws Exception {
        log.info(this.getClass().getName() + " .getBoardPostList Start !!");

        List<Board_PostDTO> result = boardService.getBoardPostList(bpDTO);
        log.info("result size : " + result.size());

        log.info(this.getClass().getName() + " .getBoardPostList End !!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/board/getHashBoardList")
    public ResponseEntity<?> getHashBoardList(@RequestBody String hashTag) throws Exception {
        log.info(this.getClass().getName() + " .getHashBoardList Start !!");

        List<BoardDTO> result = boardService.getHashBoardList(hashTag);
        log.info("result size : " + result.size());

        log.info(this.getClass().getName() + " .getHashBoardList End !!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/board/getBoard")
    public ResponseEntity<?> getBoard(@RequestBody BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".getBoard Start !!");

        BoardDTO result = boardService.getBoard(bDTO);

        log.info(this.getClass().getName() + ".getBoard End !!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/board/updateBoardInfo")
    @ResponseBody
    public ResponseEntity<?> updateBoardInfo(@RequestBody BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateBoardInfo Start !!");

        log.info("boardSeq : " + bDTO.getBoardSeq());
        log.info("start_day : " + bDTO.getStartDay());
        log.info("endDay : " + bDTO.getEndDay());
        log.info("location : " + bDTO.getLocation());
        log.info("city : " + bDTO.getCity());
        log.info("regId : " + bDTO.getRegId());
        log.info("regDt : " + bDTO.getRegDt());

        Long boardSeq = boardService.updateBoardInfo(bDTO);
        log.info("boardSeq : " + boardSeq);


        log.info(this.getClass().getName() + ".updateBoardInfo End !!");
        return new ResponseEntity<>(boardSeq, HttpStatus.OK);
    }

    @PostMapping(value = "/board/updateBoardPostInfo")
    @ResponseBody
    public ResponseEntity<?> updateBoardPostInfo(@RequestBody List<Board_PostDTO> bpList) throws Exception {
        log.info(this.getClass().getName() + ".updateBoardPostInfo Start !!");
        log.info("bpList size : " + bpList.size());
        /*잘 받는지 로그 찍기*/
        for (Board_PostDTO bpDTO : bpList) {
            log.info("boardSeq : " + bpDTO.getBoardSeq());
            log.info("title : " + bpDTO.getTitle());
            log.info("content : " + bpDTO.getContent());
            log.info("grade : " + bpDTO.getGrade());
            log.info("place : " + bpDTO.getPlace());
            log.info("hashtag : " + bpDTO.getHashtag());
            log.info("regId : " + bpDTO.getRegId());
            log.info("saveFileName : " + bpDTO.getSaveFileName());
            log.info("saveFilePath : " + bpDTO.getSaveFilePath());
        }

        int result = boardService.updateBoardPostInfo(bpList);
        log.info("result : " + result);


        log.info(this.getClass().getName() + ".updateBoardPostInfo End !!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/board/boardDelete")
    public ResponseEntity<?> boardDelete(@RequestBody BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".boardDelete Start !!");
        int result = boardService.deleteBoardInfo(bDTO);
        log.info(this.getClass().getName() + ".boardDelete End !!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/board/boardPostDelete")
    public ResponseEntity<?> boardDelete(@RequestBody Board_PostDTO bpDTO) throws Exception {
        log.info(this.getClass().getName() + ".boardPostDelete Start !!");
        int result = boardService.deleteBoardPostInfo(bpDTO);
        log.info(this.getClass().getName() + ".boardPostDelete End !!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* 자연어 처리를 위한 게시글 가져오기*/
    @PostMapping("getNlpData")
    public ResponseEntity<List<NlpDataDTO>> getNlpData()throws Exception {
        log.info(this.getClass().getName()+"NLP 데이터 조회 시작하기!");

        List<NlpDataDTO> nlpDataDTOList = boardService.getNlpDataList();

        return ResponseEntity.ok().body(nlpDataDTOList);
    }
}
