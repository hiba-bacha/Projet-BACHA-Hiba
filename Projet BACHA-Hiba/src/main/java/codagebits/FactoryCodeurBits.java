package codagebits;


/** FACTORY : sélectionne l'implémentation (avec/sans chevauchement). */
public final class FactoryCodeurBits {
private FactoryCodeurBits(){}
public static CodeurBits creer(String mode){
return switch (mode.toLowerCase()) {
case "avec" -> new CodeurAvecChev();
case "sans" -> new CodeurSansChev();
default -> throw new IllegalArgumentException("Mode inconnu: "+mode);
};
}
}