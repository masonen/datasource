/**********************************************************************
 * $Source: /cvsroot/jameica/datasource/src/de/willuhn/datasource/db/types/TypeByteArray.java,v $
 * $Revision: 1.2 $
 * $Date: 2008/07/11 16:15:54 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn software & services
 * All rights reserved
 *
 **********************************************************************/

package de.willuhn.datasource.db.types;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Typ Byte-Array.
 */
public class TypeByteArray extends TypeGeneric
{
  
  /**
   * @see de.willuhn.datasource.db.types.TypeGeneric#get(java.sql.ResultSet, java.lang.String)
   */
  public Object get(ResultSet rs, String name) throws SQLException
  {
    Object value = super.get(rs, name);
    if (value != null && (value instanceof InputStream))
    {
      // Wenn es ein Stream ist, kopieren wir die Daten in ein
      // Byte-Array
      InputStream is = (InputStream) value;
      try
      {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = is.read(buf)) != -1)
          bos.write(buf,0,len);
        
        return bos.toByteArray();
      }
      catch (IOException ioe)
      {
        throw new SQLException("unable to read blob: " + ioe.getMessage());
      }
      finally
      {
        try
        {
          is.close();
        }
        catch (IOException e)
        {
          throw new SQLException("unable to close inputstream: " + e.getMessage());
        }
      }
    }
    return value;
  }

  /**
   * @see de.willuhn.datasource.db.types.TypeGeneric#set(java.sql.PreparedStatement, int, java.lang.Object)
   */
  public void set(PreparedStatement stmt, int index, Object value) throws SQLException
  {
    if (value == null)
      stmt.setNull(index,Types.NULL);
    else
      stmt.setBytes(index,(byte[])value);
  }
}


/*********************************************************************
 * $Log: TypeByteArray.java,v $
 * Revision 1.2  2008/07/11 16:15:54  willuhn
 * @B rs.getObject() liefert ggf. (abhaengig von der Datenbank) einen InputStream statt byte[]
 *
 * Revision 1.1  2008/07/11 09:30:17  willuhn
 * @N Support fuer Byte-Arrays
 * @N SQL-Typen sind jetzt erweiterbar
 *
 **********************************************************************/