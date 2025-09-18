package kopo.poly.service;

import kopo.poly.dto.BoardDTO;
import kopo.poly.dto.Board_PostDTO;
import kopo.poly.dto.NlpDataDTO;

import java.util.List;

public interface IBoarService {

    //게시판 정보 저장
    Long insertBoardInfo(BoardDTO bDTO) throws Exception;

    //게시판 상세 정보 저장
    int insertBoardPostInfo(List<Board_PostDTO> bpList) throws Exception;

    List<BoardDTO> getBoardList(int pageSize) throws Exception;

    BoardDTO getBoard(BoardDTO bDTO) throws Exception;

    List<Board_PostDTO> getBoardPostList(Board_PostDTO bpDTO) throws Exception;

    List<BoardDTO> getHashBoardList(String hashTag) throws Exception;

    //게시판 정보 저장
    Long updateBoardInfo(BoardDTO bDTO) throws Exception;

    //게시판 상세 정보 저장
    int updateBoardPostInfo(List<Board_PostDTO> bpList) throws Exception;

    int deleteBoardInfo(BoardDTO bDTO) throws Exception;

    int deleteBoardPostInfo(Board_PostDTO bpDTO) throws Exception;

    /* NLP 데이터 가져오기*/
    List<NlpDataDTO> getNlpDataList() throws Exception;

}
