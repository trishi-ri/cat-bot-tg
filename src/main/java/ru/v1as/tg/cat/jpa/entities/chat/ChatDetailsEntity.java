package ru.v1as.tg.cat.jpa.entities.chat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDetailsEntity {

    @Id private Long id;

    @OneToOne(optional = false)
    private ChatEntity chat;

    @Column(nullable = false)
    private boolean isCatPollEnabled;

    @Column(nullable = false)
    private Integer membersAmount;
}
