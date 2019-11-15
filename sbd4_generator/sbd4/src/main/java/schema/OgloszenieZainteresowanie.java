package schema;

import annotiations.ImportObject;
import annotiations.JsonExclude;
import annotiations.JsonObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import schema.base.BaseSchema;

@Builder
@Getter
@Setter
public class OgloszenieZainteresowanie extends BaseSchema {
    @JsonExclude
    Long idogloszeniezainteresowanie;
    @JsonObject
    @ImportObject
    Zainteresowanie zainteresowanie;
    @JsonExclude
    @ImportObject
    OgloszeniePracownicze ogloszenie;

    protected Long getPrimaryKey(){
        return idogloszeniezainteresowanie;
    }
}
