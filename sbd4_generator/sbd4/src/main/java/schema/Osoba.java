package schema;

import annotiations.JsonArray;
import annotiations.JsonCollection;
import annotiations.JsonExclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import schema.base.BaseSchema;

import java.util.List;

@JsonCollection
@Builder
@Getter
@Setter
public class Osoba extends BaseSchema {
    @JsonExclude
    Long id;
    String imie;
    String nazwisko;
    String pesel;
    Character plec;
    String email;
    String telefon;

    @JsonArray
    List<Lubi> lubiList;
    @JsonArray
    List<Znajomi> znajomiList;

    protected Long getPrimaryKey(){
        return id;
    }
}
