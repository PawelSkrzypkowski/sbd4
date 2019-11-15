import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import schema.Kandydat;
import schema.Klient;
import schema.Lubi;
import schema.OgloszeniePracownicze;
import schema.OgloszenieZainteresowanie;
import schema.Osoba;
import schema.Zainteresowanie;
import schema.Znajomi;
import schema.base.BaseSchema;

class FileWriter {

    static void writeJsonUsingBufferedWriter(List<Osoba> osobaList, List<OgloszeniePracownicze> ogloszeniePracowniczeList, List<Kandydat> kandydatList, List<Klient> klientList) {
        writer(osobaList, null, null, klientList, ogloszeniePracowniczeList, kandydatList, null, null, true);
    }

    static void writeImportsUsingBufferedWriter(List<Osoba> osobaList, List<Zainteresowanie> zainteresowanieList,
                                                List<Znajomi> znajomiList, List<Klient> klientList,
                                                List<OgloszeniePracownicze> ogloszeniePracowniczeList, List<Kandydat> kandydatList,
                                                List<OgloszenieZainteresowanie> ogloszenieZainteresowanieList, List<Lubi> lubiList) {
        writer(osobaList, zainteresowanieList, znajomiList, klientList, ogloszeniePracowniczeList, kandydatList, ogloszenieZainteresowanieList, lubiList, false);
    }

    private static void writer(List<Osoba> osobaList, List<Zainteresowanie> zainteresowanieList, List<Znajomi> znajomiList,
                               List<Klient> klientList, List<OgloszeniePracownicze> ogloszeniePracowniczeList,
                               List<Kandydat> kandydatList, List<OgloszenieZainteresowanie> ogloszenieZainteresowanieList,
                               List<Lubi> lubiList, boolean isJson) {
        if (isJson) {
            writeJson(ogloszeniePracowniczeList, "ogloszenie");
        } else {
            java.io.FileWriter fr = null;
            BufferedWriter br = null;
            try {
                fr = new java.io.FileWriter("C:\\sbd4\\imports.sql");
                br = new BufferedWriter(fr);
                writeImports(osobaList, br);
                writeImports(zainteresowanieList, br);
                writeImports(znajomiList, br);
                writeImports(klientList, br);
                writeImports(ogloszeniePracowniczeList, br);
                writeImports(kandydatList, br);
                writeImports(ogloszenieZainteresowanieList, br);
                writeImports(lubiList, br);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writeJson(List list, String collectionName){
        java.io.FileWriter fr = null;
        BufferedWriter br = null;
        try {
            fr = new java.io.FileWriter("C:\\sbd4\\" + collectionName + ".json");
            br = new BufferedWriter(fr);
            br.write("[");
            for (int i=0; i<list.size(); i++) {
                if(i < list.size() - 1) {
                    br.write(list.get(i).toString() + ", ");
                } else {
                    br.write(list.get(i).toString());
                }
            }
            br.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeImports(List list, BufferedWriter br) throws IOException {
        br.write("SET IDENTITY_INSERT " + list.get(0).getClass().getSimpleName().toLowerCase() + " on;");
        for (Object obj : list) {
            BaseSchema baseSchema = (BaseSchema) obj;
            br.write(baseSchema.toImport());
        }
        br.write("SET IDENTITY_INSERT " + list.get(0).getClass().getSimpleName().toLowerCase() + " off;");
        br.write("\n\n\n\n");
    }
}
