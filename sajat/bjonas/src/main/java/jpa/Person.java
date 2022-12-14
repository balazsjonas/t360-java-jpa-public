package jpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="person")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ssn;


    private String name;

    public Person(String name, int ssn) {
        this.ssn = ssn;
        this.name = name;
    }

    @Override
    public int hashCode() {
//        return ssn;
        return id.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
//        return ssn == person.ssn;
        return id.equals(person.id);
    }
}
