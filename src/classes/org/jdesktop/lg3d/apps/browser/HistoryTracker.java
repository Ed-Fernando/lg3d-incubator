/*
 * Created on Aug 17, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser;

import java.util.ArrayList;

/**
 * Used to track the browser history
 * 
 * @author demers
 */
public class HistoryTracker implements Browser3DUIAdapter
{
  
  private int currentPosition = -1;

  private ArrayList<HistoryItem> list = new ArrayList<HistoryItem>();
  
  private Browser3D browser;
  
  public HistoryTracker( Browser3D browser )
  {
    this.browser = browser;
  }// end 

  public boolean hasBack()
  {
    return currentPosition > 0;
  }// end hasBack
  
  public boolean hasForward()
  {
    return currentPosition+1 < list.size();
  }// end hasForward
  
  public void newPage( HistoryItem newItem )
  {
    // remove everything after the current possition
    int numToRemove = list.size()-currentPosition-1;
    for( int ii = 0; ii<numToRemove; ii++ )
    {
      list.remove( this.currentPosition+1 );
    }// end for
    
    HistoryItem item = new HistoryItem( newItem.getUrl(), newItem.getTitle() );
    list.add( item );
    
    currentPosition++;
    
  }// end newPage
  
  
  public HistoryItem getForward()
  {
    HistoryItem item = (HistoryItem)list.get( currentPosition +1 );
    return item;    
  }// end getForward
  
  public HistoryItem getBack()
  {
    HistoryItem item = (HistoryItem)list.get( currentPosition -1 );
    return item;    
  }// end getBack
  
  public HistoryItem moveForward()
  {
    HistoryItem item = this.getForward();
    currentPosition++;
    return item;
  }
  
  public HistoryItem moveBack()
  {
    HistoryItem item = this.getBack();
    currentPosition--;
    return item;
  }
  
  public void printList()
  {
    for( int ii=0; ii<this.list.size(); ii++)
    {
      String selected = "";
      if( ii == currentPosition )
      {
        selected = " *";
      }// end if
      
    }// end for
  }// end printList
  
  
  public void urlChanged(String url)
  {
    HistoryItem newItem = new HistoryItem( url, browser.getTitle() );
    
    boolean newUrl = true;
//  check if there is a previous item
    if( this.currentPosition >= 0 )
    {
      HistoryItem currentItem = (HistoryItem)this.list.get( currentPosition );
      if( currentItem.getUrl().equals( url ) || hasBack() && getBack().getUrl().equals( url ) )
      {
        newUrl = false;
      }// end if
    }// end if

    if( newUrl )
      this.newPage( newItem );
    
  }// end urlChanged

  public void titleChanged(String title)
  {}

  public void statusChanged(String status)
  {}
  
  
  public static void main( String[] args )
  {
    
    HistoryTracker tracker = new HistoryTracker( null );
    
    tracker.newPage( new HistoryItem( "one",   "one"  ) );
    tracker.newPage( new HistoryItem( "two",   "two"  ) );
//    tracker.newPage( new HistoryItem( "three", "three") );
//    tracker.newPage( new HistoryItem( "four",  "four" ) );
    
    if( tracker.hasBack() )
    {
      tracker.moveBack();
    }// end if
//    if( tracker.hasBack() )
//    {
//      tracker.getBack();
//    }// end if
    
//    tracker.newPage( new HistoryItem( "five",  "five") );
    
    if( tracker.hasForward() )
    {
      tracker.moveForward();
    }// end if
    
    tracker.printList();
    
  }// end main
  
}
