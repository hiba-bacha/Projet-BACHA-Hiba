
package app;
import codagebits.*; 
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
Map<String,String> p = lireArguments(args);
String mode = p.getOrDefault("mode", "sans"); // "sans"|"avec"
int n = Integer.parseInt(p.getOrDefault("taille", "20"));
int graine = Integer.parseInt(p.getOrDefault("graine", "1"));
int max = Integer.parseInt(p.getOrDefault("max", "4095"));


int[] donnees = genererDonnees(n, graine, max);
CodeurBits codeur = FactoryCodeurBits.creer(mode); // FACTORY


int[] compresse = codeur.compresser(donnees);
int i = n/2;
int v = (n>0) ? codeur.acceder(i) : -1; // accès direct demandé par le sujet
int[] decompresse = codeur.decompresser(compresse);


System.out.printf(Locale.ROOT,
"mode=%s n=%d get(%d)=%d ok=%b%n",
mode, n, i, v, Arrays.equals(donnees, decompresse));
}


private static Map<String,String> lireArguments(String[] a){
Map<String,String> m=new HashMap<>();
for(int i=0;i<a.length;i++) if(a[i].startsWith("--")){
String k=a[i].substring(2);
if(i+1<a.length && !a[i+1].startsWith("--")) m.put(k,a[++i]); else m.put(k,"");
}
return m;
}
private static int[] genererDonnees(int n,int graine,int max){
Random r=new Random(graine); int[] t=new int[n];
for(int i=0;i<n;i++) t[i]=r.nextInt(max+1); return t;
}
}

