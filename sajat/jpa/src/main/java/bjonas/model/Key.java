package bjonas.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Key implements Serializable {
    private String colChar;
    private int rowNum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return rowNum == key.rowNum && Objects.equals(colChar, key.colChar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colChar, rowNum);
    }
}
