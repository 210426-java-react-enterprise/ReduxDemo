package com.revature.dtos;

public class CoordinatesPair<T,E>
{
    private T latitude;
    private E longitude;

    public CoordinatesPair(T latitude, E longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public T getLatitude()
    {
        return latitude;
    }

    public void setLatitude(T latitude)
    {
        this.latitude = latitude;
    }

    public E getLongitude()
    {
        return longitude;
    }

    public void setLongitude(E longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "latitude = " + latitude +
                       ", longitude = " + longitude;
    }
}
