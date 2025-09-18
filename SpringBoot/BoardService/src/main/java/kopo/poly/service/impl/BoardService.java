package kopo.poly.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.BoardDTO;
import kopo.poly.dto.Board_PostDTO;
import kopo.poly.dto.NlpDataDTO;
import kopo.poly.repository.BoardPostRepository;
import kopo.poly.repository.BoardRepository;
import kopo.poly.repository.entity.BoardEntity;
import kopo.poly.repository.entity.BoardPostEntity;
import kopo.poly.service.IBoarService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("BoardService")
public class BoardService implements IBoarService {

    private final BoardRepository boardRepository;
    private final BoardPostRepository boardPostRepository;


    @Override
    public Long insertBoardInfo(BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".insertBoardInfo Start !!");

        String startDay = CmmUtil.nvl(bDTO.getStartDay());
        String endDay = CmmUtil.nvl(bDTO.getEndDay());
        String location = CmmUtil.nvl(bDTO.getLocation());
        String city = CmmUtil.nvl(bDTO.getCity());
        String regId = CmmUtil.nvl(bDTO.getRegId());
        String regDt = CmmUtil.nvl(bDTO.getRegDt());
        String title = CmmUtil.nvl(bDTO.getTitle());
        String grade = CmmUtil.nvl(bDTO.getGrade());
        String place = CmmUtil.nvl(bDTO.getPlace());
        String hashtag = CmmUtil.nvl(bDTO.getHashtag());
        String saveFilePath = CmmUtil.nvl(bDTO.getSaveFilePath());
        String userName = CmmUtil.nvl(bDTO.getUserName());

        BoardEntity boardEntity = BoardEntity.builder()
                .startDay(startDay).endDay(endDay).location(location)
                .city(city).regId(regId).regDt(regDt).title(title)
                .grade(grade).place(place).hashtag(hashtag).saveFilePath(saveFilePath)
                .userName(userName).build();

        Long boardSeq = boardRepository.save(boardEntity).getBoardSeq();
        log.info("boardSeq : " + boardSeq);

