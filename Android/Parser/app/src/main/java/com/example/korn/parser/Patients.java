package com.example.korn.parser;

/**
 * Created by KoRn on 22-10-2017.
 */

public class Patients
{
    private String ID, fk_CPR, Measured_Level, Date;

    public Patients(String ID, String fk_CPR, String Measured_Level, String Date)
    {
        this.setID(ID);
        this.setfk_CPR(fk_CPR);
        this.setMeasured_Level(Measured_Level);
        this.setDate(Date);
       /* this.setType(type);
        this.setPhone(phone);
        this.setAddress(address);
        this.setEmail(email);*/
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getfk_CPR()
    {
        return fk_CPR;
    }

    public void setfk_CPR(String fk_CPR)
    {
        this.fk_CPR = fk_CPR;
    }

    public String getMeasured_Level()
    {
        return Measured_Level;
    }

    public void setMeasured_Level(String Measured_Level)
    {
        this.Measured_Level = Measured_Level;
    }

    public String getDate()
    {
        return Date;
    }

    public void setDate(String Date)
    {
        this.Date = Date;
    }

  /*  public String getType()


    public void setType(String type)
    {
        this.type = type;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }*/
}
