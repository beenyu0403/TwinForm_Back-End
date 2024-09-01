package proj.been433.twinformback.answer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.writeform.Form;
import proj.been433.twinformback.writeform.WriteForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "participation")
@Getter
@Setter
public class Participation {
    @Id
    @GeneratedValue
    @Column(name = "participation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    @OneToMany(mappedBy = "participation", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    private LocalDateTime participateDate;

    public void setMember(Member member) {
        this.member = member;
        member.getParticipations().add(this);
    }

    public void setForm(Form form) {
        this.form = form;
        form.getParticipations().add(this);
    }

    public static Participation createParticipation(Member member, Form form) {
        Participation participation = new Participation();
        participation.setMember(member);
        participation.setForm(form);
        participation.setParticipateDate(LocalDateTime.now());

        return participation;
    }
}
