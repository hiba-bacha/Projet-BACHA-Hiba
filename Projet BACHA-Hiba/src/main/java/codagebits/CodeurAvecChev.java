package codagebits;

public class CodeurAvecChev implements CodeurBits {
private int[] tampon;
private int n;
private int k;


// En-tête : [0]=n, [1]=k, [2]=flags(1)
private static final int ENTETE = 3;


@Override
public int[] compresser(int[] tableau){
if (tableau == null) throw new IllegalArgumentException("tableau null");
n = tableau.length;
int max = 0;
for (int v : tableau) {
if (v < 0) throw new IllegalArgumentException("Seulement >=0 dans cette version");
if (v > max) max = v;
}
k = OutilsBits.bitsNecessaires(max);
long totalBits = (long) n * k;
int nbMots = (int) ((totalBits + 31) >>> 5); // ceil /32


tampon = new int[ENTETE + Math.max(1, nbMots)];
tampon[0] = n; tampon[1] = k; tampon[2] = 1;


long bitPos = ((long) ENTETE) << 5; // début des données (en bits)
for (int v : tableau) {
OutilsBits.ecrireBits(tampon, bitPos, k, v);
bitPos += k;
}
return tampon;
}


@Override
public int[] decompresser(int[] compresse){
if (compresse == null || compresse.length < ENTETE) throw new IllegalArgumentException("buffer invalide");
tampon = compresse; n = tampon[0]; k = tampon[1];
int[] sortie = new int[n];
long bitPos = ((long) ENTETE) << 5;
for (int i = 0; i < n; i++) {
sortie[i] = OutilsBits.lireBits(tampon, bitPos, k);
bitPos += k;
}
return sortie;
}


@Override
public int acceder(int index){
if (index < 0 || index >= n) throw new IndexOutOfBoundsException();
long bitPos = (((long) ENTETE) << 5) + (long) index * k;
return OutilsBits.lireBits(tampon, bitPos, k);
}
}