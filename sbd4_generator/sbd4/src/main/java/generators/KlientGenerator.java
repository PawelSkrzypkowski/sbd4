package generators;

import generators.utils.GeneratorUtils;
import generators.utils.RandomObject;
import lombok.AllArgsConstructor;
import schema.Klient;
import schema.Lubi;
import schema.Osoba;
import schema.Zainteresowanie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class KlientGenerator {
    RandomObject<Osoba> randomObjectOsoba;

    public List<Klient> generate(long count, List<Osoba> osobaList) {
        List<Klient> list = new ArrayList<>();
        for(int i=0; i<count; i++) {
            Klient klient = Klient.builder()
                    .idklienta((long) i + 1)
                    .nazwafirmy(GeneratorUtils.generateString())
                    .nip(GeneratorUtils.generateNip())
                    .wlasciciel(GeneratorUtils.generateString())
                    .telefon(GeneratorUtils.generatePhone())
                    .miasto(GeneratorUtils.generateString()).build();
            Random random = new Random();
            if(random.nextInt(5) == 0) {
                klient.setDzialalnosc(randomObjectOsoba.randomObject(osobaList));
            }
            list.add(klient);
        }
        return list;
    }
}
