package proj.been433.twinformback.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.question.MultipleChoice;
import proj.been433.twinformback.question.MultipleChoiceItem;
import proj.been433.twinformback.question.Question;
import proj.been433.twinformback.writeform.Form;
import proj.been433.twinformback.writeform.WriteForm;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WriteQuestionRepository {
    private final EntityManager em;

    public void save(Question question) {
        em.persist(question);
    }

    public void saveQuestionItem(MultipleChoiceItem item) { em.persist(item);}

    public Question findOne(Long id) {
        return em.find(Question.class, id);
    }

    public List<Question> findAll() {
        return em.createQuery("select q from Question q", Question.class)
                .getResultList();
    }

    public List<Question> findQuestionsByForm(Form form) {
        return em.createQuery("select q from Question q where q.form = :form", Question.class)
                .setParameter("form", form)
                .getResultList();
    }

    public List<String> findItemsByQuestion(Question question) {
        return em.createQuery("select i.itemTitle from MultipleChoiceItem  i where i.question = :question", String.class)
                .setParameter("question", question)
                .getResultList();
    }


}
