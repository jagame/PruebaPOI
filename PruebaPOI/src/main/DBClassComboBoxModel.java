/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author jagam
 */
public class DBClassComboBoxModel implements ComboBoxModel<DBClass>{

    private final static DBClass[] valores = DBClass.values();
    private Object selection;
    private ListDataListener ldl;
    
    
    public DBClassComboBoxModel(){
        selection = valores[0];
    }
    
    @Override
    public void setSelectedItem(Object anItem) {
        selection = anItem;
        ListDataEvent evt = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, valores.length);
        ldl.contentsChanged( evt );
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public int getSize() {
        return valores.length;
    }

    @Override
    public DBClass getElementAt(int index) {
        return valores[index];
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        ldl = l;
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        ldl = null;
    }
    
}
