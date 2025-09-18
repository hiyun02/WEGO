package kopo.poly.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String file_detail_path;
    private String user_id;
    private String user_pwd;
    private String user_email;
    private String user_name;
    private String user_nickname;
    private String user_age;
    private String user_gender;
    private String user_interest_content;
    private String user_interest_city;
    private String board_post_content;
    private String user_addr;
    private int following;
    private int follower;
    private String member_since;
    private String board_post_title;
    private String user_introducation;
    private int file_seq;
    private int user_seq;
    private int board_user_seq;
    private int board_seq;
    private int board_cnt;
    private int auth_follow;
    private String roomkey;
    private int user_file_seq;


    private String group_seq;
    private String covid_message_contents;
}
