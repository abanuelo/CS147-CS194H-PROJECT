package com.bignerdranch.android.pife11;

public class SupportData {
    public String getDatabaseName() {
        return "pife1-0";
    }
    public String getApiKey() {
        return "VaWDh7evy8bwKyvnZYFnkrcdjlbg30En";
    }
    public String getBaseUrl()
    {
        return "https://api.mlab.com/api/1/databases/"+getDatabaseName()+"/collections/";
    }
    public String apiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }
    public String collectionName()
    {
        return "userlogin";
    }
    public String buildContactsSaveURL()
    {
        return getBaseUrl()+collectionName()+apiKeyUrl();
    }
    public String buildContactsFetchURL()
    {
        return getBaseUrl()+collectionName()+apiKeyUrl();
    }
    public String createContact(UserInfo contact) {
        return String.format("{\"name\": \"%s\", "+ "\"username\": \"%s\", " + "\"email\": \"%s\", " + "\"password\": \"%s\"}", contact.getName(), contact.getUsername(), contact.getEmail(), contact.getPassword());
    }
}
