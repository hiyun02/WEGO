package kopo.poly.service;

import kopo.poly.dto.BoardDTO;
import kopo.poly.dto.Board_PostDTO;

import java.util.List;

public interface IBoardService {

    //게시판 정보 저장
    Long insertBoardInfo(BoardDTO bDTO) throws Exception;

    //게시판 정보 저장
    int insertBoardPostInfo(List<Board_PostDTO> bpList) throws Exception;

    //게시판 리스트 가져오기
    List<BoardDTO> getBoardList(int pageSize) throws Exception;

    List<BoardDTO> getHashBoardList(String hashTag) throws Exception;

    BoardDTO getBoard(BoardDTO bDTO) throws Exception;

    List<Board_PostDTO> getBoardPostList(Board_PostDTO bpDTO) throws Exception;

    //게시판 정보 수정
    Long updateBoardInfo(BoardDTO bDTO) throws Exception;

    //게시판 정보 수정
    int updateBoardPostInfo(List<Board_PostDTO> bpList) throws Exception;

    int boardDelete(BoardDTO bDTO) throws Exception;

    int boardPostDelete(Board_PostDTO bpDTO) throws Exception;

    String getUserName(String user_name) throws Exception;
}
