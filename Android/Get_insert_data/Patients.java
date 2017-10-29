package com.example.korn.parser;

/**
 * Created by KoRn on 22-10-2017.
 */

public class Patients
{
    private String cpr, name, surname, age, type, phone, address, email;

    public Patients(String cpr, String name, String surname, String age, String type, String phone, String address, String email)
    {
        this.setCpr(cpr);
        this.setName(name);
        this.setSurname(surname);
        this.setAge(age);
        this.setType(type);
        this.setPhone(phone);
        this.setAddress(address);
        this.setEmail(email);
    }

    public String getCpr()
    {
        return cpr;
    }

    public void setCpr(String cpr)
    {
        this.cpr = cpr;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getType()
    {
        return type;
    }

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
    }
}



