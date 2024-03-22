package johnny.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {
    private Long id;
    private String email;
    private Integer status;//0-not activated; 1-activated
    private String validateCode;
    private Date registerTime;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getRegisterTime() {
        return registerTime;
    }

    @Transient
    public Date getLastActivateTime() {
        Calendar cl = Calendar.getInstance();
        cl.setTime(registerTime);
        cl.add(Calendar.DATE, 2);//day?
        return cl.getTime();
    }

}
