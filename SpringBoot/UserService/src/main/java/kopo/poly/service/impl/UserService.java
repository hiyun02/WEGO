package kopo.poly.service.impl;

import com.ctc.wstx.util.DataUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.InterestDTO;
import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.repostiory.InterestRepository;
import kopo.poly.repostiory.UserRepository;
import kopo.poly.repostiory.entity.InterestEntity;
import kopo.poly.repostiory.entity.UserEntity;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserService;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service("UserService")
public class UserService implements IUserService {


    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final IMailService mailService;
    private final PasswordEncoder passwordEncoder;

    /*
     * 회원 아이디 찾기 시작
     * */
    @Override
    public String findUserId(UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원 아이디 찾기 시작!");

        UserEntity userEntity = userRepository.findByUserNameAndEmail(userDTO.getUserName()
                , EncryptUtil.encAES128CBC(userDTO.getEmail()));

        String userId = "";

        if (userEntity != null) {
            userId = userEntity.getUserId();
        }

        return userId;
    }

    /*
     * 회원 비밀번호 업데이트
     * */
    @Transactional
    @Override
    public int findUserPwd(UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원 비밀번호 변경 시작!");

        int res = userRepository.updateUserPassword(passwordEncoder.encode(userDTO.getPassword()), userDTO.getUserId());

        return res;
    }

    @Override
    public int userReg(UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName() + "회원가입 시작!");

        int res = 0;

        UserEntity userEntity = UserEntity.builder().userId(userDTO.getUserId()).password(passwordEncoder.encode(userDTO.getPassword()))
                .userName(userDTO.getUserName()).userNickName(userDTO.getUserNickName())
                .email(EncryptUtil.encAES128CBC(userDTO.getEmail())).userAge(userDTO.getUserAge())
                .gender(userDTO.getGender()).introDuce(userDTO.getIntroDuce())
                .roles(userDTO.getRoles()).filePath(userDTO.getFilePath()).build();

        userRepository.save(userEntity);

        Optional<UserEntity> rEntity = userRepository.findByUserId(userDTO.getUserId());

        if (rEntity.isPresent()) {
            res = 1;
        }
        return res;
    }

    @Override
    public int userIdCheck(String userId) throws Exception {
        log.info(this.getClass().getName()+"아이디 중복 확인 시작!");
        int res = 0;

        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);

        if (userEntity.isPresent()) {
            res = 1;
        }
        return res;
    }

    @Override
    public int userEmailCheck(String email) throws Exception {
        log.info(this.getClass().getName()+"이메일 중복확인 시작!");

        int res = 0;

        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        if (userEntity.isPresent()) {
            res = 1;
        }

        return res;
    }

    @Override
    public int userInterest(List<InterestDTO> rList) throws Exception {
        log.info(this.getClass().getName() + "회원 관심정보 입력 시작!");

        int res = 0;

        for (InterestDTO interestDTO : rList) {
            InterestEntity interestEntity = InterestEntity.builder().interestContent(interestDTO.getInterestContent())
                    .regId(interestDTO.getRegId()).regDT(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss")).build();

            interestRepository.save(interestEntity);
        }

        res = 1;

        return res;
    }

    @Override
    public List<InterestDTO> getUserInterest(String userId) throws Exception {
        log.info(this.getClass().getName()+"회원 관심정보 조회하기");

        List<InterestEntity> interestEntityList = interestRepository.findAllByRegId(userId);

        List<InterestDTO> interestDTOList = new ObjectMapper().convertValue(interestEntityList, new TypeReference<List<InterestDTO>>(){});

        return interestDTOList;

    }

    /*
     * 회원 이메일 전송
     * */
    @Override
    public int userSendEmail(UserDTO userDTO) throws Exception {
        log.info(this.getClass().getName()+"회원 이메일 전송 시작");

        int res = 0;
        Random random = new Random();
        int Random_Pin = random.nextInt(888888) + 111111;

        MailDTO mDTO = new MailDTO();
        mDTO.setToMail(userDTO.getEmail());
        mDTO.setTitle("Travel 인증번호 발송");
        mDTO.setContents("인증번호 : " + Random_Pin);

        Optional<UserEntity> userEntity = userRepository.findByUserId(userDTO.getUserId());

        if (userEntity.isPresent()) {
            if (userDTO.getEmail().equals(EncryptUtil.decAES128CBC(userEntity.get().getEmail()))) {
                res = mailService.doSendmail(mDTO);
            }
        }else{
            return res;
        }

        if (res == 1) {
            return Random_Pin;
        } else {
            return res;
        }
    }

    @Override
    public UserDTO getUserInfo(String userId)throws Exception{
        log.info(this.getClass().getName()+"회원 정보 조회하기");

        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);

        UserDTO userDTO = new UserDTO();
        if(userEntity.isPresent()){
            userDTO = new ObjectMapper().convertValue(userEntity.get(), UserDTO.class);
        }

        return userDTO;
    }

    @Override
    public int updateUserInfo(UserDTO userDTO)throws Exception {
        log.info(this.getClass().getName() + "회원정보 업데이트하기");

        int res = userRepository.updateUserInfo(userDTO.getFilePath(), userDTO.getUserName(), userDTO.getUserNickName(), userDTO.getUserAge(), userDTO.getGender(),
                userDTO.getEmail(), userDTO.getIntroDuce(), userDTO.getUserId());

        log.info("result : " + res);

        return 1;
    }
}
