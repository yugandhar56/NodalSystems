package com.nodel.nodalsytems.ui.util;

/**
 * Created by yugandhar on 12/30/2017.
 */

public interface Constants
{
    public static boolean IS_NEW_LOOK=false;
    public interface IFields
    {
        interface InputStyle////={"\0Default","\1Multi-line Edit box","\2Combo Box","\3Radio Button","\4Check Box"};
        {
            byte Default=0;
            byte Multi_line=1;
            byte Combo_Box=2;
            byte Radio_Button=3;
            byte Check_Box=4;
        }
        interface IDisplayStyle//"\0Normal","\1Read Only","\2Hidden","\3UnEditable","\4Value Mandatory",
        {
            byte Normal=0;
            byte ReadOnly=1;
            byte Hidden=2;
            byte UnEditable=3;
            byte ValueMandatory=4;
        }
    }

}
