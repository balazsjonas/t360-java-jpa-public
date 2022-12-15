package bjonas.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person {

    @Id
    @GeneratedValue(generator = "pGen")
    @TableGenerator(name = "pGen", table="person_id_gen",
    pkColumnName = "gen_name",
    valueColumnName = "gen_val",

            initialValue = 123, allocationSize = 10)
    private Long id;

    private String name;

    public Person(String name) {
        this.name = name;
    }
}