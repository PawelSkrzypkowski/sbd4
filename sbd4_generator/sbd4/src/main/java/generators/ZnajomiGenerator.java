package generators;

import generators.utils.RandomObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import schema.Osoba;
import schema.Znajomi;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
public class ZnajomiGenerator {
    RandomObject<Osoba> randomObject;

    public List<Znajomi> generate(Long count, List<Osoba> osobaList) {
        List<Znajomi> list = new ArrayList<>();
        for(int i=0; i<count; i++) {
            Osoba friend = randomObject.randomObject(osobaList);
            list.add(Znajomi.builder()
                    .idznajomi((long) i+1)
                    .znajomy1(friend)
                    .znajomy2(secondFriend(friend, osobaList)).build());
        }
        return list;
    }

    private Osoba secondFriend(Osoba firstFriend, List<Osoba> osobaList) {
        Osoba osoba = null;
        while (osoba == null) {
            Osoba tempOsoba = randomObject.randomObject(osobaList);
            osoba = tempOsoba.equals(firstFriend) ? null : tempOsoba;
        }
        return osoba;
    }
}
