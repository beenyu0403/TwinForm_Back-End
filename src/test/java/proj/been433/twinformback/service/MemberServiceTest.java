package proj.been433.twinformback.service;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    void join() {
        Member member = new Member();
        member.setName("kim");

        Long saveId = memberService.join(member);
        Member findMember = memberService.findOneById(saveId);

        Assertions.assertThat(findMember.getId()).isEqualTo(saveId);
    }


}