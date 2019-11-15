package schema;

import annotiations.*;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import schema.base.BaseSchema;

@JsonCollection
@Builder
@Getter
@Setter
public class OgloszeniePracownicze extends BaseSchema {
    @JsonPrimary
    Long idogloszenia;
    String opis;
    Integer zarobki;

    @JsonObject
    @ImportObject
    Klient zleceniodawca;
    @JsonArray
    List<OgloszenieZainteresowanie> ogloszenieZainteresowanieList;
    @JsonArray
    List<Kandydat> kandydatList;

    protected Long getPrimaryKey(){
        return idogloszenia;
    }
}
