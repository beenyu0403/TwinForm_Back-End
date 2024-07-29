package proj.been433.twinformback.writeform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.been433.twinformback.question.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Form {
    @Id
    @GeneratedValue
    @Column(name = "form_id")
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "form", fetch = FetchType.LAZY)
    //@JoinColumn(name = "question_id")
    private List<Question> questions = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writeForm_id")
    private WriteForm writeForm;

    private String title;

    private String minDate;

    private String maxDate;

    public static Form createForm(String title, String minDate, String maxDate) {
        Form form = new Form();
        form.setTitle(title);
        form.setMinDate(minDate);
        form.setMaxDate(maxDate);

        return form;
    }
}