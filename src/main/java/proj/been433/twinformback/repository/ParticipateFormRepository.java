package proj.been433.twinformback.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import proj.been433.twinformback.answer.Answer;
import proj.been433.twinformback.answer.Participation;
import proj.been433.twinformback.api.dto.FormTitleResponse;
import proj.been433.twinformback.api.dto.ParticipateFormTitleResponse;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.question.Question;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ParticipateFormRepository {
    private final EntityManager em;

    public void save(Participation participation) {
        em.persist(participation);
    }
    public void saveAnswer(Answer answer) { em.persist(answer);}

    public Participation findOne(Long id) {
        return em.find(Participation.class, id);
    }
    public Answer findOneAnswer(Long id) {
        return em.find(Answer.class, id);
    }

    public String findAnswerByQuestion(Question question, Participation participation) {
        return em.createQuery("select a.answer from Answer a where a.question = :question and a.participation = :participation", String.class)
                .setParameter("question", question)
                .setParameter("participation", participation)
                .getSingleResult();
    }

    public List<ParticipateFormTitleResponse> findAllTitleWriteFormWithMember(Member member, String sortType) {
        if (sortType.equals("recent")) {
            return em.createQuery("select p.id, w.id, f.title, w.status from Form f join Participation p on p.form = f join WriteForm w on f.writeForm = w where w.member = :member order by p.participateDate DESC", ParticipateFormTitleResponse.class)
                    .setParameter("member", member)
                    .getResultList();
        } else {
            return em.createQuery("select p.id, w.id, f.title, w.status from Form f join Participation p on p.form = f join WriteForm w on f.writeForm = w where w.member = :member order by f.title", ParticipateFormTitleResponse.class)
                    .setParameter("member", member)
                    .getResultList();
        }

//        return em.createQuery("select p.id, f.title, w.status from Form f join Participation p on p.form = f join WriteForm w on f.writeForm = w where w.member = :member order by f.title", FormTitleResponse.class)
//                .setParameter("member", member)
//                .getResultList();
    }

    public List<ParticipateFormTitleResponse> findTitlesByTerm(Member member, String searchTerm, String sortType) {

        if (sortType.equals("recent")) {
            return em.createQuery("select p.id, w.id, f.title, w.status from Form f join Participation p on p.form = f join WriteForm w on f.writeForm = w where w.member = :member and upper(f.title) like upper(concat('%',:searchTerm,'%')) order by p.participateDate DESC", ParticipateFormTitleResponse.class)
                    .setParameter("member", member)
                    .setParameter("searchTerm", searchTerm)
                    .getResultList();
        } else {
            return em.createQuery("select p.id, w.id, f.title, w.status from Form f join Participation p on p.form = f join WriteForm w on f.writeForm = w where w.member = :member and upper(f.title) like upper(concat('%',:searchTerm,'%')) order by f.title", ParticipateFormTitleResponse.class)
                    .setParameter("member", member)
                    .setParameter("searchTerm", searchTerm)
                    .getResultList();
        }
    }

}
