package ru.gsa.biointerface.domain.entity;

import java.io.Serializable;
import java.util.Objects;

public class GraphEntityId implements Serializable {
    private int numberOfChannel = -1;
    private ExaminationEntity examinationEntity;

    public GraphEntityId() {
    }

    public GraphEntityId(int numberOfChannel, ExaminationEntity examinationEntity) {
        this.numberOfChannel = numberOfChannel;
        this.examinationEntity = examinationEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphEntityId that = (GraphEntityId) o;
        return numberOfChannel == that.numberOfChannel && Objects.equals(examinationEntity, that.examinationEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfChannel, examinationEntity);
    }

    @Override
    public String toString() {
        return "GraphEntityId{" +
                "numberOfChannel=" + numberOfChannel +
                ", examinationEntity=" + examinationEntity +
                '}';
    }
}