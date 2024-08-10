package proj.been433.twinformback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.question.Question;
import proj.been433.twinformback.repository.MemberRepository;
import proj.been433.twinformback.repository.WriteFormRepository;
import proj.been433.twinformback.repository.WriteQuestionRepository;
import proj.been433.twinformback.writeform.Form;
import proj.been433.twinformback.writeform.WriteForm;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WriteFormService {
    private final MemberRepository memberRepository;
    private final WriteFormRepository writeFormRepository;


    @Transactional
    public Long writeForm(Form form) {
        writeFormRepository.saveForm(form);
        return form.getId();
    }

    @Transactional
    public Long update(Long writeFormId, String title, String min_date, String max_date) {
        WriteForm writeForm = writeFormRepository.findOneWriteForm(writeFormId);
        Form form = writeFormRepository.findOneFormByWriteForm(writeForm);
        form.setTitle(title);
        form.setMinDate(min_date);
        form.setMaxDate(max_date);
        writeFormRepository.saveForm(form);
        WriteForm.changeFormStatus(form, writeForm);

        return form.getId();
    }

    @Transactional
    public Long write(Long memberId, Long formId) {
        Member member = memberRepository.findOne(memberId);
        Form form = writeFormRepository.findOne(formId);

        WriteForm writeForm = WriteForm.createWriteForm(member, form);
        writeFormRepository.save(writeForm);

        return writeForm.getId();
    }

    public Object findAllTitleFormsByMemberId(Member member) {
        return writeFormRepository.findAllTitleWriteFormWithMember(member);
    }

    public Form findOne(Long Id) {
        return writeFormRepository.findOne(Id);
    }

    public WriteForm findOneWriteForm(Long Id) {
        return writeFormRepository.findOneWriteForm(Id);
    }

    public Form findOneFormByWriteFormId(Long Id) {
        WriteForm writeForm = writeFormRepository.findOneWriteForm(Id);
        return writeFormRepository.findOneFormByWriteForm(writeForm);
    }
    public Object findOneByForm(Form form) {
        return writeFormRepository.findOneByForm(form);
    }

}
