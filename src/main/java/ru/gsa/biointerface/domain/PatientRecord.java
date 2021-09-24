package ru.gsa.biointerface.domain;

import ru.gsa.biointerface.domain.entity.IcdEntity;
import ru.gsa.biointerface.domain.entity.PatientRecordEntity;
import ru.gsa.biointerface.persistence.DAOException;
import ru.gsa.biointerface.persistence.dao.PatientRecordDAO;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class PatientRecord implements Comparable<PatientRecord> {
    private final PatientRecordEntity entity;
    private Icd icd;

    public PatientRecord(PatientRecordEntity patientRecordEntity) {
        if (patientRecordEntity.getId() == 0)
            throw new NullPointerException("id is null");
        if (patientRecordEntity.getSecondName() == null)
            throw new NullPointerException("secondName is null");
        if (patientRecordEntity.getFirstName() == null)
            throw new NullPointerException("firstName is null");
        if (patientRecordEntity.getMiddleName() == null)
            throw new NullPointerException("middleName is null");
        if (patientRecordEntity.getBirthday() == null)
            throw new NullPointerException("birthday is null");

        entity = patientRecordEntity;

        if (entity.getIcd() != null)
            icd = new Icd(entity.getIcd());
    }

    public PatientRecord(int id, String secondName, String firstName, String middleName, LocalDate birthday, IcdEntity icdEntity, String comment) {
        if (id == 0)
            throw new NullPointerException("id is null");
        if (secondName == null)
            throw new NullPointerException("secondName is null");
        if (firstName == null)
            throw new NullPointerException("firstName is null");
        if (middleName == null)
            throw new NullPointerException("middleName is null");
        if (birthday == null)
            throw new NullPointerException("birthday is null");

        entity = new PatientRecordEntity(id, secondName, firstName, middleName, birthday, icdEntity, comment);
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        entity.getBirthday().format(dateFormatter);
        if (entity.getIcd() != null)
            icd = new Icd(entity.getIcd());
    }

    static public Set<PatientRecord> getSetAll() throws DomainException {
        try {
            Set<PatientRecordEntity> entitys = PatientRecordDAO.getInstance().getAll();
            Set<PatientRecord> result = new TreeSet<>();
            entitys.forEach(o -> result.add(new PatientRecord(o)));
            return result;
        } catch (DAOException e) {
            e.printStackTrace();
            throw new DomainException("dao getAll patientRecords error");
        }
    }

    public void insert() throws DomainException {
        try {
            PatientRecordDAO.getInstance().insert(entity);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new DomainException("dao insert patientRecord error");
        }
    }

    public void update() throws DomainException {
        try {
            PatientRecordDAO.getInstance().update(entity);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new DomainException("dao update patientRecord error");
        }
    }

    public void delete() throws DomainException {
        try {
            PatientRecordDAO.getInstance().delete(entity);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new DomainException("dao delete patientRecord error");
        }
    }

    public PatientRecordEntity getEntity() {
        return entity;
    }

    public int getId() {
        return entity.getId();
    }

    public String getSecondName() {
        return entity.getSecondName();
    }

    public String getFirstName() {
        return entity.getFirstName();
    }

    public String getMiddleName() {
        return entity.getMiddleName();
    }

    public LocalDate getBirthday() {
        return entity.getBirthday();
    }

    public Icd getIcd() {
        return icd;
    }

    public void setIcd(Icd icd) {
        if (icd != null)
            entity.setIcd(icd.getEntity());
        else
            entity.setIcd(null);

        this.icd = icd;
    }

    public String getComment() {
        return entity.getComment();
    }

    public void setComment(String comment) {
        entity.setComment(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientRecord that = (PatientRecord) o;
        return entity.equals(that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }

    @Override
    public int compareTo(PatientRecord o) {
        return entity.compareTo(o.entity);
    }
}