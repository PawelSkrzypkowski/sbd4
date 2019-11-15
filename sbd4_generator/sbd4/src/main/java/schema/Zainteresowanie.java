package schema;

import annotiations.JsonExclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import schema.base.BaseSchema;

@Builder
@Getter
@Setter
public class Zainteresowanie extends BaseSchema {
    @JsonExclude
    Long idzainteresowania;
    String nazwa;

    protected Long getPrimaryKey(){
        return idzainteresowania;
    }
}
