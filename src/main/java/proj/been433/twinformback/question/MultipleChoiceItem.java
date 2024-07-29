package proj.been433.twinformback.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.been433.twinformback.writeform.Form;

@Entity
@Getter
@Setter
public class MultipleChoiceItem {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private MultipleChoice question;

    private String itemTitle;

    public void changeQuestion(MultipleChoice mc) {
        this.question = mc;
        mc.getItems().add(this);
    }
}
