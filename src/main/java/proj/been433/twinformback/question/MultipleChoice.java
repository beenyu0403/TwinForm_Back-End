package proj.been433.twinformback.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import proj.been433.twinformback.writeform.Form;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(QuestionType.Values.MULTIPLE_CHOICE)
@Getter
@Setter
public class MultipleChoice extends Question{
   // private List<String> items;
    @JsonIgnore
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<MultipleChoiceItem> items = new ArrayList<>();

    public static MultipleChoice createMultipleChoice(String title, String description, boolean isEssential, String imageName) {
        MultipleChoice multipleChoice = new MultipleChoice();

        //multipleChoice.setType(type);
        multipleChoice.setTitle(title);
        multipleChoice.setDescription(description);
        multipleChoice.setEssential(isEssential);
        multipleChoice.setImageName(imageName);

        //multipleChoice.setItems(items);

        return multipleChoice;
    }

    public static MultipleChoiceItem createMultipleChoiceItem(MultipleChoice multipleChoice, String items) {
        MultipleChoiceItem itemList = new MultipleChoiceItem();
        itemList.setItemTitle(items);
        itemList.changeQuestion(multipleChoice);

        return itemList;
    }
}
