package ru.gsa.biointerface.ui.window;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import ru.gsa.biointerface.domain.DomainException;
import ru.gsa.biointerface.domain.Examinations;
import ru.gsa.biointerface.domain.PatientRecords;
import ru.gsa.biointerface.domain.entity.Examination;
import ru.gsa.biointerface.domain.entity.PatientRecord;
import ru.gsa.biointerface.ui.UIException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PatientRecordOpenController extends AbstractWindow implements WindowWithProperty<PatientRecord> {
    int idSelectedRow = -1;
    private PatientRecord patientRecord;
    @FXML
    private Text idText;
    @FXML
    private Text secondNameText;
    @FXML
    private Text firstNameText;
    @FXML
    private Text middleNameText;
    @FXML
    private Text birthdayText;
    @FXML
    private Text icdText;
    @FXML
    private TextArea commentField;
    @FXML
    private TableView<Examination> tableView;
    @FXML
    private TableColumn<Examination, String> dateTimeCol;
    @FXML
    private TableColumn<Examination, Integer> deviceIdCol;

    public WindowWithProperty<PatientRecord> setProperty(PatientRecord patientRecord) {
        if (patientRecord == null)
            throw new NullPointerException("patientRecord is null");

        this.patientRecord = patientRecord;

        return this;
    }

    @Override
    public void showWindow() throws UIException {
        if (resourceSource == null || transitionGUI == null)
            throw new UIException("resourceSource or transitionGUI is null. First call setResourceAndTransition()");
        if (patientRecord == null)
            throw new UIException("patientRecord is null. First call setParameter()");

        idText.setText(String.valueOf(patientRecord.getId()));
        secondNameText.setText(patientRecord.getSecondName());
        firstNameText.setText(patientRecord.getFirstName());
        middleNameText.setText(patientRecord.getMiddleName());
        if (patientRecord.getIcd() != null)
            icdText.setText(patientRecord.getIcd().toString());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        birthdayText.setText(patientRecord.getBirthday().format(dateFormatter));

        tableView.getItems().clear();
        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                onMouseClickedTableView(mouseEvent);
            }
        });
        dateTimeCol.setCellValueFactory(param -> {
            DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime dateTime = param.getValue().getDateTime();
            return new SimpleObjectProperty<>(dateTime.format(dateFormatter1));
        });
        deviceIdCol.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDevice().getId()));

        ObservableList<Examination> list = FXCollections.observableArrayList();
        try {
            list.addAll(Examinations.getSetByPatientRecordId(patientRecord));
        } catch (DomainException e) {
            e.printStackTrace();
        }
        tableView.setItems(list);
        transitionGUI.show();
    }

    @Override
    public String getTitleWindow() {
        return ": patient record";
    }

    @Override
    public void resizeWindow(double height, double width) {

    }

    public void commentFieldChange() {
        if (!commentField.getText().equals(patientRecord.getComment())) {
            patientRecord.setComment(commentField.getText());
            try {
                PatientRecords.update(patientRecord);
            } catch (DomainException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBack() {
        try {
            generateNewWindow("PatientRecords.fxml")
                    .showWindow();
        } catch (UIException e) {
            e.printStackTrace();
        }
    }

    public void onAdd() {
        try {
            ((WindowWithProperty<PatientRecord>) generateNewWindow("BiointerfaceData.fxml"))
                    .setProperty(patientRecord)
                    .showWindow();
        } catch (UIException e) {
            e.printStackTrace();
        }
    }

    public void onMouseClickedTableView(MouseEvent mouseEvent) {
        if (idSelectedRow != tableView.getFocusModel().getFocusedCell().getRow()) {
            idSelectedRow = tableView.getFocusModel().getFocusedCell().getRow();
            commentField.setText(
                    tableView.getItems().get(idSelectedRow).getComment()
            );
            //deleteButton.setDisable(false);
            commentField.setDisable(false);
        }

//        if (mouseEvent.getClickCount() == 2) {
//            FXMLLoader loader = new FXMLLoader(rootObject.getClass().getResource("PatientRecordOpen.fxml"));
//            try {
//                transitionGUI.transition(loader);
//                PatientRecordOpen controller = loader.getController();
//                controller.setPatientRecord(tableView.getItems().get(idSelectedRow));
//                controller.uploadContent(rootObject, transitionGUI);
//            } catch (UIException e) {
//                e.printStackTrace();
//            }
//        }
    }
}