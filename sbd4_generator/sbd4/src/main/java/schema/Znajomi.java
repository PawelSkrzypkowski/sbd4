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
public class Znajomi extends BaseSchema {
    @JsonExclude
    Long idznajomi;

    @JsonObject(excludeFields = {"lubiList", "znajomiList"})
    @ImportObject
    Osoba znajomy1;
    @JsonObject(excludeFields = {"lubiList", "znajomiList"})
    @ImportObject
    Osoba znajomy2;

    protected Long getPrimaryKey(){
        return idznajomi;
    }
}
