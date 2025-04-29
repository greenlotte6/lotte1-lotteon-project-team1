/*
    작성자 : 손준오(sjo112777)

*/
package com.example.lotteon.entity.cs;

import com.example.lotteon.entity.user.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "qna")
public class Qna {

    private static final String[] STATUSES = {"open", "close"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "user_id")
    private Member member_id;

    private String title;
    private String content;

    @CreationTimestamp
    private LocalDate register_date;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Article_Type type_id;

    private String status;

}
