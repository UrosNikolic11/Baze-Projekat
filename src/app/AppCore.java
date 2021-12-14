package app;

import database.Database;
import database.DatabaseImplementation;
import database.MSSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.table.TableModel;
import observer.Implementations.PublisherImplementation;
import observer.Notification;
import observer.enumi.NotificationCode;
import queryBuilder.QueryBuilderImplementation;
import queryBuilder.Queryuilder;
import queryBuilder.kompajler.CompilerImplementation;
import queryBuilder.validator.ValidatorImplementation;
import resource.implementation.InformationResource;
import utils.Constants;

public class AppCore extends PublisherImplementation {
    private Database database;
    private Settings settings;
    private Queryuilder qb;
    private TableModel tableModel;

    public AppCore() {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MSSQLrepository(this.settings));
        this.qb = new QueryBuilderImplementation(new ValidatorImplementation(), new CompilerImplementation());
        tableModel = new TableModel();
    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImplementation.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImplementation.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImplementation.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        return settingsImplementation;
    }


    public void loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,ir));
    }

    public void readDataFromTable(String fromTable){

        tableModel.setRows(this.database.readDataFromTable(fromTable));

        //Zasto ova linija moze da ostane zakomentarisana?
        //this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }


    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Queryuilder getQb() {
        return qb;
    }

    public void setQb(Queryuilder qb) {
        this.qb = qb;
    }
}
