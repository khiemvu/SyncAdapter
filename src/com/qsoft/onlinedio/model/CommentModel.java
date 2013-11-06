package com.qsoft.onlinedio.model;

/**
 * User: khiemvx
 * Date: 10/18/13
 */
public class CommentModel
{
    private int imageID;
    private String person;
    private String comment;
    private String time;

    public CommentModel(int imageID, String person, String comment, String time)
    {
        this.imageID = imageID;
        this.person = person;
        this.comment = comment;
        this.time = time;
    }

    public int getImageID()
    {
        return imageID;
    }

    public void setImageID(int imageID)
    {
        this.imageID = imageID;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getPerson()
    {
        return person;
    }

    public void setPerson(String person)
    {
        this.person = person;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}
