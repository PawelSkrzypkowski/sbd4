package schema;

import annotiations.ImportObject;
import annotiations.JsonExclude;
import annotiations.JsonObject;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import schema.base.BaseSchema;

@Builder
@Getter
@Setter
public class Lubi extends BaseSchema {
    @JsonExclude
    Long idlubi;
    Date odkiedy;
    Integer stopienzainteresowania;

    @JsonExclude
    @ImportObject
    Osoba osoba;
    @JsonObject
    @ImportObject
    Zainteresowanie zainteresowanie;

    protected Long getPrimaryKey(){
        return idlubi;
    }
}
