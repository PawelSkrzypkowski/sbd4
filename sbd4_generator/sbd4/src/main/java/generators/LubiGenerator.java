package generators;

import generators.utils.GeneratorUtils;
import generators.utils.RandomObject;
import lombok.AllArgsConstructor;
import schema.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class LubiGenerator {
    RandomObject<Zainteresowanie> randomObjectZainteresowanie;
    RandomObject<Osoba> randomObjectOsoba;

    public List<Lubi> generate(long count, List<Osoba> osobaList,
                               List<Zainteresowanie> zainteresowanieList) {
        List<Lubi> list = new ArrayList<>();
        for(int i=0; i<count; i++) {
            list.add(Lubi.builder()
                    .idlubi((long) i + 1)
                    .odkiedy(GeneratorUtils.generateDate())
                    .stopienzainteresowania(GeneratorUtils.generateFrom1To10())
                    .osoba(randomObjectOsoba.randomObject(osobaList))
                    .zainteresowanie(randomObjectZainteresowanie.randomObject(zainteresowanieList))
                    .build());
        }
        return list;
    }
}
