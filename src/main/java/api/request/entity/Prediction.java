package api.request.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class Prediction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=2000)
    private String data;

    public Prediction(String data){
        this.data =data;
    }

}
