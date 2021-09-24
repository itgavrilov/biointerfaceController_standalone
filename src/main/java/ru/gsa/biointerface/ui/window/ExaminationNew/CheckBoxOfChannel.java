package ru.gsa.biointerface.ui.window.ExaminationNew;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;

import java.util.Objects;

public class CheckBoxOfChannel extends CheckBox implements Comparable<CheckBoxOfChannel> {
    private final char index;

    public CheckBoxOfChannel(char index) {
        this.index = index;

        this.setSelected(true);
        this.setPadding(new Insets(0, 0, 3, 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckBoxOfChannel that = (CheckBoxOfChannel) o;
        return index + 1 == that.index + 1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index + 1);
    }

    @Override
    public int compareTo(CheckBoxOfChannel o) {
        return (index + 1) - (o.index + 1);
    }
}