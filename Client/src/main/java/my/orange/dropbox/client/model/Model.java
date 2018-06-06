package my.orange.dropbox.client.model;

import my.orange.dropbox.common.SavedFile;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Comparator;
import java.util.List;

public class Model implements TableModel {

    private List<SavedFile> files;

    public Model(List<SavedFile> files) {
        this.files = files;
        this.files.sort(Comparator.comparing(SavedFile::getName));
    }

    @Override
    public int getRowCount() {
        return files.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Name";
            case 1:
                return "Size";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SavedFile file = files.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return file.getName();
            case 1:
                return file.getSize();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public SavedFile getSavedFile(int row) {
        if (row == -1) return null;
        return files.get(row);
    }
}
