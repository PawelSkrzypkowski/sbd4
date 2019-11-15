package generators;

import generators.utils.GeneratorUtils;
import generators.utils.RandomObject;
import lombok.AllArgsConstructor;
import schema.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OgloszenieGenerator {
    RandomObject<Klient> randomObjectKlient;

    public List<OgloszeniePracownicze> generate(Long count, List<Klient> klientList) {
        List<OgloszeniePracownicze> list = new ArrayList<>();
        for(int i=0; i<count; i++) {
            list.add(OgloszeniePracownicze.builder()
                    .idogloszenia((long) i +1)
                    .opis(GeneratorUtils.generateString())
                    .zarobki(GeneratorUtils.generateSalary())
                    .zleceniodawca(randomObjectKlient.randomObject(klientList))
                    .ogloszenieZainteresowanieList(new ArrayList<>())
                    .kandydatList(new ArrayList<>()).build());
        }
        return list;
    }

    public void fillLists(List<OgloszeniePracownicze> ogloszenieList, List<OgloszenieZainteresowanie> ogloszenieZainteresowanieList, List<Kandydat> kandydatList) {
        ogloszenieList.forEach(ogloszenie -> {
            ogloszenieZainteresowanieList
                .stream()
                .filter(ogloszenieZainteresowanie -> ogloszenieZainteresowanie.getOgloszenie().equals(ogloszenie))
                .forEach(ogloszenieZainteresowanie -> ogloszenie.getOgloszenieZainteresowanieList().add(ogloszenieZainteresowanie));
            kandydatList
                    .stream()
                    .filter(kandydat -> kandydat.getOgloszenie().equals(ogloszenie))
                    .forEach(kandydat -> ogloszenie.getKandydatList().add(kandydat));
        });
    }
}
