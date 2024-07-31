package proj.been433.twinformback.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.question.Question;
import proj.been433.twinformback.writeform.Form;
import proj.been433.twinformback.writeform.FormStatus;
import proj.been433.twinformback.writeform.WriteForm;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WriteFormRepository {

    private final EntityManager em;

    public void save(WriteForm writeForm) {
        em.persist(writeForm);
    }

    public void saveForm(Form form) { em.persist(form);}

    public Form findOne(Long id) {
        return em.find(Form.class, id);
    }

    public WriteForm findOneWriteForm(Long id) {
        return em.find(WriteForm.class, id);
    }

    public Form findOneFormByWriteForm(WriteForm writeForm) {
        return em.createQuery("select f from Form f where f.writeForm = :writeForm", Form.class)
                .setParameter("writeForm", writeForm)
                .getSingleResult();
    }


    public Object findAllTitleWriteFormWithMember(Member member) {

        return em.createQuery("select w.id, w.title, f.status from Form w join WriteForm f on w.writeForm = f where f.member = :member", AllIdAndTitleForm.class)
                .setParameter("member", member)
                .getResultList();
    }
    @Data
    @AllArgsConstructor
    static class AllIdAndTitleForm {
        private Long id;
        private String title;
        private FormStatus status;
    }
    public Object findOneByForm(Form form) {
        return em.createQuery("select f.id, f.title, f.minDate, f.maxDate from Form f where f.id = :id", OneWriteForm.class)
                .setParameter("id", form.getId())
                .getSingleResult();
    }


    @Data
    @AllArgsConstructor
    static class OneWriteForm {
        private Long id;
        //private List<Question> questions;
        private String title;
        private String minDate;
        private String maxDate;
    }

}
