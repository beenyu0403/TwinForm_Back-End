package proj.been433.twinformback.service;


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
    public Question createQuestion(Long form_id, String questionType, String question, String description, Boolean is_essential, String image_name, List<String> choice_items) {
        Form form = writeFormRepository.findOne(form_id);
        Question question1;
        if (questionType.equals("객관식")) {

            question1 = MultipleChoice.createMultipleChoice(QuestionType.MULTIPLE_CHOICE, question, description, is_essential, image_name);
            for (int i=0; i< choice_items.size(); i++) {
                MultipleChoiceItem multipleChoiceItem = MultipleChoice.createMultipleChoiceItem((MultipleChoice) question1, choice_items.get(i));
                writeQuestionRepository.saveQuestionItem(multipleChoiceItem);
            }
        }else{
            question1 = ShortAnswer.createShortAnswer(QuestionType.SHORT_ANSWER, question, description, is_essential, image_name);
        }
        question1.changeForm(form);
        writeQuestionRepository.save(question1);
        return question1;
    }


    public List<Question> findQuestions() {
        return writeQuestionRepository.findAll();
    }

    public Question findOne(Long questionId) {
        return writeQuestionRepository.findOne(questionId);
    }
}
