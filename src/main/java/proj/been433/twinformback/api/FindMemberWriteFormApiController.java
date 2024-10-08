package proj.been433.twinformback.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import proj.been433.twinformback.api.dto.FormTitleResponse;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.service.MemberService;
import proj.been433.twinformback.service.WriteFormService;
import proj.been433.twinformback.service.WriteQuestionService;
import proj.been433.twinformback.writeform.Form;

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

    @RequestMapping("")
    public String test() {
        return "test";
    }

    @GetMapping("/write-form-detail")
    public WriteFormDetailResponse findWriteFormDetail(@RequestParam String writeformid) {
        WriteFormDetailResponse wfDetail = new WriteFormDetailResponse();
        Form form = writeFormService.findOneFormByWriteFormId(Long.parseLong(writeformid));
        wfDetail.setForm(form);
        wfDetail.setQuestions(writeQuestionService.findQuestionsByForm(form));
        return wfDetail;
    }

    @Data
    static class WriteFormListResponse {
        private List<FormTitleResponse> writeFormAllTitleList;

        public WriteFormListResponse(List<FormTitleResponse> writeFormAllTitleList) {
            this.writeFormAllTitleList = writeFormAllTitleList;
        }
    }

    @Data
    static class WriteFormDetailResponse {
        private Form form;
        private Object questions;
    }

}
