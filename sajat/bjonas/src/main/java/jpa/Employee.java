package jpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="emp_name")
    private String name;

    public Employee(String name) {
        this.name = name;
    }
}
