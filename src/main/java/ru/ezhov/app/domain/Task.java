package ru.ezhov.app.domain;

import java.awt.Color;
import java.sql.Date;
import java.sql.Timestamp;
import javax.swing.JLabel;

/**
 * @author Денис
 */
public class Task {

    private int id;
    private Date dateTask;
    private String textTask;
    private boolean closeNotClose;
    private int who;
    private Timestamp dateInsert;
    private String color;

    public Task(
            int id,
            Date dateTask,
            String textTask,
            boolean closeNotClose,
            int who,
            Timestamp dateInsert,
            String color) {
        this.id = id;
        this.dateTask = dateTask;
        this.textTask = textTask;
        this.closeNotClose = closeNotClose;
        this.dateInsert = dateInsert;
        this.who = who;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public Date getDateTask() {
        return dateTask;
    }

    public String getTextTask() {
        return textTask;
    }

    public boolean isCloseNotClose() {
        return closeNotClose;
    }

    public Timestamp getDateInsert() {
        return dateInsert;
    }

    public int getWho() {
        return who;
    }

    @Override
    public String toString() {
        return textTask;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateTask(Date dateTask) {
        this.dateTask = dateTask;
    }

    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }

    public void setCloseNotClose(boolean closeNotClose) {
        this.closeNotClose = closeNotClose;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public void setDateInsert(Timestamp dateInsert) {
        this.dateInsert = dateInsert;
    }

    public Color getColor() {
        if (color == null) {
            return new JLabel().getBackground();
        }
        String[] mass = color.split(";");
        return new Color(
                Integer.valueOf(mass[0]),
                Integer.valueOf(mass[1]),
                Integer.valueOf(mass[2]));
    }

    public void setColor(Color color) {
        this.color =
                String.valueOf(
                        color.getRed()) + ";" +
                        String.valueOf(color.getGreen()) + ";" +
                        String.valueOf(color.getBlue());
    }
}
