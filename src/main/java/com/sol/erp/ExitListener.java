package com.sol.erp;

 import java.awt.event.WindowAdapter;
 import java.awt.event.WindowEvent;
 
 
 
 public class ExitListener
   extends WindowAdapter
 {
   public void windowClosing(WindowEvent paramWindowEvent)
   {
     System.exit(0);
   }
 }
