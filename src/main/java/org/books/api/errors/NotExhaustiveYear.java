package org.books.api.errors;

public class NotExhaustiveYear extends RuntimeException{

    public NotExhaustiveYear(String year){
        super("L'année n'est pas une valeur possible. Valeur envoyée: " + year);
    }
}
