package proj.been433.twinformback.service;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.been433.twinformback.question.*;
import proj.been433.twinformback.repository.WriteFormRepository;
import proj.been433.twinformback.repository.WriteQuestionRepository;
import proj.been433.twinformback.writeform.Form;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WriteQuestionService {
    private final WriteQuestionRepository writeQuestionRepository;
    private final WriteFormRepository writeFormRepository;

    @Transactional
    public Long saveQuestion(Question question) {
        writeQuestionRepository.save(question);
        return  question.getId();
    }

    @Transactional
    public Question createQuestion(Long form_id, String questionType, String question, String description, Boolean is_essential, String image_name, List<String> choice_items, int question_order) {
        Form form = writeFormRepository.findOne(form_id);
        Question question1;
        if (questionType.equals("객관식")) {

            question1 = MultipleChoice.createMultipleChoice(question, description, is_essential, image_name, question_order);
            for (int i=0; i< choice_items.size(); i++) {
                MultipleChoiceItem multipleChoiceItem = MultipleChoice.createMultipleChoiceItem((MultipleChoice) question1, choice_items.get(i));
                writeQuestionRepository.saveQuestionItem(multipleChoiceItem);
            }
        }else{
            question1 = ShortAnswer.createShortAnswer(QuestionType.SHORT_ANSWER, question, description, is_essential, image_name, question_order);
        }
        question1.changeForm(form);
        writeQuestionRepository.save(question1);
        return question1;
    }
//    @Transactional
//    public void updateQuestion(Long form_id, String questionType, String question, String description, Boolean is_essential, String image_name, List<String> choice_items, int question_id, int question_order) {
//        Form form = writeFormRepository.findOne(form_id);
//        List<Question> questions = writeQuestionRepository.findQuestionsByForm(form);
//
//        Question question1;
//
////        if (questionType.equals("객관식")) {
////            //question1 = MultipleChoice.createMultipleChoice(question, description, is_essential, image_name, question_order);
////            for (int i=0; i< choice_items.size(); i++) {
////                MultipleChoiceItem multipleChoiceItem = MultipleChoice.createMultipleChoiceItem((MultipleChoice) question1, choice_items.get(i));
////                writeQuestionRepository.saveQuestionItem(multipleChoiceItem);
////            }
////        }else{
////            question1 = ShortAnswer.createShortAnswer(QuestionType.SHORT_ANSWER, question, description, is_essential, image_name, question_order);
//////        }
//        //question1.changeForm(form);
//        //writeQuestionRepository.save(question1);
//        //return question1;
//    }

    public int countQuestions(Long formId) {
        Form form = writeFormRepository.findOne(formId);
        int count = writeQuestionRepository.findQuestionsByForm(form).size();
        return count;
    }

    public List<Question> findQuestions() {
        return writeQuestionRepository.findAll();
    }

    public Question findOne(Long questionId) {
        return writeQuestionRepository.findOne(questionId);
    }

    public List<QuestionListResponse> findQuestionsByForm(Form form) {
        List<Question> q = writeQuestionRepository.findQuestionsByForm(form);
        List<QuestionListResponse> result = new ArrayList<>();
        for (int i=0; i<q.size(); i++) {
            if (supportsMultipleChoice(q.get(i))) {
                QuestionListResponse qr = new QuestionListResponse(q.get(i), QuestionType.MULTIPLE_CHOICE.toString());
                List<String> items = writeQuestionRepository.findItemsByQuestion(q.get(i));
                for (int j=0; j<items.size(); j++) {
                    qr.addQuestionItem(items.get(j));
                }
                result.add(qr);
            }else{
                result.add(new QuestionListResponse(q.get(i), QuestionType.SHORT_ANSWER.toString()));
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

        public QuestionListResponse(Question q, String type) {
            this.question_id = q.getId();
            this.question = q.getTitle();
            this.description = q.getDescription();
            this.essential = q.isEssential();
            this.imageName = q.getImageName();
            this.type = type;
            this.questionOrder = q.getQuestionOrder();
        }

        public void addQuestionItem(String item) {
            this.items.add(item);
        }
    }



    public boolean supportsMultipleChoice(Question question) {
        return (question instanceof MultipleChoice);
    }


}
