import generators.KandydatGenerator;
import generators.KlientGenerator;
import generators.LubiGenerator;
import generators.OgloszenieGenerator;
import generators.OgloszenieZainteresowanieGenerator;
import generators.OsobaGenerator;
import generators.ZainteresowanieGenerator;
import generators.ZnajomiGenerator;
import generators.utils.RandomObject;
import java.util.List;
import schema.Kandydat;
import schema.Klient;
import schema.Lubi;
import schema.OgloszeniePracownicze;
import schema.OgloszenieZainteresowanie;
import schema.Osoba;
import schema.Zainteresowanie;
import schema.Znajomi;

public class Main {
    private static long count = /*6250L*/ /*12500L*/ 25000L;
    private static long count_osoba = count / 4;
    private static long count_kandydat = count * 4;
    private static long count_znajomi = (long) (count * 6.75);
    RandomObject<Osoba> randomObjectOsoba;
    RandomObject<OgloszeniePracownicze> randomObjectOgloszenie;
    RandomObject<Zainteresowanie> randomObjectZainteresowanie;
    RandomObject<Klient> randomObjectKlient;
    KandydatGenerator kandydatGenerator;
    KlientGenerator klientGenerator;
    LubiGenerator lubiGenerator;
    OgloszenieGenerator ogloszenieGenerator;
    OgloszenieZainteresowanieGenerator ogloszenieZainteresowanieGenerator;
    OsobaGenerator osobaGenerator;
    ZainteresowanieGenerator zainteresowanieGenerator;
    ZnajomiGenerator znajomiGenerator;
    List<Osoba> osobaList;
    List<Zainteresowanie> zainteresowanieList;
    List<Znajomi> znajomiList;
    List<Klient> klientList;
    List<OgloszeniePracownicze> ogloszeniePracowniczeList;
    List<Kandydat> kandydatList;
    List<OgloszenieZainteresowanie> ogloszenieZainteresowanieList;
    List<Lubi> lubiList;

    private void prepareObjects() {
        randomObjectOsoba = new RandomObject<>();
        randomObjectOgloszenie = new RandomObject<>();
        randomObjectZainteresowanie = new RandomObject<>();
        randomObjectKlient = new RandomObject<>();
        kandydatGenerator = new KandydatGenerator(randomObjectOsoba, randomObjectOgloszenie);
        klientGenerator = new KlientGenerator(randomObjectOsoba);
        lubiGenerator = new LubiGenerator(randomObjectZainteresowanie, randomObjectOsoba);
        ogloszenieGenerator = new OgloszenieGenerator(randomObjectKlient);
        ogloszenieZainteresowanieGenerator = new OgloszenieZainteresowanieGenerator(randomObjectOgloszenie, randomObjectZainteresowanie);
        osobaGenerator = new OsobaGenerator();
        zainteresowanieGenerator = new ZainteresowanieGenerator();
        znajomiGenerator = new ZnajomiGenerator(randomObjectOsoba);
    }

    private void generate() {
        osobaList = osobaGenerator.generate(count_osoba);
        zainteresowanieList = zainteresowanieGenerator.generate(count);
        znajomiList = znajomiGenerator.generate(count_znajomi, osobaList);
        klientList = klientGenerator.generate(count, osobaList);
        ogloszeniePracowniczeList = ogloszenieGenerator.generate(count, klientList);
        kandydatList = kandydatGenerator.generate(count_kandydat, osobaList, ogloszeniePracowniczeList);
        ogloszenieZainteresowanieList = ogloszenieZainteresowanieGenerator.generate(count, ogloszeniePracowniczeList, zainteresowanieList);
        lubiList = lubiGenerator.generate(count, osobaList, zainteresowanieList);

        osobaGenerator.fillLists(osobaList, lubiList, znajomiList);
        ogloszenieGenerator.fillLists(ogloszeniePracowniczeList, ogloszenieZainteresowanieList, kandydatList);
        kandydatGenerator.fillLists(kandydatList);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.prepareObjects();
        main.generate();
        FileWriter.writeJsonUsingBufferedWriter(main.osobaList, main.ogloszeniePracowniczeList, main.kandydatList, main.klientList);
        FileWriter.writeImportsUsingBufferedWriter(main.osobaList, main.zainteresowanieList, main.znajomiList,
                main.klientList, main.ogloszeniePracowniczeList, main.kandydatList, main.ogloszenieZainteresowanieList, main.lubiList);
    }
}
