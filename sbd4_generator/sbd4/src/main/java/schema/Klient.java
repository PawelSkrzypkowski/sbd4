package schema;

import annotiations.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import schema.base.BaseSchema;

@JsonCollection
@Builder
@Getter
@Setter
public class Klient extends BaseSchema {
    @JsonExclude
    Long idklienta;
    String nazwafirmy;
    String nip;
    String wlasciciel;
    String telefon;
    String miasto;

    @JsonObject
    @ImportObject
    Osoba dzialalnosc;

    protected Long getPrimaryKey(){
        return idklienta;
    }
}
