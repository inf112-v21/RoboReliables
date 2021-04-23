package inf112.skeleton.app.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Iterator;

public class Message {
    private final String text;
    private int yPos;
    private final Label label;

    public Message(String text, int yPos) {
        this.text = text;
        this.yPos = yPos;
        this.label = createLabel(text);
        label.setPosition(860, yPos);
    }

    public Label createLabel(String text) {
        BitmapFont font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        return new Label(text, style);
    }

    public void setyPos(int newyPos) {
        yPos = newyPos;
        label.setPosition(860, yPos);
    }

    public String getText() {
        return text;
    }

    public int getyPos() {
        return yPos;
    }


    public Label getLabel() {
        return label;
    }

}
