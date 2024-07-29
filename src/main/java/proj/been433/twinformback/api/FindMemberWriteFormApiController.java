package proj.been433.twinformback.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.service.MemberService;
import proj.been433.twinformback.service.WriteFormService;
import proj.been433.twinformback.service.WriteQuestionService;
import proj.been433.twinformback.writeform.Form;
import proj.been433.twinformback.writeform.FormStatus;
import proj.been433.twinformback.writeform.WriteForm;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twinform")
public class FindMemberWriteFormApiController {
    private final MemberService memberService;
    private final WriteFormService writeFormService;
    private final WriteQuestionService writeQuestionService;

    @GetMapping("/write-form-title")
    public WriteFormListResponse findWriteFormTitle(@RequestParam String userid) {
        Member member = memberService.findOneByLoginId(userid);
        return new WriteFormListResponse(writeFormService.findAllTitleFormsByMemberId(member));
    }

    @GetMapping("/write-form-detail")
    public WriteFormListResponse findWriteFormDetail(@RequestParam String formid) {
        Form form = writeFormService.findOne(Long.parseLong(formid));
        return new WriteFormListResponse(writeFormService.findOne(form.getId()));
    }

    @Data
    static class WriteFormListResponse {
        private Object writeFormAllTitleList;

        public WriteFormListResponse(Object writeFormAllTitleList) {
            this.writeFormAllTitleList = writeFormAllTitleList;
        }
    }

}
