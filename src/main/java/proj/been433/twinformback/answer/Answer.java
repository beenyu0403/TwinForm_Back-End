package proj.been433.twinformback.answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.question.Question;
import proj.been433.twinformback.writeform.Form;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_id")
    private Participation participation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private String answer;

    public void setParticipation(Participation participation) {
        this.participation = participation;
        participation.getAnswers().add(this);
    }

    public void setQuestion(Question question) {
        this.question = question;
        question.getAnswers().add(this);
    }
}
