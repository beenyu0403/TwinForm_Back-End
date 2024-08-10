package proj.been433.twinformback.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import proj.been433.twinformback.member.Login;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.question.Question;
import proj.been433.twinformback.service.MemberService;
import proj.been433.twinformback.service.WriteFormService;
import proj.been433.twinformback.service.WriteQuestionService;
import proj.been433.twinformback.writeform.Form;
import proj.been433.twinformback.writeform.WriteForm;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/twinform")
public class WriteFormApiController {

    private final MemberService memberService;
    private final WriteFormService writeFormService;
    private final WriteQuestionService writeQuestionService;

    @PostMapping("/write-form")
    public CreateWriteFormResponse wirteForm(@RequestHeader("userid") String userid, @RequestBody @Validated CreateWriteFormRequest request) {

        Member member = memberService.findOneByLoginId(userid);
        Form form = newForm(request);
        Long form_id = writeFormService.writeForm(form);
        for (int i=0; i<request.questions.size(); i++) {
            CreateWriteFormRequestQuestion cw = request.questions.get(i);
            Question question = writeQuestionService.createQuestion(form_id, cw.questionType, cw.question, cw.description, cw.is_essential, cw.image_name, cw.choice_items, i+1);
        }
        Long writeId = writeFormService.write(member.getId(), form.getId());

        System.out.println(userid);

        return new CreateWriteFormResponse(writeId);
    }

    @PutMapping("/write-form")
    public CreateWriteFormResponse wirteForm(@RequestHeader("userid") String userid, @RequestHeader("writeformid") String writeformid, @RequestBody @Validated CreateWriteFormRequest request) {

        Member member = memberService.findOneByLoginId(userid);
        WriteForm writeForm = writeFormService.findOneWriteForm(Long.parseLong(writeformid));
        //Long formId = 0L;
//        if (writeForm.getMember().getId() == member.getId()) {
//            Long formId = writeFormService.update(writeForm.getId(), request.getTitle(), request.getMin_Date(), request.getMax_Date());
//
//            for (int i=0; i<request.questions.size(); i++) {
//                CreateWriteFormRequestQuestion cw = request.questions.get(i);
//                Question question = writeQuestionService.updateQuestion(formId, cw.questionType, cw.question, cw.description, cw.is_essential, cw.image_name, cw.choice_items, i+1);
//            }
//        }

        System.out.println("PUT 요청 - writeForm");

        return new CreateWriteFormResponse(1L);
    }

    /**
     *
     * ObjectMapper objectMapper = new ObjectMapper();
     *         try {
     *             String test = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
     *             System.out.println(test);
     *         } catch (JsonProcessingException e) {
     *             throw new RuntimeException(e);
     *         }
     */

    private Form newForm(CreateWriteFormRequest request) {
        Form form = new Form();
        form.setTitle(request.title);
        form.setMinDate(request.min_Date);
        form.setMaxDate(request.max_Date);
        return form;
    }


    @Data
    static class CreateWriteFormRequest {
        private String title;
        private String min_Date;
        private String max_Date;
        private List<CreateWriteFormRequestQuestion> questions;
    }
    @Data
    static class CreateWriteFormRequestQuestion {
        private String questionType;
        private String question;
        private String description;
        private Boolean is_essential;
        private String image_name;
        private List<String> choice_items;
    }

    @Data
    static class CreateWriteFormResponse {
        private Long id;
        public CreateWriteFormResponse(Long id) {
            this.id = id;
        }
    }

}
