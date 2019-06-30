package ru.turlyunef.focusstart.turlyun.client.view;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class FieldFocusAdapter extends FocusAdapter {
    private final JTextComponent component;
    private final String hintString;

    FieldFocusAdapter(JTextComponent component, String hintString) {
        this.component = component;
        this.hintString = hintString;
    }

    @Override
    public void focusGained(FocusEvent e) {
        Color color = component.getCaretColor();
        component.setForeground(color);
        if (component.getText().equals(hintString)) {
            component.setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (component.getText().isEmpty()) {
            component.setForeground(Color.LIGHT_GRAY);
            component.setText(hintString);
        }
    }
}