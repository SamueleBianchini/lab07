package it.unibo.nestedenum;

import java.util.Comparator;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    private static final int LONG = 31;
    private static final int NORMAL = 30;
    private static final int SHORT = 28;

    public enum Mesi{
        JANUARY("January"),
        FEBRUARY("February"),
        MARCH("March"),
        APRIL("April"),
        MAY("May"),
        JUNE("June"),
        JULY("July"),
        AUGUST("August"),
        SEPTEMBER("September"),
        OCTOBER("October"),
        NOVEMBER("November"),
        DECEMBER("December");

        private final String nome;
        private final int giorni;

        Mesi(String nome){
            this.nome=nome;
            switch(nome){
                case "February" : this.giorni = SHORT; break;
                case "November" : this.giorni = NORMAL; break;
                case "April" : this.giorni = NORMAL; break;
                case "June" : this.giorni = NORMAL; break;
                case "September" : this.giorni = NORMAL; break;
                default :this.giorni = LONG;
            }
        }

        public String getName(){
            return this.nome;
        }

        public int getDays(){
            return this.giorni;
        }
        
        public static Mesi fromString(String stringa){
            if(stringa == null){
                throw new NullPointerException("La stringa inserita non è valida");
            }
            int i = 0;
            Mesi ris = null;
            for (Mesi selezione : Mesi.values()){
                if(selezione.getName().equalsIgnoreCase(stringa) || 
                selezione.getName().toLowerCase().startsWith(stringa.toLowerCase())){
                    i++;
                    if(i>1){
                        throw new IllegalArgumentException("Il mese inserito è ambiguo");
                    }
                    ris = selezione;
                }
            }
            if(ris == null){
                throw new IllegalArgumentException("Non esiste il mese inserito");
            }
            return ris;
        }
    }

    private static int nullControl(String a, String b){
        if(a == null){
            if(b == null){
                return 0;
            } else {
                return -1;
            }
        } else if(b==null){
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new Comparator<String>(){
            public int compare(String a, String b){
                if(nullControl(a , b) == 2){
                    Mesi meseA = Mesi.fromString(a);
                    Mesi meseB = Mesi.fromString(b);
                    return Integer.compare(meseA.getDays(),meseB.getDays());
                } else {
                    return nullControl(a, b);
                }
            }
        };
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new Comparator<String>(){
            public int compare(String a, String b){
                if(nullControl(a , b) == 2){
                    Mesi meseA = Mesi.fromString(a);
                    Mesi meseB = Mesi.fromString(b);
                    return Integer.compare(meseA.ordinal(),meseB.ordinal());
                } else {
                    return nullControl(a, b);
                }
            }
        };
    }

    public static class SortByMonthOrder implements Comparator<String>{
        public int compare(String a, String b){
            if(nullControl(a , b) == 2){
                Mesi meseA = Mesi.fromString(a);
                Mesi meseB = Mesi.fromString(b);
                return Integer.compare(meseA.ordinal(),meseB.ordinal());
            } else {
                return nullControl(a, b);
            }
        }
    }

    public static class SortByDate implements Comparator<String>{
        public int compare(String a, String b){
            if(nullControl(a , b) == 2){
                Mesi meseA = Mesi.fromString(a);
                Mesi meseB = Mesi.fromString(b);
                return Integer.compare(meseA.getDays(),meseB.getDays());
            } else {
                return nullControl(a, b);
            }
        }
    }
}
