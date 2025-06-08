package com.group_9.project.utils;

import javax.swing.text.*;

public class LengthLimitFilter extends DocumentFilter {
    private final int maxLength;

    public LengthLimitFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        if ((fb.getDocument().getLength() + string.length()) <= maxLength) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
            throws BadLocationException {
        if (string == null) return;
        int currentLength = fb.getDocument().getLength();
        int newLength = currentLength - length + string.length();
        if (newLength <= maxLength) {
            super.replace(fb, offset, length, string, attr);
        }
    }
}
