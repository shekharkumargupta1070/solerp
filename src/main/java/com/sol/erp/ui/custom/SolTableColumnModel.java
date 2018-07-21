package com.sol.erp.ui.custom;

import javax.swing.table.*;
import java.util.Vector;
import java.util.Enumeration;

public class SolTableColumnModel extends DefaultTableColumnModel
 {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Vector<TableColumn> allTableColumns = new Vector<TableColumn>();

   public SolTableColumnModel()
     {
     }

    public void setColumnVisible(TableColumn column, boolean visible)
     {
        if(!visible) {
            super.removeColumn(column);
        }
        else 
	    {
            int noVisibleColumns    = tableColumns.size();
            int noInvisibleColumns  = allTableColumns.size();
            int visibleIndex        = 0;
            
            for(int invisibleIndex = 0; invisibleIndex < noInvisibleColumns; ++invisibleIndex) {
                TableColumn visibleColumn   = (visibleIndex < noVisibleColumns ? (TableColumn)tableColumns.get(visibleIndex) : null);
                TableColumn testColumn      = (TableColumn)allTableColumns.get(invisibleIndex);
                
                if(testColumn == column) {
                    if(visibleColumn != column) {
                        super.addColumn(column);
                        super.moveColumn(tableColumns.size() - 1, visibleIndex);
                    }
                    return; // ####################
                }
                if(testColumn == visibleColumn) {
                    ++visibleIndex;
                }
            }
        }
    }
    
    public void setAllColumnsVisible()
    {
        int noColumns       = allTableColumns.size();
        
        for(int columnIndex = 0; columnIndex < noColumns; ++columnIndex)
	 {
            TableColumn visibleColumn = (columnIndex < tableColumns.size() ? (TableColumn)tableColumns.get(columnIndex) : null);
            TableColumn invisibleColumn = (TableColumn)allTableColumns.get(columnIndex);
            
            if(visibleColumn != invisibleColumn)
		 {
                super.addColumn(invisibleColumn);
                super.moveColumn(tableColumns.size() - 1, columnIndex);
            }
        }
    }
    
    public TableColumn getColumnByModelIndex(int modelColumnIndex)
    {
        for (int columnIndex = 0; columnIndex < allTableColumns.size(); ++columnIndex)
	  {
            TableColumn column = (TableColumn)allTableColumns.elementAt(columnIndex);
            if(column.getModelIndex() == modelColumnIndex)
		 {
                return column;
             }
        }
        return null;
    }

    public boolean isColumnVisible(TableColumn aColumn)
	 {
        return (tableColumns.indexOf(aColumn) >= 0);
      }
    
    public void addColumn(TableColumn column)
    {
        allTableColumns.addElement(column);
        super.addColumn(column);
    }
    
    public void removeColumn(TableColumn column)
     {
        int allColumnsIndex = allTableColumns.indexOf(column);
        if(allColumnsIndex != -1)
        {
            allTableColumns.removeElementAt(allColumnsIndex);
        }
        super.removeColumn(column);
    }

    public void moveColumn(int oldIndex, int newIndex)
    {
	if ((oldIndex < 0) || (oldIndex >= getColumnCount()) ||
	    (newIndex < 0) || (newIndex >= getColumnCount()))
	    throw new IllegalArgumentException("moveColumn() - Index out of range");
        
        TableColumn fromColumn  = (TableColumn) tableColumns.get(oldIndex);
        TableColumn toColumn    = (TableColumn) tableColumns.get(newIndex);
        
        int allColumnsOldIndex  = allTableColumns.indexOf(fromColumn);
        int allColumnsNewIndex  = allTableColumns.indexOf(toColumn);

        if(oldIndex != newIndex)
        {
            allTableColumns.removeElementAt(allColumnsOldIndex);
            allTableColumns.insertElementAt(fromColumn, allColumnsNewIndex);
        }
        
        super.moveColumn(oldIndex, newIndex);
    }



    public int getColumnCount(boolean onlyVisible)
      {
        Vector<?> columns = (onlyVisible ? tableColumns : allTableColumns);
	  return columns.size();
      }

    public Enumeration<?> getColumns(boolean onlyVisible)
      {
        Vector<?> columns = (onlyVisible ? tableColumns : allTableColumns);
        
	  return columns.elements();
      }

    public int getColumnIndex(Object identifier, boolean onlyVisible)
      {
	if (identifier == null)
	   {
	    throw new IllegalArgumentException("Identifier is null");
	   }

        Vector<?>      columns     = (onlyVisible ? tableColumns : allTableColumns);
        int         noColumns   = columns.size();
        TableColumn column;
        
        for(int columnIndex = 0; columnIndex < noColumns; ++columnIndex)
          {
	      column = (TableColumn)columns.get(columnIndex);

            if(identifier.equals(column.getIdentifier()))
		return columnIndex;
          }
	throw new IllegalArgumentException("Identifier not found");
    }

    public TableColumn getColumn(int columnIndex, boolean onlyVisible)
     {
	return (TableColumn)tableColumns.elementAt(columnIndex);
     }
}
