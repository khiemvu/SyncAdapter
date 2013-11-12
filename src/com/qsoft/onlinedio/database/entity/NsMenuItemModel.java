package com.qsoft.onlinedio.database.entity;

public class NsMenuItemModel
{

    public int title;
    public int iconRes;
    public boolean isButton;
    public boolean isHeader;

    public NsMenuItemModel(int title, int iconRes, boolean header, boolean isButton)
    {
        this.title = title;
        this.iconRes = iconRes;
        this.isHeader = header;
        this.isButton = isButton;
    }

    public NsMenuItemModel(int title, int iconRes, boolean header)
    {
        this(title, iconRes, header, false);
    }

    public NsMenuItemModel(int title, int iconRes)
    {
        this(title, iconRes, false);
    }


}
