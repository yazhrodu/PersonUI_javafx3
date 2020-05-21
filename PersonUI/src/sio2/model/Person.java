package sio2.model;

import com.sun.deploy.net.MessageHeader;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import java.util.Objects;

public class Person {
    private StringProperty prenom = new SimpleStringProperty(this, "prenom");
    private StringProperty nom = new SimpleStringProperty(this, "nom");
    private StringProperty note = new SimpleStringProperty(this, "note");


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return  Objects.equals(getPrenom(), person.getPrenom()) &&
                Objects.equals(getNom(), person.getNom()) &&
                Objects.equals(getNote(), person.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrenom(), getNom(), getNote());
    }

    private Observable lastnameProperty() {
        return nom;
    }
    private Observable firstnameProperty() { return prenom; }


    @Override
    public String toString() {
        return prenom.get() + " " + nom.get();
    }

    //Constructeur
    public Person(String prenom, String nom, String note) {
        this.prenom.set(prenom);
        this.nom.set(nom);
        this.note.set(note);
    }

    //Setter
    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }
    public void setNom(String nom) {
        this.nom.set(nom);
    }
    public void setNote(String note) {
        this.note.set(note);
    }

    //Getter
    public String getPrenom() {
        return prenom.get();
    }
    public String getNom() {
        return nom.get();
    }
    public String getNote() {
        return note.get();
    }

    //extrator
    public static Callback<Person, Observable[]> extractor = person -> new Observable[]
            { person.lastnameProperty(), person.firstnameProperty()};




}
