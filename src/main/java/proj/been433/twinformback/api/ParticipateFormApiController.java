package proj.been433.twinformback.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import proj.been433.twinformback.answer.Participation;
import proj.been433.twinformback.api.dto.FormTitleResponse;
import proj.been433.twinformback.api.dto.ParticipateFormTitleResponse;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.question.Question;
import proj.been433.twinformback.service.MemberService;
import proj.been433.twinformback.service.ParticipateFormService;
import proj.been433.twinformback.service.WriteFormService;
import proj.been433.twinformback.service.WriteQuestionService;
import proj.been433.twinformback.writeform.Form;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twinform")
public class ParticipateFormApiController {
    private final MemberService memberService;
    private final WriteFormService writeFormService;
    private final WriteQuestionService writeQuestionService;
    private final ParticipateFormService participateFormService;
    @PostMapping("/participate-form")
    public CreateParticipateFormResponse wirteForm(@RequestHeader("userid") String userid, @RequestBody @Validated CreateParticipateFormRequest request) {

        Member member = memberService.findOneByLoginId(userid);
        Form form = writeFormService.findOneFormByWriteFormId(request.writeform_id);
        Participation participation = participateFormService.create(member, form);
        for (int i=0; i<request.answers.size(); i++) {
            Question question = writeQuestionService.findOne(request.answers.get(i).question_id);
            participateFormService.saveAnswer(participation, question, request.answers.get(i).answer);
        }

        Long participateId = participateFormService.saveParticipation(participation);

        //System.out.println(userid);

        return new CreateParticipateFormResponse(participateId);
    }

    @PostMapping("/participate-search")
    public SearchParticipateFormResponse searchForm(@RequestHeader("userid") String userid, @RequestBody @Validated SearchParticipateFormRequest request) {
        Member member = memberService.findOneByLoginId(userid);
        if (request.search_term.equals("")) {
            return new SearchParticipateFormResponse(participateFormService.findAllTitleFormsByMemberId(member, request.sort_type));
        } else{
            return new SearchParticipateFormResponse(participateFormService.findTitlesByTerm(member, request.search_term, request.sort_type));
        }

    }

    @GetMapping("/participate-form-detail")
    public ParticipateFormDetailResponse findWriteFormDetail(@RequestParam String writeformid, @RequestParam String participateid) {
        ParticipateFormDetailResponse wfDetail = new ParticipateFormDetailResponse();
        Form form = writeFormService.findOneFormByWriteFormId(Long.parseLong(writeformid));
        wfDetail.setForm(form);
        wfDetail.setQuestions(participateFormService.findQuestionsByForm(form, Long.parseLong(participateid)));
        return wfDetail;
    }

    @Data
    static class ParticipateFormDetailResponse{
        private Form form;
        private Object questions;
    }

    @Data
    static class SearchParticipateFormResponse {
        private List<ParticipateFormTitleResponse> participateFormAllTitleList;

        public SearchParticipateFormResponse(List<ParticipateFormTitleResponse> participateFormAllTitleList) {
            this.participateFormAllTitleList = participateFormAllTitleList;
        }
    }

    @Data
    static class SearchParticipateFormRequest {
        private String search_term;
        private String sort_type;
    }
    @Data
    static class CreateParticipateFormRequest {
        private Long writeform_id;
        private List<CreateParticipateFormRequestAnswer> answers;
    }
    @Data
    static class CreateParticipateFormRequestAnswer {
        private Long question_id;
        private String answer;
    }
    @Data
    static class CreateParticipateFormResponse {
        private Long id;
        public CreateParticipateFormResponse(Long id) {
            this.id = id;
        }
    }


}
