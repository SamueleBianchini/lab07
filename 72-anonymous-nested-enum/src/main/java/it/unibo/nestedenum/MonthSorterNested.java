package it.unibo.nestedenum;

import java.util.Comparator;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    private static final int LONG = 31;
    private static final int NORMAL = 30;
    private static final int SHORT = 28;

    public enum Month{
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

        private final String name;
        private final int days;

        Month(String name){
            this.name = name;
            switch(name) {
                case "February" : this.days = SHORT; break;
                case "November" : this.days = NORMAL; break;
                case "April" : this.days = NORMAL; break;
                case "June" : this.days = NORMAL; break;
                case "September" : this.days = NORMAL; break;
                default : this.days = LONG;
            }
        }

        public String getName(){
            return this.name;
        }

        public int getDays(){
            return this.days;
        }
        
        public static Month fromString(String string){
            if(string == null){
                throw new NullPointerException("The inserted string is not valid");
            }
            int i = 0;
            Month res = null;
            for (Month selection : Month.values()){
                if(selection.getName().equalsIgnoreCase(string) || 
                selection.getName().toLowerCase().startsWith(string.toLowerCase())){
                    i++;
                    if(i > 1){
                        throw new IllegalArgumentException("The inserted month is ambiguous");
                    }
                    res = selection;
                }
            }
            if(res == null){
                throw new IllegalArgumentException("The inserted month does not exist");
            }
            return res;
        }
    }

    private static int nullControl(String a, String b){
        if(a == null){
            if(b == null){
                return 0;
            } else {
                return -1;
            }
        } else if(b == null){
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new Comparator<String>(){
            public int compare(String a, String b){
                if(nullControl(a, b) == 2){
                    Month A = Month.fromString(a);
                    Month B = Month.fromString(b);
                    return Integer.compare(A.getDays(), B.getDays());
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
                    Month A = Month.fromString(a);
                    Month B = Month.fromString(b);
                    return Integer.compare(A.ordinal(), B.ordinal());
                } else {
                    return nullControl(a, b);
                }
            }
        };
    }

    public static class SortByMonthOrder implements Comparator<String>{
        public int compare(String a, String b){
            if(nullControl(a , b) == 2){
                Month A = Month.fromString(a);
                Month B = Month.fromString(b);
                return Integer.compare(A.ordinal(), B.ordinal());
            } else {
                return nullControl(a, b);
            }
        }
    }

    public static class SortByDate implements Comparator<String>{
        public int compare(String a, String b){
            if(nullControl(a , b) == 2){
                Month A = Month.fromString(a);
                Month B= Month.fromString(b);
                return Integer.compare(A.getDays(), B.getDays());
            } else {
                return nullControl(a, b);
            }
        }
    }
}
