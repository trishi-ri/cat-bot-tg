package ru.v1as.tg.cat.jpa.entities.user;

import static org.apache.http.util.TextUtils.isEmpty;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.v1as.tg.cat.model.TgUser;

@Entity
@Data
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements TgUser {
    @Id private Integer id;

    @Column(unique = true)
    private String userName;

    private String firstName;
    private String lastName;
    private String languageCode;

    @Column(nullable = false)
    private boolean privateChat;

    public String getUsernameOrFullName() {
        if (isEmpty(userName)) {
            return Stream.of(firstName, lastName)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining(" "));
        } else {
            return "@" + userName;
        }
    }

    public boolean update(TgUser user) {
        boolean changed = false;
        if (Objects.equals(userName, user.getUserName())) {
            userName = user.getUserName();
            changed = true;
        }
        if (Objects.equals(firstName, user.getFirstName())) {
            firstName = user.getFirstName();
            changed = true;
        }
        if (Objects.equals(lastName, user.getLastName())) {
            lastName = user.getLastName();
            changed = true;
        }
        if (Objects.equals(languageCode, user.getLanguageCode())) {
            languageCode = user.getLanguageCode();
            changed = true;
        }
        return changed;
    }
    @Override
    public String toString() {
        return String.format("Usr[%s:%s]", id, getUsernameOrFullName());
    }
}
