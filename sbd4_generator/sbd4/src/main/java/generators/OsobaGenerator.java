package generators;

import generators.utils.GeneratorUtils;
import schema.*;

import java.util.ArrayList;
import java.util.List;

public class OsobaGenerator {

    public List<Osoba> generate(Long count) {
        List<Osoba> list = new ArrayList<>();
        for(int i=0; i<count; i++) {
            list.add(            Osoba.builder()
                    .id((long) i +1)
                    .imie(GeneratorUtils.generateString())
                    .nazwisko(GeneratorUtils.generateString())
                    .pesel(GeneratorUtils.generatePesel())
                    .plec(GeneratorUtils.generateSex())
                    .email(GeneratorUtils.generateEmail())
                    .telefon(GeneratorUtils.generatePhone())
                    .lubiList(new ArrayList<>())
                    .znajomiList(new ArrayList<>()).build());
        }
        return list;
    }

    public void fillLists(List<Osoba> osobaList, List<Lubi> lubiList, List<Znajomi> znajomiList) {
        osobaList.forEach(osoba -> {
            lubiList.forEach(lubi -> {
                if(lubi.getOsoba().equals(osoba)) {
                    osoba.getLubiList().add(lubi);
                }
            });
            znajomiList.forEach(znajomi -> {
                if(znajomi.getZnajomy1().equals(osoba) || znajomi.getZnajomy2().equals(osoba)) {
                    osoba.getZnajomiList().add(znajomi);
                }
            });
        });
    }
}
