package proj.been433.twinformback.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import proj.been433.twinformback.member.Member;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }
    public List<Member> findOneByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.login.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getResultList();
    }
    public Member findOneByLoginIdToLogin(String loginId) {
        return em.createQuery("select m from Member m where m.login.loginId = :loginId", Member.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
    }
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }





}
