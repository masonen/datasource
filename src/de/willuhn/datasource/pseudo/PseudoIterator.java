/**********************************************************************
 * $Source: /cvsroot/jameica/datasource/src/de/willuhn/datasource/pseudo/PseudoIterator.java,v $
 * $Revision: 1.5 $
 * $Date: 2004/11/05 19:48:24 $
 * $Author: willuhn $
 * $Locker:  $
 * $State: Exp $
 *
 * Copyright (c) by willuhn.webdesign
 * All rights reserved
 *
 **********************************************************************/
package de.willuhn.datasource.pseudo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.List;

import de.willuhn.datasource.GenericIterator;
import de.willuhn.datasource.GenericObject;

/**
 * Ein Pseudo-Iterator, der zwar das GenericIterator-Interface
 * implementiert, jedoch kein Datenbank-Backend benutzt sondern
 * Listen/Maps aus java.util. 
 */
public class PseudoIterator extends UnicastRemoteObject implements GenericIterator
{

	private List list = null;
	private int index = 0;

	/**
   * Der Konstruktor ist private, damit Instanzen nur
   * ueber die statischen Methoden fromFoo erzeugt werden.
   */
  private PseudoIterator() throws RemoteException
	{
	}

  /**
	 * Erzeugt einen GenericIterator aus einem Array von GenericObjects.
   * @param objects das Array, aus dem der Iterator aufgebaut werden soll.
   * @return der generierte Iterator.
   * @throws RemoteException
   */
  public static GenericIterator fromArray(GenericObject[] objects) throws RemoteException
	{
		PseudoIterator i = new PseudoIterator();
		i.list = Arrays.asList(objects);
		return i;
	}

  /**
   * @see de.willuhn.datasource.GenericIterator#hasNext()
   */
  public boolean hasNext() throws RemoteException
  {
    return (list.size() > index && list.size() > 0);
  }

  /**
   * @see de.willuhn.datasource.GenericIterator#next()
   */
  public GenericObject next() throws RemoteException
  {
    return (GenericObject) list.get(index++);
  }

  /**
   * @see de.willuhn.datasource.GenericIterator#previous()
   */
  public GenericObject previous() throws RemoteException
  {
    return (GenericObject) list.get(index--);
  }

  /**
   * @see de.willuhn.datasource.GenericIterator#begin()
   */
  public void begin() throws RemoteException
  {
		index = 0;
  }

  /**
   * @see de.willuhn.datasource.GenericIterator#size()
   */
  public int size() throws RemoteException
  {
    return list.size();
  }

  /**
   * @see de.willuhn.datasource.GenericIterator#contains(de.willuhn.datasource.GenericObject)
   */
  public GenericObject contains(GenericObject o) throws RemoteException
  {
		if (o == null)
			return null;

		GenericObject object = null;
		for (int i=0;i<list.size();++i)
		{
			object = (GenericObject) list.get(i);
			if (object.equals(o))
				return object;
		}
    
		return null;
  }

}


/**********************************************************************
 * $Log: PseudoIterator.java,v $
 * Revision 1.5  2004/11/05 19:48:24  willuhn
 * *** empty log message ***
 *
 * Revision 1.4  2004/08/30 15:02:47  willuhn
 * *** empty log message ***
 *
 * Revision 1.3  2004/08/18 23:14:00  willuhn
 * @D Javadoc
 *
 * Revision 1.2  2004/07/21 23:53:56  willuhn
 * @C massive Refactoring ;)
 *
 * Revision 1.1  2004/06/17 22:06:29  willuhn
 * @N PseudoIterator
 *
 **********************************************************************/