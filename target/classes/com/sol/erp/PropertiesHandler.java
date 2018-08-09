/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sol.erp;

import com.sol.erp.constants.ApplicationConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ravisharma
 */
public class PropertiesHandler {

    public static void writePropertiies(Properties prop) {
        try {
            
            FileOutputStream out = new FileOutputStream(ApplicationConstants.USER_HOME+File.separator+ApplicationConstants.CONFIG_FILE_NAME);
            prop.store(out, "");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Properties readProperties() {
        Properties prop = null;
        try {
            FileInputStream in = new FileInputStream(ApplicationConstants.USER_HOME+File.separator+ApplicationConstants.CONFIG_FILE_NAME);
            prop = new Properties();
            prop.load(in);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), "Could not found configuration file: "+ApplicationConstants.CONFIG_FILE_NAME);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesHandler.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return prop;
    }

}
