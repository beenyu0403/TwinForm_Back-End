package proj.been433.twinformback.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import proj.been433.twinformback.member.Login;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twinform")
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/users/id-check")
    public IdCheckResponse checkId(@RequestParam String userid) {
        Boolean result = memberService.checkLoginId(userid);
        return new IdCheckResponse(result);
    }

    @PostMapping("/users/sign-up")
    public UserSignUpResponse saveMemberV2(@RequestBody @Validated UserSignUpRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        //member.setAddress(request.getAddress());
        member.setLogin(request.getLogin());

        Long id = memberService.join(member);
        return new UserSignUpResponse(id);

    }

    @PostMapping("/users/sign-in")
    public UserSignInResponse loginMember(@RequestBody @Validated UserSignInRequest request) {
        String success = memberService.successLogin(request.loginId, request.loginPw);
        return new UserSignInResponse(success);
    }
    @Data
    static class UserSignUpRequest {
        private String name;
        private Login login;
    }

    @Data
    static class UserSignUpResponse {
        private Long id;

        public UserSignUpResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    @AllArgsConstructor
    static class IdCheckResponse {
        private Boolean checkResult;
    }

    @Data
    static class IdCheckRequest {
        private String loginId;
    }

    @Data
    static class UserSignInRequest {
        private String loginId;
        private String loginPw;
    }
    @Data
    @AllArgsConstructor
    static class UserSignInResponse {
        private String successLoginId;
    }
}
