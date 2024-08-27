package org.scoula.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.member.dto.MemberDTO;
import org.scoula.member.dto.MemberJoinDTO;
import org.scoula.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    final MemberService service;

//    @PathVariable : 경로상에 username을 변수 처리(이름 일치해야함)
    @GetMapping("/checkusername/{username}")
    public ResponseEntity<Boolean> checkUsername(@PathVariable String username) {
//        주어진 사용자 이름의 중복 여부 체크하고 결과를 ResponseEntity로 반환
        return ResponseEntity.ok().body(service.checkDuplicate(username));
    }

    @PostMapping("")
    public ResponseEntity<MemberDTO> join(MemberJoinDTO member) {
//        회원 가입 처리하고 가입된 회원 정보를 ResponseEntity로 반환
        return ResponseEntity.ok(service.join(member));
    }
}
