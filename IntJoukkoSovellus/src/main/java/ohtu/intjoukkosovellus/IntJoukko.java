package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] lukujono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        lukujono = new int[KAPASITEETTI];
        taytaTaulukko(lukujono);
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;
    }

    public IntJoukko(int kapasiteetti) {
        if (kapasiteetti < 0) {
            return;
        }
        lukujono = new int[kapasiteetti];
        taytaTaulukko(lukujono);
        alkioidenLkm = 0;
        this.kasvatuskoko = OLETUSKASVATUS;

    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti väärin");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kapasiteetti2");
        }
        lukujono = new int[kapasiteetti];
        taytaTaulukko(lukujono);
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;

    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            lukujono[alkioidenLkm] = luku;
            alkioidenLkm++;

            if (alkioidenLkm % lukujono.length == 0) {
                int[] vanhaTaulukko = lukujono;
                lukujono = kasvataTaulukkoa(vanhaTaulukko, kasvatuskoko);
            }
            return true;
        }

        return false;
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                return true;
            }
        }
        return false;

    }

    public boolean poista(int luku) {
        if (!kuuluu(luku)) {
            return false;
        }

        int indeksi = haeIndeksi(luku);
        int edellinenArvo;

        if (indeksi != -1) {
            for (int j = indeksi; j < alkioidenLkm - 1; j++) {
                edellinenArvo = lukujono[j];
                lukujono[j] = lukujono[j + 1];
                lukujono[j + 1] = edellinenArvo;
            }
            alkioidenLkm--;
            return true;
        }

        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    private void taytaTaulukko(int[] lukujono) {
        for (int i = 0; i < lukujono.length; i++) {
            lukujono[i] = 0;
        }
    }

    public int[] kasvataTaulukkoa(int[] vanhaTaulukko, int kasvatuskoko) {
        int[] uusi = new int[vanhaTaulukko.length + kasvatuskoko];
        kopioiTaulukko(vanhaTaulukko, uusi);
        return uusi;
    }

    private int haeIndeksi(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == lukujono[i]) {
                return i;
            }
        }

        return -1;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        if (alkioidenLkm == 0) {
            return "{}";
        } else if (alkioidenLkm == 1) {
            return "{" + lukujono[0] + "}";
        } else {
            String tuotos = "{";
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += lukujono[i] + ", ";
            }
            tuotos += lukujono[alkioidenLkm - 1] + "}";
            return tuotos;
        }
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = lukujono[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        return teeYhdiste(aTaulu, bTaulu);
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        return teeLeikkaus(aTaulu, bTaulu);
    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();

        return teeErotus(aTaulu, bTaulu);
    }

    private static IntJoukko teeYhdiste(int[] aTaulu, int[] bTaulu) {
        IntJoukko yhdiste = new IntJoukko();

        for (int i = 0; i < aTaulu.length; i++) {
            yhdiste.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            yhdiste.lisaa(bTaulu[i]);
        }
        return yhdiste;
    }

    private static IntJoukko teeLeikkaus(int[] aTaulu, int[] bTaulu) {
        IntJoukko leikkaus = new IntJoukko();

        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    leikkaus.lisaa(bTaulu[j]);
                }
            }
        }

        return leikkaus;
    }

    private static IntJoukko teeErotus(int[] aTaulu, int[] bTaulu) {
        IntJoukko erotus = new IntJoukko();

        for (int i = 0; i < aTaulu.length; i++) {
            erotus.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            erotus.poista(i);
        }

        return erotus;
    }

}
