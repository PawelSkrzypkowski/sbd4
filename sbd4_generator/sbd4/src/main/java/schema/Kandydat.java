package schema;

import annotiations.*;
import lombok.Builder;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import schema.base.BaseSchema;

@JsonCollection
@Builder
@Getter
@Setter
public class Kandydat extends BaseSchema {
    @JsonExclude
    Long idkandydata;
    Boolean zaakceptowany;
    Date dataaplikacji;

    @JsonObject
    @ImportObject
    Osoba osoba;
    @JsonExclude
    @ImportObject
    OgloszeniePracownicze ogloszenie;
    @JsonArrayOfValues
    List<Long> kandydujacyznajomiIds;

    protected Long getPrimaryKey(){
        return idkandydata;
    }
}
