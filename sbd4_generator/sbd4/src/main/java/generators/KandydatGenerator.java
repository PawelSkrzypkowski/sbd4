package generators;

import generators.utils.GeneratorUtils;
import generators.utils.RandomObject;
import lombok.AllArgsConstructor;
import schema.Kandydat;
import schema.OgloszeniePracownicze;
import schema.Osoba;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class KandydatGenerator {
    RandomObject<Osoba> randomObjectOsoba;
    RandomObject<OgloszeniePracownicze> randomObjectOgloszenie;

    public List<Kandydat> generate(long count, List<Osoba> osobaList, List<OgloszeniePracownicze> ogloszeniePracowniczeList) {
        List<Kandydat> list = new ArrayList<>();
        for(int i=0; i<count; i++) {
            list.add(Kandydat.builder()
                    .idkandydata((long) i + 1)
                    .zaakceptowany(GeneratorUtils.generateBoolean())
                    .dataaplikacji(GeneratorUtils.generateDate())
                    .osoba(randomObjectOsoba.randomObject(osobaList))
                    .ogloszenie(randomObjectOgloszenie.randomObject(ogloszeniePracowniczeList))
                    .kandydujacyznajomiIds(new ArrayList<>())
                    .build());
        }
        return list;
    }

    public void fillLists(List<Kandydat> kandydatList) {
        kandydatList.forEach(kandydat -> kandydatList.forEach(znajomyKandydat -> {
            if(kandydat.getOgloszenie().equals(znajomyKandydat.getOgloszenie()) &&
                    !kandydat.getOsoba().equals(znajomyKandydat.getOsoba()) &&
                    kandydat.getOsoba().getZnajomiList()
                            .stream()
                            .anyMatch(znajomi -> znajomi.getZnajomy1().equals(znajomyKandydat.getOsoba()) ||
                                    znajomi.getZnajomy2().equals(znajomyKandydat.getOsoba()))) {
                kandydat.getKandydujacyznajomiIds().add(znajomyKandydat.getIdkandydata());
            }
        }));
    }
}
