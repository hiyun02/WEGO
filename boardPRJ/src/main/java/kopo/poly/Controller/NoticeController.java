package kopo.poly.Controller;


import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.NoticeDTO;
import kopo.poly.service.INoticeService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller // 현재 클래스를 controller라고 인식
@Slf4j // slf4j는 스프링 프레임워크에서 로그 처리하는 인터페이스 기술
@RequiredArgsConstructor
@RequestMapping("/notice")

public class NoticeController {

    private final INoticeService noticeService;
    //RequiredArgsConstructor를 통해서 메모리에 올라가 있는 service 객체를 controller에서 사용할 수 있게 주입함

/**
 * 향후 수정사항
 *  HTML 디자인적 요소 + 로그인 회원가입 로직 구현 시 세션 저장 요소
 */


    /**
     * 게시판 리스트 보여주기
     */
    @GetMapping(value = "noticeList")
    public String noticeList(HttpSession session,ModelMap model,
                             @PageableDefault(size=2) Pageable pageable) throws Exception {

        log.info(getClass().getName() + " noticeList start");
        session.setAttribute("SESSION_USER_ID", "USER01"); //향후에 세션 수정 해야됨

        Page<NoticeDTO> rList = noticeService.getNoticeList(pageable);
        // optional을 활용하여 Null 예러 처리
        // ofNullable : 데이터에 값이 null이 올수 도 있고 아닐수도 있는경우
        // orElseGet: 파라미터로 함수형 인터페이스(함수)를 받는다.
        int startPage =Math.max(1,rList.getPageable().getPageNumber()-4);
        int endPage =Math.min(rList.getTotalPages(),rList.getPageable().getPageNumber()+4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("rList", rList);

        log.info(getClass().getName() + " noticeList end");

        return "notice/noticeList";
    }


    /**
     * 게시판 작성페이지
     */
    @GetMapping(value = "noticeReg")
    public String noticeReg(HttpSession session, ModelMap model)throws Exception{

        log.info(getClass().getName() + "noticeReg start");
        String userId = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID")); // 향후에 세션으로 넣어야됨

        NoticeDTO nDTO = new NoticeDTO(); // 세션에 있는 현재 로그인된 작성자의 아이디를 DTO에 넣고 HTML에 전달
        nDTO.setUserId(userId);

        model.addAttribute("nDTO", nDTO);
        log.info(getClass().getName() + "noticeReg End");

        return "notice/noticeReg";
    }

    /**
     * 게시판 글 등록
     */
    @ResponseBody
    @PostMapping(value = "noticeInsert")
    public MsgDTO noticeInsert(HttpServletRequest request, HttpSession session)throws Exception{

        log.info(getClass().getName() + "noticeInsert start");

        String msg = "";
        MsgDTO dto = null;

        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID")); // 향후에 세션으로 넣어야됨
            String title = CmmUtil.nvl(request.getParameter("title")); // 제목
            String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

            log.info("session user_id : " + userId);
            log.info("title : " + title);
            log.info("contents : " + contents);


            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setUserId(userId);
            pDTO.setTitle(title);
            pDTO.setContents(contents);

            noticeService.insertNoticeInfo(pDTO);

            msg = "등록되었습니다.";


        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        }finally {
            dto = new MsgDTO();
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".noticeInsert End!" );

        }

        log.info(getClass().getName() + "noticeInsert End");
        return dto;
    }

    /**
     * 게시판 상세보기
     */
    @GetMapping(value = "noticeInfo")
    public String noticeInfo(HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".noticeInfo Start!");

        String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 공지글번호(PK)

        log.info("nSeq : " + nSeq);

        NoticeDTO pDTO = new NoticeDTO();
        pDTO.setNoticeSeq(Long.parseLong(nSeq)); //DTO에 저장된 타입은 Long이므로 형변환


        NoticeDTO rDTO = Optional.ofNullable(noticeService.getNoticeInfo(pDTO, true))
                .orElseGet(NoticeDTO::new);


        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".noticeInfo End!");

        return "notice/noticeInfo";
    }


    /**
     * 게시판 수정 보기
     */
    @GetMapping(value = "noticeEditInfo")
    public String noticeEditInfo(HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".noticeEditInfo Start!");

        String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 공지글번호(PK)
        log.info("nSeq : " + nSeq);


        NoticeDTO pDTO = new NoticeDTO();
        pDTO.setNoticeSeq(Long.parseLong(nSeq));

        NoticeDTO rDTO = Optional.ofNullable(noticeService.getNoticeInfo(pDTO, false))
                .orElseGet(NoticeDTO::new);

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".noticeEditInfo End!");

        return "notice/noticeEditInfo";
    }


    /**
     * 게시판 글 수정
     */
    @ResponseBody
    @PostMapping(value = "noticeUpdate")
    public MsgDTO noticeUpdate(HttpSession session, HttpServletRequest request) {

        log.info(this.getClass().getName() + ".noticeUpdate Start!");

        String msg = ""; // 메시지 내용
        MsgDTO dto = null; // 결과 메시지 구조

        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SESSION_USER_ID")); // 향후에 세션으로 넣어야됨
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 글번호(PK)
            String title = CmmUtil.nvl(request.getParameter("title")); // 제목
            String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

            log.info("userId : " + userId);
            log.info("nSeq : " + nSeq);
            log.info("title : " + title);
            log.info("contents : " + contents);

            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setUserId(userId);
            pDTO.setNoticeSeq(Long.parseLong(nSeq));
            pDTO.setTitle(title);
            pDTO.setContents(contents);

            // 게시글 수정하기 DB
            noticeService.updateNoticeInfo(pDTO);

            msg = "수정되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {

            dto = new MsgDTO();
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".noticeUpdate End!");

        }

        return dto;
    }

    /**
     * 게시판 글 삭제
     */
    @ResponseBody
    @PostMapping(value = "noticeDelete")
    public MsgDTO noticeDelete(HttpServletRequest request) {

        log.info(this.getClass().getName() + ".noticeDelete Start!");

        String msg = ""; // 메시지 내용
        MsgDTO dto = null; // 결과 메시지 구조

        try {
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 글번호(PK)
            log.info("nSeq : " + nSeq);

            NoticeDTO pDTO = new NoticeDTO();
            pDTO.setNoticeSeq(Long.parseLong(nSeq));

            // 게시글 삭제하기 DB
            noticeService.deleteNoticeInfo(pDTO);

            msg = "삭제되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            dto = new MsgDTO();
            dto.setMsg(msg);

            log.info(this.getClass().getName() + ".noticeDelete End!");

        }

        return dto;
    }


}
