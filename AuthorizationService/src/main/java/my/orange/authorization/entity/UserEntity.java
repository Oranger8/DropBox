package my.orange.authorization.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "USER", schema = "main")
public class UserEntity {

    @Id
    @Column(name = "LOGIN")
    private String login;

    @Basic
    @Column(name = "PASSWORD")
    private int password;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return password == that.password &&
                Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
}
