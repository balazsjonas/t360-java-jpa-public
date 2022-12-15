package bjonas.model;

import lombok.*;

import javax.persistence.*;

@Entity
@IdClass(Key.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChessTable {

    @Id
    private String colChar;
    @Id
    private int rowNum;

    private String piece;

    @PrePersist
    void prePersist() {
        System.err.println("(bjonas) PrePersist: "+ this.piece);
    }
    @PostPersist
    void postPersist() {
        System.err.println("(bjonas) PostPersist: " + this.piece);
    }
}
