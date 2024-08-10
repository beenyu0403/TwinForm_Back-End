package proj.been433.twinformback.question;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import proj.been433.twinformback.writeform.Form;

import java.util.List;

@Entity
@DiscriminatorValue(QuestionType.Values.SHORT_ANSWER)
@Getter
@Setter
public class ShortAnswer extends Question {
    public static ShortAnswer createShortAnswer(QuestionType type, String title, String description, boolean isEssential, String imageName, int questionOrder) {
        ShortAnswer shortAnswer = new ShortAnswer();
        //shortAnswer.setType(type);
        shortAnswer.setQuestionOrder(questionOrder);
        shortAnswer.setDelete(false);
        shortAnswer.setTitle(title);
        shortAnswer.setDescription(description);
        shortAnswer.setEssential(isEssential);
        shortAnswer.setImageName(imageName);

        return shortAnswer;
    }
}
