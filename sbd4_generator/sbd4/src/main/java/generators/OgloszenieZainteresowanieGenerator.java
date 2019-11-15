package generators;

import generators.utils.RandomObject;
import lombok.AllArgsConstructor;
import schema.OgloszeniePracownicze;
import schema.OgloszenieZainteresowanie;
import schema.Zainteresowanie;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OgloszenieZainteresowanieGenerator {
    RandomObject<OgloszeniePracownicze> randomObjectOgloszenie;
    RandomObject<Zainteresowanie> randomObjectZainteresowanie;

    public List<OgloszenieZainteresowanie> generate(long count, List<OgloszeniePracownicze> ogloszeniePracowniczeList,
                                                    List<Zainteresowanie> zainteresowanieList) {
        List<OgloszenieZainteresowanie> list = new ArrayList<>();
        for(int i=0; i<count; i++) {
            list.add(OgloszenieZainteresowanie.builder()
                    .idogloszeniezainteresowanie((long) i + 1)
                    .ogloszenie(randomObjectOgloszenie.randomObject(ogloszeniePracowniczeList))
                    .zainteresowanie(randomObjectZainteresowanie.randomObject(zainteresowanieList)).build());
        }
        return list;
    }
}
