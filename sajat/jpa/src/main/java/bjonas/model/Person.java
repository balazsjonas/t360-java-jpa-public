package bjonas.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pGen")
    @TableGenerator(name = "pGen", table = "person_id_gen",
            pkColumnName = "gen_name",
            valueColumnName = "gen_val",
            initialValue = 123, allocationSize = 10)

    private Long id;

    private String name;

    @OneToMany(mappedBy = "owner",
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Kutyus> kutyusok;

    public Person(String name) {
        this.name = name;
    }

    public void addKutyus(Kutyus kutyus) {
        if (kutyusok == null) {
            kutyusok = new HashSet<>();
        }
        kutyusok.add(kutyus);
        kutyus.setOwner(this);
    }
}