        log.info(this.getClass().getName() + ".insertBoardInfo End !!");
        return boardSeq;
    }

    @Override
    public int insertBoardPostInfo(List<Board_PostDTO> bpList) throws Exception {
        log.info(this.getClass().getName() + ".insertBoardPostInfo Start !!");
        List<BoardPostEntity> boardEntityList = new ArrayList<>();
        for (Board_PostDTO bpDTO : bpList) {
            BoardPostEntity boardEntity = BoardPostEntity.builder()
                    .boardSeq(bpDTO.getBoardSeq()).title(bpDTO.getTitle())
                    .content(bpDTO.getContent()).grade(bpDTO.getGrade())
                    .place(bpDTO.getPlace()).hashtag(bpDTO.getHashtag())
                    .regId(bpDTO.getRegId()).saveFileName(bpDTO.getSaveFileName())
                    .saveFilePath(bpDTO.getSaveFilePath()).build();
            boardEntityList.add(boardEntity);
        }
        try {
            boardPostRepository.saveAll(boardEntityList);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<BoardDTO> getBoardList(int pageSize) throws Exception {
        log.info(this.getClass().getName() + ".getBoardList Start !!");

//        List<BoardEntity> beList = boardRepository.findAll();
        PageRequest pageRequest = PageRequest.of((pageSize-1),4, Sort.by("boardSeq").descending());
        Page<BoardEntity> beList = boardRepository.findAll(pageRequest);
        List<BoardDTO> bList = new ObjectMapper().convertValue(beList.getContent(),
                new TypeReference<List<BoardDTO>>() {
                });

        log.info(this.getClass().getName() + ".getBoardList End !!");
        return bList;
    }

    @Override
    public BoardDTO getBoard(BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".getBoard Start !!");
        BoardEntity boardEntity = boardRepository.findByBoardSeq(bDTO.getBoardSeq());
        bDTO = new ObjectMapper().convertValue(boardEntity,
                new TypeReference<BoardDTO>() {
                });

        log.info(this.getClass().getName() + ".getBoard End !!");
        return bDTO;
    }

    @Override
    public List<Board_PostDTO> getBoardPostList(Board_PostDTO bpDTO) throws Exception {
        log.info(this.getClass().getName() + ".getBoardPostList Start !!");
        log.info("boardSeq : " + bpDTO.getBoardSeq());

        List<BoardPostEntity> beList = boardPostRepository.findByBoardSeq(bpDTO.getBoardSeq());

        List<Board_PostDTO> bpList = new ObjectMapper().convertValue(beList,
                new TypeReference<List<Board_PostDTO>>() {
                });

        log.info(this.getClass().getName() + ".getBoardPostList End !!");
        return bpList;
    }

    @Override
    public List<BoardDTO> getHashBoardList(String hashtag) throws Exception {

        log.info(this.getClass().getName() + ".getHashBoardList Start !!");
        log.info("hashTag : " + hashtag);

        List<BoardEntity> beList = boardRepository.findByHashtag(hashtag);

        List<BoardDTO> bList = new ObjectMapper().convertValue(beList,
                new TypeReference<List<BoardDTO>>() {
                });

        log.info("bList size : " + bList.size());

        log.info(this.getClass().getName() + ".getHashBoardList End !!");
        return bList;
    }

    @Override
    public Long updateBoardInfo(BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".updateBoardInfo Start !!");


        String startDay = CmmUtil.nvl(bDTO.getStartDay());
        String endDay = CmmUtil.nvl(bDTO.getEndDay());
        String location = CmmUtil.nvl(bDTO.getLocation());
        String city = CmmUtil.nvl(bDTO.getCity());
        String regId = CmmUtil.nvl(bDTO.getRegId());
        String regDt = CmmUtil.nvl(bDTO.getRegDt());
        String title = CmmUtil.nvl(bDTO.getTitle());
        String grade = CmmUtil.nvl(bDTO.getGrade());
        String place = CmmUtil.nvl(bDTO.getPlace());
        String hashtag = CmmUtil.nvl(bDTO.getHashtag());
        String saveFilePath = CmmUtil.nvl(bDTO.getSaveFilePath());
        String userName = CmmUtil.nvl(bDTO.getUserName());

        BoardEntity boardEntity = BoardEntity.builder()
                .boardSeq(bDTO.getBoardSeq()).startDay(startDay).endDay(endDay).location(location)
                .city(city).regId(regId).regDt(regDt).title(title)
                .grade(grade).place(place).hashtag(hashtag).saveFilePath(saveFilePath)
                .userName(userName).build();

        Long boardSeq = boardRepository.save(boardEntity).getBoardSeq();
        log.info("boardSeq : " + boardSeq);

        log.info(this.getClass().getName() + ".updateBoardInfo End !!");
        return boardSeq;
    }

    @Override
    public int updateBoardPostInfo(List<Board_PostDTO> bpList) throws Exception {
        log.info(this.getClass().getName() + ".updateBoardPostInfo Start !!");
        List<BoardPostEntity> boardEntityList = new ArrayList<>();
        for (Board_PostDTO bpDTO : bpList) {
            BoardPostEntity boardEntity = BoardPostEntity.builder()
                    .boardSeq(bpDTO.getBoardSeq()).title(bpDTO.getTitle())
                    .content(bpDTO.getTitle()).grade(bpDTO.getGrade())
                    .place(bpDTO.getPlace()).hashtag(bpDTO.getHashtag())
                    .regId(bpDTO.getRegId()).saveFileName(bpDTO.getSaveFileName())
                    .saveFilePath(bpDTO.getSaveFilePath()).build();
            boardEntityList.add(boardEntity);
        }
        try {
            boardPostRepository.saveAll(boardEntityList);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int deleteBoardInfo(BoardDTO bDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteBoardInfo Start!!");

        try {
            boardRepository.deleteById(bDTO.getBoardSeq());
            log.info(this.getClass().getName() + ".deleteBoardInfo End !!");
            return 1;
        }catch (Exception e) {
            log.info(this.getClass().getName() + ".deleteBoardInfo End !!");
            return 0;
        }
    }

    @Override
    public int deleteBoardPostInfo(Board_PostDTO bpDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteBoardPostInfo Start !!");
        try {
            boardPostRepository.deleteByBoardSeq(bpDTO.getBoardSeq());
            log.info("bpDTO.getBoardSeq : " + bpDTO.getBoardSeq());
            log.info("삭제 성공");

            log.info(this.getClass().getName() + ".deleteBoardPostInfo End !!");
            return 1;
        }catch (Exception e) {
            log.info(e+"");
            log.info(this.getClass().getName() + ".deleteBoardPostInfo End !!");
            return 0;
        }
    }

    @Override
    public List<NlpDataDTO> getNlpDataList() throws Exception {
        log.info(this.getClass().getName() + "자연어 처리 데이터 가져오기");

        List<NlpDataDTO> nlpDataDTOList = boardPostRepository.getNlpDataList();

        return nlpDataDTOList;
    }
}
