package codagebits;

public class CodeurSansChev implements CodeurBits {
    private int[] tampon; private int n; private int k;
    private static final int ENTETE = 3; // [0]=n, [1]=k, [2]=flags(0)

    @Override public int[] compresser(int[] tableau){
        if (tableau == null) throw new IllegalArgumentException("tableau null");
        n = tableau.length;
        int max = 0;
        for (int v : tableau) {
            if (v < 0) throw new IllegalArgumentException("Seulement >=0");
            if (v > max) max = v;
        }
        k = OutilsBits.bitsNecessaires(max);
        int parMot = Math.max(1, 32/Math.max(1,k));
        int nbMots = (n + parMot - 1) / parMot;

        tampon = new int[ENTETE + Math.max(1, nbMots)];
        tampon[0]=n; tampon[1]=k; tampon[2]=0;

        int idx=0;
        for (int bloc=0; bloc<nbMots; bloc++){
            int acc=0, shift=0;
            for (int j=0; j<parMot && idx<n; j++, idx++){
                acc |= (tableau[idx] & OutilsBits.masque(k)) << shift;
                shift += k;
            }
            tampon[ENTETE + bloc] = acc;
        }
        return tampon;
    }

    @Override public int[] decompresser(int[] compresse){
        if (compresse == null || compresse.length < ENTETE) throw new IllegalArgumentException("buffer invalide");
        tampon = compresse; n = tampon[0]; k = tampon[1];
        int[] out = new int[n];
        int parMot = Math.max(1,32/Math.max(1,k));
        int idx=0;
        for (int bloc=0; idx<n; bloc++){
            int acc = tampon[ENTETE + bloc];
            for (int j=0; j<parMot && idx<n; j++, idx++){
                out[idx] = acc & OutilsBits.masque(k);
                acc >>>= k;
            }
        }
        return out;
    }

    @Override public int acceder(int index){
        if (index < 0 || index >= n) throw new IndexOutOfBoundsException();
        int parMot = Math.max(1,32/Math.max(1,k));
        int bloc = index / parMot, pos = index % parMot;
        int acc = tampon[ENTETE + bloc];
        acc >>>= (pos * k);
        return acc & OutilsBits.masque(k);
    }
}
