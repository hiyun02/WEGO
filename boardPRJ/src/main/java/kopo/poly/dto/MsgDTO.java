package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MsgDTO {

    private int rssult;// 성공 여부  ( 성공 : 1 , 실패 : 0)
    private String msg; // 메세지

}