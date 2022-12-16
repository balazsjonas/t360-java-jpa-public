package bjonas.model;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Kutyus {
    @Id
    private Long kutyusId;
    private String name;

    @ManyToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private Person owner;

    public Kutyus(String name) {
        this.name = name;
    }
}
