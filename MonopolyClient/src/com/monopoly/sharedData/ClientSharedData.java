package com.monopoly.sharedData;

/*
 signelton for clientPlayerName use
 */
public class ClientSharedData {
    private String clientPlayerName;
    private static ClientSharedData instance;
    
    private ClientSharedData(){}
    
    public static ClientSharedData getInstance()
    {
        if(instance == null)
        {
            instance = new ClientSharedData();
        }
        return instance;
    }
    
    public String getClientPlayerName()
    {
        return clientPlayerName;
    }
    
    public void setClientPlayerName(String playerName)
    {
        this.clientPlayerName = playerName;
    }   
    
}
