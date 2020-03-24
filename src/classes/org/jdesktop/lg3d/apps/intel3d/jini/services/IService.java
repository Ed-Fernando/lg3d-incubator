/*
 * IService.java
 *
 * Copyright (c) 2003 Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 * 
 * Created on November 21, 2003, 11:56 AM
 */

package org.jdesktop.lg3d.apps.intel3d.jini.services;

import java.rmi.RemoteException;

/** All the jini services to be recognized by the jws should
 * implement IService. The client will only look for IService
 * interface to get the jini services in its ServiceTemplate
 * @author asrivast
 * @since 11/21/2003
 * @version 1.0
 */
public interface IService {
    /** The method should be implemented by all the jini services to be
     * recognized by the JWS.
     * @param String operand The string which the service has to operate
     *        upon. This could be as simple as IDs or a file location.
     * @throws RemoteException throws RemoteException
     */    
    public void doService(String operand) throws RemoteException;
    
    /** The method should be implemented by all jini service implementors.
     * The method should a small service description.
     * @return String The service description
     */
    public String getDescription () throws RemoteException;
}
