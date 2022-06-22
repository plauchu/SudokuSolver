/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Plauchu
 */
public class DTable extends JTable {

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columIndex) {
        Component component = super.prepareRenderer(renderer, rowIndex, columIndex);
        component.setBackground(Color.WHITE);
        component.setForeground(Color.BLUE);
        if (getValueAt(rowIndex, columIndex) != null) {
            Integer val = Integer.parseInt(getValueAt(rowIndex, columIndex).toString());
            if (val == -1) {
                component.setBackground(Color.BLACK);
                component.setForeground(Color.black);
            }
        }
        return component;
    }

}
