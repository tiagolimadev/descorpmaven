/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifpe.tads.descorp.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Eduardo
 */
public class JpaUtil {
    
    private static EntityManagerFactory EMF;
    
    private JpaUtil(){
        
    }
    
    public static EntityManagerFactory getInstance(){
        if (EMF == null) {
            EMF = Persistence.createEntityManagerFactory("descorp");
        }
        
        return EMF;
    }
    
}
