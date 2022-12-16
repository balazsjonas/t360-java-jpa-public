package bjonas.model;

import com.google.common.base.Objects;
import lombok.*;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Cicus extends Animal{

    private String colour;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="id")
//    private Person owner;

    public Cicus(String name, String colour) {
        super(name);
        this.colour = colour;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        Cicus cicus = (Cicus) o;
//        return Objects.equal(colour, cicus.colour);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hashCode(super.hashCode(), colour);
//    }
}
