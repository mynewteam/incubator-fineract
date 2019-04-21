/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.fineract.useradministration.data;

import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * Immutable data object for application user data.
 */
public class AppUserData {

    private final Long id;
    private final String username;
    private final Long officeId;
    private final String officeName;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final Boolean passwordNeverExpires;

    //import fields
    private List<Long> roles;
    private Boolean sendPasswordToEmail;
    private Long staffId;
    private transient Integer rowIndex;

    @SuppressWarnings("unused")
    private final Collection<RoleData> availableRoles;
    private final Collection<RoleData> selectedRoles;
    private final Boolean isSelfServiceUser;
    

	public static AppUserData importInstance(Long officeId,Long staffId,String userName, String firstName, String lastName,
            String email,Boolean sendPasswordToEmail,Boolean passwordNeverExpires, List<Long> roleIds,
            Integer rowIndex){
	    return new AppUserData(officeId,staffId,userName,firstName,lastName,email,
                sendPasswordToEmail,passwordNeverExpires,roleIds,rowIndex);
    }
    private AppUserData(Long officeId,Long staffId,String username, String firstname, String lastname,
            String email,Boolean sendPasswordToEmail,Boolean passwordNeverExpires, List<Long> roleIds,
            Integer rowIndex) {
        this.id = null;
        this.username = username;
        this.officeId = officeId;
        this.officeName = null;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.passwordNeverExpires = passwordNeverExpires;
        this.roles = roleIds;
        this.sendPasswordToEmail = sendPasswordToEmail;
        this.staffId = staffId;
        this.rowIndex = rowIndex;
        this.availableRoles = null;
        this.selectedRoles = null;
        this.isSelfServiceUser = null;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public static AppUserData template(final AppUserData user) {
        return new AppUserData(user.id, user.username, user.email, user.officeId, user.officeName, user.firstname, user.lastname,
                user.availableRoles, user.selectedRoles, user.passwordNeverExpires, user.isSelfServiceUser);
    }

    public static AppUserData template( final Collection<RoleData> availableRoles) {
        return new AppUserData(null, null, null, null, null, null, null, availableRoles, null, null, null);
    }

    public static AppUserData dropdown(final Long id, final String username) {
        return new AppUserData(id, username, null, null, null, null, null, null, null, null, null);
    }

    public static AppUserData instance(final Long id, final String username, final String email, final Long officeId,
            final String officeName, final String firstname, final String lastname, final Collection<RoleData> availableRoles,
            final Collection<RoleData> selectedRoles, final Boolean passwordNeverExpire, final Boolean isSelfServiceUser) {
        return new AppUserData(id, username, email, officeId, officeName, firstname, lastname, availableRoles, selectedRoles,
                passwordNeverExpire, isSelfServiceUser);
    }

    private AppUserData(final Long id, final String username, final String email, final Long officeId, final String officeName,
            final String firstname, final String lastname, final Collection<RoleData> availableRoles,
            final Collection<RoleData> selectedRoles,
            final Boolean passwordNeverExpire, final Boolean isSelfServiceUser) {
        this.id = id;
        this.username = username;
        this.officeId = officeId;
        this.officeName = officeName;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.availableRoles = availableRoles;
        this.selectedRoles = selectedRoles;
        this.passwordNeverExpires = passwordNeverExpire;
        this.isSelfServiceUser = isSelfServiceUser;
    }

    public boolean hasIdentifyOf(final Long createdById) {
        return this.id.equals(createdById);
    }

    public String username() {
        return this.username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUserData that = (AppUserData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    
    public boolean isSelfServiceUser() {
		return this.isSelfServiceUser==null?false:this.isSelfServiceUser;
	}

}