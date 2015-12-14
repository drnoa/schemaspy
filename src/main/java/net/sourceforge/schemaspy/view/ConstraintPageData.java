package net.sourceforge.schemaspy.view;

import net.sourceforge.schemaspy.model.ForeignKeyConstraint;
import net.sourceforge.schemaspy.model.Table;

import java.util.List;

/**
 * This Class holds the model for the Contraintpage
 */
public class ConstraintPageData {
    private GlobalData globalData;
    private List<ForeignKeyConstraint> constraints;
    private List<Table> tables;


    public GlobalData getGlobalData() {
        return globalData;
    }
    public void setGlobalData(GlobalData globalData) {
        this.globalData = globalData;
    }

    public void setConstraints(List<ForeignKeyConstraint> constraints) {
        this.constraints = constraints;
    }

    public List<ForeignKeyConstraint> getConstraints() {
        return constraints;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public boolean isCheckConstraints(){
        for (Table table : tables) {
            if(table.getCheckConstraints() != null && !table.getCheckConstraints().isEmpty()){
                return true;
            }
        }
        return false;
    }
}
