package com.oceana.chat.client;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.TableCellRenderer;

public class EmoticonRender implements TableCellRenderer{

	public EmoticonRender(){
        super();
    }
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JTextPane editor = new JTextPane();
		editor.setContentType("text/html");
	    if (value != null)
	      editor.setText("<HTML>" + value.toString());
	    return editor;
//		JTextField editor = new JTextField();
//	    if (value != null)
//	      editor.setText(value.toString());
//	    editor.setBackground((row % 2 == 0) ? Color.white : Color.cyan);
//	    return editor;
	}

}
