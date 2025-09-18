package kopo.poly.controller;

import kopo.poly.auth.JwtTokenProvider;
import kopo.poly.auth.JwtTokenType;
import kopo.poly.dto.*;
import kopo.poly.service.IBoardService;
import kopo.poly.service.IS3UploadService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.ConvertAreaUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final IBoardService boardService;
    private final IS3UploadService s3UploadService;
    private final JwtTokenProvider jwtTokenProvider;

    /*Thymeleaf boardWrite*/
    @GetMapping(value = "/board/boardWrite")
    public String boardWrite(HttpServletRequest request) {
        log.info(this.getClass().getName() + "게시글 작성 게시판 컨트롤러 시작!");
        log.info(this.getClass().getName() + "게시글 작성 게시판 컨트롤러 종료!");

        return "board/boardWrite";
    }

    @PostMapping(value = "/board/boardWriteProc")
    @ResponseBody
    public Map<String, String> boardWriteProc(HttpServletRequest request, HttpSession session, ModelMap model, MultipartHttpServletRequest mft) throws Exception {
        log.info(this.getClass().getName() + "게시글 작성 컨트롤러 로직 시작!");

        String msg = "";
        String url = "";

        /*임시 계정 정보*/
        String token = CmmUtil.nvl(jwtTokenProvider.resolveToken(request, JwtTokenType.REFRESH_TOKEN));
        String user_id = CmmUtil.nvl(jwtTokenProvider.getUserId(token));
        String user_name = CmmUtil.nvl((String)jwtTokenProvider.getUserName(token));
        log.info("user_name : " + user_name);
        log.info("user_id : " + user_id);

//        session.setAttribute("user_id", "test");
//        session.setAttribute("user_name", "홍석민");
//        String user_id = (String) session.getAttribute("user_id");
//        String user_name = (String) session.getAttribute("user_name");

        String location = request.getParameter("location");
        String city = request.getParameter("smallcity");

        String start_day = CmmUtil.nvl(request.getParameter("start_day"));
        String end_day = CmmUtil.nvl(request.getParameter("end_day"));
        //-------------------------------똑같은 name을 가진 input type이 여러개------------------------------------//
        String[] titles = request.getParameterValues("title");
        String[] grades = new String[titles.length];
        for(int i=0; i< titles.length; i++) {
            grades[i] = request.getParameter("rating"+(i+1));
            log.info("grades[" + i + "] : " + grades[i]);
        }
        String[] contents = request.getParameterValues("board_content");
        log.info(contents[0]);
        String[] hashTags = request.getParameterValues("hashTag");
        String[] places = request.getParameterValues("addr");
        //----------------------------------------------------------------------------------------------------//
        /*================BoardDTO 설정 -> 게시판에 있어 부모역할=======*/
        BoardDTO bDTO = new BoardDTO();

        /*첫번째 게시물만 가져오기 -> BoardList는 대표 게시글 하나만 보여줌*/
        MultipartFile firstMf = mft.getFiles("self_file").get(0);
        //원본 파일 이름
        String oriFileName = firstMf.getOriginalFilename();

        // 파일 확장자 가져오기
        String firstExt = oriFileName
                .substring(oriFileName.lastIndexOf(".") + 1).toLowerCase();
        String firstSaveFileName = DateUtil.getDateTime("24hhmmss") + "." + firstExt;
        String fullName = "https://paastabucket.s3.ap-northeast-2.amazonaws.com/" +
                firstSaveFileName + "/" + oriFileName;
        log.info("fullName : " + fullName);
        String convertLoction = ConvertAreaUtil.convertLocationCode(location);
        bDTO.setStartDay(start_day);
        bDTO.setEndDay(end_day);
        bDTO.setLocation(convertLoction);
        bDTO.setCity(ConvertAreaUtil.convertCityCode(city, convertLoction));
        bDTO.setRegId(user_id);
        bDTO.setRegDt(DateUtil.getDateTime("YYYY-MM-dd"));
        bDTO.setTitle(titles[0]);
        bDTO.setGrade(grades[0]);
        bDTO.setPlace(places[0]);
        bDTO.setUserName(user_name);
        bDTO.setHashtag(hashTags[0]);
        bDTO.setSaveFilePath(fullName);

        //BoardDTO insert
        //BOARD 테이블에 값을 저장한 후 boardSeq를 반환 받음
        Long boardSeq = boardService.insertBoardInfo(bDTO);
        log.info("최종 반환 boardSeq : " + boardSeq);
        /*=======================================================*/

        /*=========Board_PostDTO 설정 -> 게시판에 있어 자식역할=======*/
        List<Board_PostDTO> bpList = new ArrayList<>();

        //파일 기준으로 반복해서 List 값 담기
        List<MultipartFile> fileList = mft.getFiles("self_file");
        log.info("넘어온 파일 갯수 " + fileList.size());
        int idx = 0;
        log.info("title size : " + titles.length);
        for(MultipartFile mf : fileList) {
            String saveFileName;
            //원본 파일 이름
            String originalFileName = mf.getOriginalFilename();

            // 파일 확장자 가져오기
            String ext = originalFileName
                    .substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
            if(idx==0) {
                saveFileName = firstSaveFileName;
            } else {
                saveFileName = DateUtil.getDateTime("24hhmmss") + "." + ext;
            }
            // s3에 파일 저장하면서 filePath 가져오기
            String saveFilePath = s3UploadService.fileUpload(mf, saveFileName);

            Board_PostDTO bpDTO = new Board_PostDTO();
            bpDTO.setBoardSeq(boardSeq);
            bpDTO.setRegId(user_id);
            bpDTO.setTitle(titles[idx]);
            bpDTO.setContent(contents[idx]);
            bpDTO.setGrade(grades[idx]);
            bpDTO.setPlace(places[idx]);
            bpDTO.setHashtag(hashTags[idx]);
            bpDTO.setSaveFileName(saveFileName);
            bpDTO.setSaveFilePath(saveFilePath);

            bpList.add(bpDTO);
            bpDTO = null;
            idx++;
        }
        int result = boardService.insertBoardPostInfo(bpList);
        log.info("result : " + result);
        if(result == 1 && boardSeq >= 1) {
            session.setAttribute("board_seq", boardSeq);

            msg = "게시글 작성을 성공했습니다";
            // 임시로 보내기
            url = "/board/boardWrite";
        } else {
            msg = "게시글 작성을 실패했습니다 관리자에게 문의해주세요";
            url = "/board/boardWrite";
        }

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        Map<String, String> rMap = new HashMap<>();

        rMap.put("msg", msg);
        rMap.put("url", url);
        rMap.put("moveUrl", "/moveRedirect");

        return rMap;
    }



    /*Thymeleaf boardList*/
    @GetMapping(value = "/board/boardList")
    public String boardList (HttpServletRequest request, HttpSession session, ModelMap model) throws Exception{
        log.info(this.getClass().getName() + "게시판 리스트 컨트롤러 시작!");

        int pageSize;
        try {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        } catch (NumberFormatException e){
            pageSize = 1;
        }
        List<BoardDTO> bList = boardService.getBoardList(pageSize);
        log.info("bList size : " + bList.size());

        List<String> hashList = new ArrayList<>();
        //Tags 중복 제거해서 따로 List에 담기
        for(BoardDTO a : bList) {
            if(!hashList.contains(a.getHashtag())) {
                hashList.add(a.getHashtag());
            }
        }


        model.addAttribute("hashList", hashList);
        model.addAttribute("bList", bList);
        model.addAttribute("pageSize", pageSize);
        log.info(this.getClass().getName() + "게시판 리스트 컨트롤러 종료!");
        return "board/boardList";
    }

    @GetMapping(value = "/board/hashList")
    public String hashList(HttpServletRequest request, ModelMap model) throws Exception {
        log.info(this.getClass().getName() + ".hashList Start !!");
        String hashTag = CmmUtil.nvl(request.getParameter("hashTag"));
        log.info("hashTag : " + hashTag);
        List<BoardDTO> bList = boardService.getHashBoardList(hashTag);
        for(BoardDTO a : bList) {
            log.info(a.getHashtag());
        }

        model.addAttribute("bList", bList);
        log.info(this.getClass().getName() + ".hashList End !!");
        return "board/boardHashtagList";
    }

    @GetMapping(value = "moveRedirect")
    public String moveRedirect(HttpServletRequest request, HttpSession session, ModelMap model) throws Exception {
        String url = request.getParameter("url");
        String msg = request.getParameter("msg");

        model.addAttribute("url", url);
        model.addAttribute("msg", msg);
        return "boardRedirect";
    }

    @GetMapping(value = "/board/boardDetail")
    public String boardDetail(HttpServletRequest request, Model model) throws Exception{
        log.info(this.getClass().getName() + ".boardDetail Start !!");

        /*임시 계정 정보*/
        String token = CmmUtil.nvl(jwtTokenProvider.resolveToken(request, JwtTokenType.REFRESH_TOKEN));
        String tokenUserName = CmmUtil.nvl((String)jwtTokenProvider.getUserName(token));

        model.addAttribute("tokenUserName", tokenUserName);

        Long boardSeq = Long.parseLong(CmmUtil.nvl(request.getParameter("boardSeq")));
        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String regDt = CmmUtil.nvl(request.getParameter("regDt"));
        String location = CmmUtil.nvl(request.getParameter("location"));
        String city = CmmUtil.nvl(request.getParameter("city"));
        log.info("boardSeq : " + CmmUtil.nvl(request.getParameter("boardSeq")));
        log.info("boardSeq copy: " + boardSeq);
        Board_PostDTO bpDTO = new Board_PostDTO();
        BoardDTO bDTO = new BoardDTO();
        bDTO.setBoardSeq(boardSeq);
        bDTO.setUserName(userName);
        bDTO.setRegDt(regDt);
        bDTO.setLocation(location);
        bDTO.setCity(city);
        bpDTO.setBoardSeq(boardSeq);

        List<Board_PostDTO> bpList = boardService.getBoardPostList(bpDTO);

        if(bpList.size() != 0) {
            log.info("가져오기 성공");
            for(Board_PostDTO bpEachDTO : bpList) {
                log.info("content : " + bpEachDTO.getContent());
            }
            log.info("boardSeq, bDTO : " + bDTO.getBoardSeq());
            model.addAttribute("bpList", bpList);
            model.addAttribute("bDTO", bDTO);
        } else {
            log.info("가져오기 실패");
        }
        log.info(this.getClass().getName() + ".boardDetail End !!");
        return "board/boardDetail";
    }

    @PostMapping(value = "/board/boardUpdatePage")
    public String boardUpdatePage(HttpServletRequest request, Model model) throws Exception {
        log.info(this.getClass().getName() + "boardUpdatePage Start !!");
        Long boardSeq = Long.parseLong(CmmUtil.nvl(request.getParameter("board_seq")));
        log.info("boardSeq : " + boardSeq);
        /*boardDTO*/
        BoardDTO bDTO = new BoardDTO();
        bDTO.setBoardSeq(boardSeq);
        /*boradPostDTO*/
        Board_PostDTO bpDTO = new Board_PostDTO();
        bpDTO.setBoardSeq(boardSeq);

        bDTO = boardService.getBoard(bDTO);
        log.info("bDTO ==================");
        log.info(bDTO.getStartDay() + " / " + bDTO.getEndDay() + " / " + bDTO.getLocation() + " / " + bDTO.getCity());
        List<Board_PostDTO> bpList = boardService.getBoardPostList(bpDTO);

        if(bpList.size() != 0) {
            log.info("가져오기 성공");
            for(Board_PostDTO bpEachDTO : bpList) {
                log.info("content : " + bpEachDTO.getContent());
            }
            model.addAttribute("bpList", bpList);
            model.addAttribute("bDTO", bDTO);
        } else {
            log.info("가져오기 실패");
        }


        log.info(this.getClass().getName() + "boardUpdatePage End !!");
        return "board/boardUpdate";
    }

    @PostMapping(value = "/board/boardUpdateProc")
    @ResponseBody
    public Map<String, String> boardUpdateProc(HttpServletRequest request, HttpSession session, ModelMap model, MultipartHttpServletRequest mft) throws Exception {
        log.info(this.getClass().getName() + "게시글 업데이트 컨트롤러 로직 시작!");

        String msg = "";
        String url = "";

        /*임시 계정 정보*/
        String token = CmmUtil.nvl(jwtTokenProvider.resolveToken(request, JwtTokenType.REFRESH_TOKEN));
        String user_id = CmmUtil.nvl(jwtTokenProvider.getUserId(token));
        String user_name = CmmUtil.nvl((String)jwtTokenProvider.getUserName(token));

        Long boardSeq = Long.parseLong(CmmUtil.nvl(request.getParameter("board_seq")));
        String location = request.getParameter("location");
        String city = request.getParameter("smallcity");

        String start_day = CmmUtil.nvl(request.getParameter("start_day"));
        String end_day = CmmUtil.nvl(request.getParameter("end_day"));
        //-------------------------------똑같은 name을 가진 input type이 여러개------------------------------------//
        String[] titles = request.getParameterValues("title");
        String[] grades = request.getParameterValues("rating");
        String[] contents = request.getParameterValues("board_content");
        String[] hashTags = request.getParameterValues("hashTag");
        String[] places = request.getParameterValues("addr");
        //----------------------------------------------------------------------------------------------------//
        /*================BoardDTO 설정 -> 게시판에 있어 부모역할=======*/
        BoardDTO bDTO = new BoardDTO();

        /*첫번째 게시물만 가져오기 -> BoardList는 대표 게시글 하나만 보여줌*/
        MultipartFile firstMf = mft.getFiles("self_file").get(0);
        //원본 파일 이름
        String oriFileName = firstMf.getOriginalFilename();

        // 파일 확장자 가져오기
        String firstExt = oriFileName
                .substring(oriFileName.lastIndexOf(".") + 1).toLowerCase();
        String firstSaveFileName = DateUtil.getDateTime("24hhmmss") + "." + firstExt;
        String fullName = "https://paastabucket.s3.ap-northeast-2.amazonaws.com/" +
                firstSaveFileName + "/" + oriFileName;
        log.info("fullName : " + fullName);

        String convertLocation = ConvertAreaUtil.convertLocationCode(location);
        bDTO.setBoardSeq(boardSeq);
        bDTO.setStartDay(start_day);
        bDTO.setEndDay(end_day);
        bDTO.setLocation(convertLocation);
        bDTO.setCity(ConvertAreaUtil.convertCityCode(city, convertLocation));
        bDTO.setRegId(user_id);
        bDTO.setRegDt(DateUtil.getDateTime("YYYY-MM-dd"));
        bDTO.setTitle(titles[0]);
        bDTO.setGrade(grades[0]);
        bDTO.setPlace(places[0]);
        bDTO.setUserName(user_name);
        bDTO.setHashtag(hashTags[0]);
        bDTO.setSaveFilePath(fullName);

        //BoardDTO insert
        //BOARD 테이블에 값을 저장한 후 boardSeq를 반환 받음
        boardSeq = boardService.updateBoardInfo(bDTO);
        log.info("최종 반환 boardSeq : " + boardSeq);
        /*=======================================================*/

        /*=========Board_PostDTO 설정 -> 게시판에 있어 자식역할=======*/
        List<Board_PostDTO> bpList = new ArrayList<>();

        //파일 기준으로 반복해서 List 값 담기
        List<MultipartFile> fileList = mft.getFiles("self_file");
        log.info("넘어온 파일 갯수 " + fileList.size());
        int idx = 0;
        for(MultipartFile mf : fileList) {
            String saveFileName;
            //원본 파일 이름
            String originalFileName = mf.getOriginalFilename();

            // 파일 확장자 가져오기
            String ext = originalFileName
                    .substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
            if(idx==0) {
                saveFileName = firstSaveFileName;
            } else {
                saveFileName = DateUtil.getDateTime("24hhmmss") + "." + ext;
            }
            // s3에 파일 저장하면서 filePath 가져오기
            String saveFilePath = s3UploadService.fileUpload(mf, saveFileName);

            Board_PostDTO bpDTO = new Board_PostDTO();
            bpDTO.setBoardSeq(boardSeq);
            bpDTO.setRegId(user_id);
            bpDTO.setTitle(titles[idx]);
            bpDTO.setContent(contents[idx]);
            bpDTO.setGrade(grades[idx]);
            bpDTO.setPlace(places[idx]);
            bpDTO.setHashtag(hashTags[idx]);
            bpDTO.setSaveFileName(saveFileName);
            bpDTO.setSaveFilePath(saveFilePath);

            bpList.add(bpDTO);
            bpDTO = null;
            idx++;
        }
        int result = boardService.updateBoardPostInfo(bpList);
        log.info("result : " + result);
        if(result == 1 && boardSeq >= 1) {
            session.setAttribute("board_seq", boardSeq);
            msg = "게시글 수정을 성공했습니다";
            // 임시로 보내기
            url = "/board/boardList";
        } else {
            msg = "게시글 작성을 실패했습니다 관리자에게 문의해주세요";
            url = "/board/boardList";
        }

        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        Map<String, String> rMap = new HashMap<>();

        rMap.put("msg", msg);
        rMap.put("url", url);
        rMap.put("moveUrl", "/moveRedirect");

        return rMap;
    }

    @PostMapping(value = "/board/boardDelete")
    @ResponseBody
    public Map<String, String> boardDelete(HttpServletRequest request,Model model) throws Exception {
        log.info(this.getClass().getName() + ".boardDelete Start !!");
        String msg="";
        String url="";
        Long boardSeq = Long.parseLong(CmmUtil.nvl(request.getParameter("board_seq")));
        log.info("boardSeq : " + boardSeq);
        BoardDTO bDTO = new BoardDTO();
        Board_PostDTO bpDTO = new Board_PostDTO();

        bDTO.setBoardSeq(boardSeq);
        bpDTO.setBoardSeq(boardSeq);

        int result = boardService.boardDelete(bDTO);
        if(result != 0) {
            boardService.boardPostDelete(bpDTO);
            msg = "게시글 삭제를 성공했습니다";
            // 임시로 보내기
            url = "/board/boardList";
        }


        model.addAttribute("msg", msg);
        model.addAttribute("url", url);
        Map<String, String> rMap = new HashMap<>();

        rMap.put("msg", msg);
        rMap.put("url", url);
        rMap.put("moveUrl", "/moveRedirect");
        log.info(this.getClass().getName() + ".boardDelete End !!");

        return rMap;
    }

}
