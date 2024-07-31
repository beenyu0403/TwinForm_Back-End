package proj.been433.twinformback.writeform;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.datetime.DateFormatter;
import proj.been433.twinformback.member.Member;
import proj.been433.twinformback.writeform.Form;
import proj.been433.twinformback.writeform.FormStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "writeForms")
@Getter
@Setter
public class WriteForm {
    @Id
    @GeneratedValue
    @Column(name = "writeForm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "writeForm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;

    private LocalDateTime writeDate;

    @Enumerated(EnumType.STRING)
    private FormStatus status; // 폼 상태 (RESERVATION, ONGOING, TERMINATE)

    public void setMember(Member member) {
        this.member = member;
        member.getWriteForms().add(this);
    }

    public void setForm(Form form) {
        this.form = form;
        form.setWriteForm(this);
    }

    public static WriteForm createWriteForm(Member member, Form form) {
        WriteForm writeForm = new WriteForm();
        writeForm.setMember(member);
        writeForm.setForm(form);

        changeFormStatus(form, writeForm);

        writeForm.setWriteDate(LocalDateTime.now());
        return writeForm;
    }

    private static void changeFormStatus(Form form, WriteForm writeForm) {
        LocalDate min = LocalDate.parse(form.getMinDate());
        LocalDate max = LocalDate.parse(form.getMaxDate());
        LocalDate current = LocalDate.now();
        int compareToMinDate = current.compareTo(min);
        int compareToMaxDate = current.compareTo(max);

        if (compareToMinDate < 0) {
            writeForm.setStatus(FormStatus.RESERVATION);
        }else if (compareToMaxDate > 0) {
            writeForm.setStatus(FormStatus.TERMINATE);
        }else{
            writeForm.setStatus(FormStatus.ONGOING);
        }
    }

}
