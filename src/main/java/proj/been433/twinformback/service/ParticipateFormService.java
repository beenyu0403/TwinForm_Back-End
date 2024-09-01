package proj.been433.twinformback.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.been433.twinformback.answer.Answer;
import proj.been433.twinformback.answer.Participation;
import proj.been433.twinformback.api.dto.ParticipateFormTitleResponse;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.question.MultipleChoice;
import proj.been433.twinformback.question.Question;
import proj.been433.twinformback.question.QuestionType;
import proj.been433.twinformback.repository.MemberRepository;
import proj.been433.twinformback.repository.ParticipateFormRepository;
import proj.been433.twinformback.repository.WriteFormRepository;
import proj.been433.twinformback.repository.WriteQuestionRepository;
import proj.been433.twinformback.writeform.Form;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipateFormService {
    private final ParticipateFormRepository participateFormRepository;
    private final MemberRepository memberRepository;
    private final WriteFormRepository writeFormRepository;
    private final WriteQuestionRepository writeQuestionRepository;
    @Transactional
    public Long saveParticipation(Participation participation) {
        participateFormRepository.save(participation);
        return  participation.getId();
    }

    @Transactional
    public Long saveAnswer(Answer answer) {
        participateFormRepository.saveAnswer(answer);
        return  answer.getId();
    }

    @Transactional
    public Participation create(Member member, Form form) {
        Participation participation = Participation.createParticipation(member, form);
        participateFormRepository.save(participation);

        return participation;
    }

    @Transactional
    public Long saveAnswer(Participation participation, Question question, String ans) {
        Answer answer = new Answer();
        answer.setParticipation(participation);
        answer.setQuestion(question);
        answer.setAnswer(ans);

        participateFormRepository.saveAnswer(answer);

        return answer.getId();
    }

    public List<ParticipateFormTitleResponse> findAllTitleFormsByMemberId(Member member, String sortType) {

        return participateFormRepository.findAllTitleWriteFormWithMember(member, sortType);
    }

    public List<ParticipateFormTitleResponse> findTitlesByTerm(Member member, String searchTerm, String sortType) {
        return participateFormRepository.findTitlesByTerm(member, searchTerm, sortType);
    }

    public List<QuestionListResponse> findQuestionsByForm(Form form, Long participateId) {
        List<Question> q = writeQuestionRepository.findQuestionsByForm(form);
        Participation participation = participateFormRepository.findOne(participateId);
        List<QuestionListResponse> result = new ArrayList<>();
        for (int i=0; i<q.size(); i++) {
            String answer = participateFormRepository.findAnswerByQuestion(q.get(i), participation);
            if (supportsMultipleChoice(q.get(i))) {
                QuestionListResponse qr = new QuestionListResponse(q.get(i), QuestionType.MULTIPLE_CHOICE.toString(), answer);
                List<String> items = writeQuestionRepository.findItemsByQuestion(q.get(i));
                for (int j=0; j<items.size(); j++) {
                    qr.addQuestionItem(items.get(j));
                }
                result.add(qr);
            }else{
                result.add(new QuestionListResponse(q.get(i), QuestionType.SHORT_ANSWER.toString(), answer));
            }

        }
        return result;
    }

    @Data
    static class QuestionListResponse {
        private Long question_id;
        private String question;
        private String description;
        private boolean essential;
        private String imageName;
        private List<String> items = new ArrayList<>();
        private String type;
        private int questionOrder;
        private String answer;

        public QuestionListResponse(Question q, String type, String answer) {
            this.question_id = q.getId();
            this.question = q.getTitle();
            this.description = q.getDescription();
            this.essential = q.isEssential();
            this.imageName = q.getImageName();
            this.type = type;
            this.questionOrder = q.getQuestionOrder();
            this.answer = answer;
        }

        public void addQuestionItem(String item) {
            this.items.add(item);
        }
    }

    public boolean supportsMultipleChoice(Question question) {
        return (question instanceof MultipleChoice);
    }

}
