package proj.been433.twinformback.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.been433.twinformback.answer.Answer;
import proj.been433.twinformback.writeform.Form;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "qtype", discriminatorType = DiscriminatorType.STRING)
public abstract class Question {
    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

   // @Enumerated(EnumType.STRING)
   // private QuestionType type; // 질문 종류 (MULTIPLE_CHOICE, SHORT_ANSWER)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    @JsonIgnore
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    private String title;
    private String description;

    private boolean isEssential; // 답변 필수 여부
    private String imageName; // 첨부 이미지

    @JsonIgnore
    private boolean isDelete;

    private int questionOrder;

    public void changeForm(Form form) {
        this.form = form;
        form.getQuestions().add(this);
    }

}
