package com.monopoly.utils;

import com.monopoly.controller.XmlMonopolyInitReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Utils
{
    private Utils()
    {
    }

    public static Integer tryParseInt(String num)
    {
        try
        {
            return Integer.parseUnsignedInt(num);
        } catch (NumberFormatException e)
        {
            return null;
        }
    }


}
