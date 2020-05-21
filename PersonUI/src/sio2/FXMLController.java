package sio2;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import sio2.model.Person;
import sio2.model.SampleData;
import javafx.beans.value.ObservableValue.*;


public class FXMLController {
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextArea notesTextArea;
    @FXML
    private Button createButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button removeButton;
    ChangeListener< ?super Person> personChangeListener;

    @FXML
    private void createButtonAction(ActionEvent actionEvent) {
        System.out.println("Personne ajoutée");
        Person person = new Person(firstnameTextField.getText(), lastnameTextField.getText(), notesTextArea.getText());
        personList.add(person);
        // on sélectionne ce nouvel ajout
        listView.getSelectionModel().select(person);
    }

    @FXML
    private void removeButtonAction(ActionEvent actionEvent) {
        System.out.println("Suppression de : " + selectedPerson);
        personList.remove(selectedPerson);
    }
    @FXML
    private void updateButtonAction(ActionEvent actionEvent) {
        System.out.println("Mise à jour de : " + selectedPerson);
        Person p = listView.getSelectionModel().getSelectedItem();
                listView.getSelectionModel().selectedItemProperty().removeListener(personChangeListener);
        p.setPrenom(firstnameTextField.getText());
        p.setNom(lastnameTextField.getText());
        p.setNote(notesTextArea.getText());
        // on met un écouteur sur ce ‘nouvel’ objet
        listView.getSelectionModel().selectedItemProperty().addListener(personChangeListener);
        // on remet le drapeau de modif de propriété à ‘false’ pour le rendre à nouveau modifiable
        modifiedProperty.set(false);
    }

    @FXML
    private void handleKeyAction(KeyEvent keyEvent) {
        modifiedProperty.set(true);
    }

    @FXML
    private ListView<Person> listView;


    private final ObservableList<Person> personList = FXCollections.observableArrayList(Person.extractor);
    private Person selectedPerson;
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);



    public void initialize(){

        removeButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());

        updateButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull().or(modifiedProperty.not())
                .or(firstnameTextField.textProperty().isEmpty().or(lastnameTextField.textProperty().isEmpty())));

        SampleData.fillSampleData(personList);
        listView.setItems(personList);



        listView.getSelectionModel().selectedItemProperty().addListener(
                    personChangeListener = (observable, oldValue, newValue)->{
                // si rien n’est sélectionné alors ‘newValue’ peut être null
                selectedPerson = newValue;
                modifiedProperty.set(false);
                if (newValue != null) {
                    //mettre à jour les champs
                    firstnameTextField.setText(selectedPerson.getPrenom());
                    lastnameTextField.setText(selectedPerson.getNom());
                    notesTextArea.setText(selectedPerson.getNote());
                } else {
                    firstnameTextField.setText("");
                    lastnameTextField.setText("");
                    notesTextArea.setText("");
                }
            });

        SortedList<Person> sortedList = new SortedList<>(personList);

// Maintenant que ‘sortedList’ est créée il faut trier les éléments en les comparant un à un
        sortedList.setComparator((p1, p2) -> {
            int result = p1.getNom().compareToIgnoreCase(p2.getNom());
            if (result == 0) {
                result = p1.getPrenom().compareToIgnoreCase(p2.getPrenom());
            }
            return result;
        });
        listView.setItems(sortedList);

    }


}
