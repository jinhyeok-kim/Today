package com.example.jinhyeok.whattoday;

public class DBManager
{
    private static DBManager s_Instance;

    public static DBManager getInstance()
    {
        if(s_Instance == null)
            s_Instance = new DBManager();

        return s_Instance;
    }

    private DBManager()
    {

    }
}
