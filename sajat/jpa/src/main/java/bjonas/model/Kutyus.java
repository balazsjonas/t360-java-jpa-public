package bjonas.model;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Kutyus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kutyusId;
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}) // (fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    private Person owner;

    public Kutyus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Kutyus{" +
                "kutyusId=" + kutyusId +
                ", name='" + name + '\'' +
                '}';
    }
}
