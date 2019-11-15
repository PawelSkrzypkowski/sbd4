package generators;

import generators.utils.GeneratorUtils;
import schema.Zainteresowanie;

import java.util.ArrayList;
import java.util.List;

public class ZainteresowanieGenerator {

    public List<Zainteresowanie> generate(Long count) {
        List<Zainteresowanie> list = new ArrayList<>();
        for(int i=0; i<count; i++) {
            list.add(Zainteresowanie.builder()
                    .nazwa(GeneratorUtils.generateString())
                    .idzainteresowania((long) i+1).build());
        }
        return list;
    }
}
