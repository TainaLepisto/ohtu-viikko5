package ohtu.intjoukkosovellus;

public class IntJoukko {

    private int KAPASITEETTI = 5; // aloitustalukon koko
    private int KASVATUSKOKO = 5;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        alustaJoukko(KAPASITEETTI, KASVATUSKOKO);
    }

    public IntJoukko(int kapasiteetti) {
        alustaJoukko(kapasiteetti, KASVATUSKOKO);
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        alustaJoukko(kapasiteetti, kasvatuskoko);
    }

    private void alustaJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasiteetti ei sallittu");
        }
        this.KAPASITEETTI = kapasiteetti;

        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kasvatuskoko ei sallittu");
        }
        this.KASVATUSKOKO = kasvatuskoko;

        luoTyhjaJoukko();

    }

    private void luoTyhjaJoukko() {
        ljono = new int[KAPASITEETTI];
        for (int i = 0; i < ljono.length; i++) {
            ljono[i] = 0;
        }
        alkioidenLkm = 0;
    }

    public boolean lisaa(int luku) {

        if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            if (alkioidenLkm == ljono.length) {
                kasvataKokoa();
            }
            return true;
        }
        return false;
    }

    private void kasvataKokoa() {
        int[] taulukkoOld = ljono;
        ljono = new int[alkioidenLkm + KASVATUSKOKO];
        kopioiTaulukko(taulukkoOld, ljono);
    }

    public boolean kuuluu(int luku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean poista(int luku) {
        int kohta = etsiPoistoKohta(luku);

        if (kohta != -1) {
            ljono[kohta]=0 ;
            int apu ;
            for (int j = kohta; j < alkioidenLkm - 1; j++) {
                apu = ljono[j];
                ljono[j] = ljono[j + 1];
                ljono[j + 1] = apu;
            }
            alkioidenLkm--;
            return true;
        }

        return false;
    }

    private int etsiPoistoKohta(int etsittavaLuku) {
        for (int i = 0; i < alkioidenLkm; i++) {
            if (etsittavaLuku == ljono[i]) {
                return i;
            }
        }
        return -1;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int getJoukonKoko() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");

        for (int i = 0; i < alkioidenLkm; i++) {
            sb.append(ljono[i]);
            if (i != alkioidenLkm - 1) {
                sb.append(", ");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko x = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaa(bTaulu[i]);
        }
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko y = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            for (int j = 0; j < bTaulu.length; j++) {
                if (aTaulu[i] == bTaulu[j]) {
                    y.lisaa(bTaulu[j]);
                }
            }
        }
        return y;

    }

    public static IntJoukko erotus(IntJoukko a, IntJoukko b) {
        IntJoukko z = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            z.lisaa(aTaulu[i]);
        }
        for (int i = 0; i < bTaulu.length; i++) {
            z.poista(i);
        }

        return z;
    }

}
