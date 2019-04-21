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
package org.apache.fineract.useradministration.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.fineract.infrastructure.core.domain.JdbcSupport;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.useradministration.data.AppUserData;
import org.apache.fineract.useradministration.data.RoleData;
import org.apache.fineract.useradministration.domain.AppUser;
import org.apache.fineract.useradministration.domain.AppUserClientMapping;
import org.apache.fineract.useradministration.domain.AppUserRepository;
import org.apache.fineract.useradministration.domain.Role;
import org.apache.fineract.useradministration.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class AppUserReadPlatformServiceImpl implements AppUserReadPlatformService {

    private final JdbcTemplate jdbcTemplate;
    private final PlatformSecurityContext context;
    private final RoleReadPlatformService roleReadPlatformService;
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserReadPlatformServiceImpl(final PlatformSecurityContext context, final RoutingDataSource dataSource,
              final RoleReadPlatformService roleReadPlatformService,
            final AppUserRepository appUserRepository  ) {
        this.context = context;
        this.roleReadPlatformService = roleReadPlatformService;
        this.appUserRepository = appUserRepository;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
     * used for caching in spring expression language.
     */
    public PlatformSecurityContext getContext() {
        return this.context;
    }

    @Override
    @Cacheable(value = "users", key = "T(org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil).getTenant().getTenantIdentifier().concat(#root.target.context.authenticatedUser().getOffice().getHierarchy())")
    public Collection<AppUserData> retrieveAllUsers() {

        final AppUser currentUser = this.context.authenticatedUser();
        final String hierarchy = ".";
        final String hierarchySearchString = hierarchy + "%";

return null;
    }

    @Override
    public Collection<AppUserData> retrieveSearchTemplate() {
        final AppUser currentUser = this.context.authenticatedUser();
        final String hierarchy = ".";
        final String hierarchySearchString = hierarchy + "%";

        final AppUserLookupMapper mapper = new AppUserLookupMapper();
        final String sql = "select " + mapper.schema();

        return this.jdbcTemplate.query(sql, mapper, new Object[] { hierarchySearchString });
    }

    @Override
    public AppUserData retrieveNewUserDetails() {

        
      return null;
    }

    @Override
    public AppUserData retrieveUser(final Long userId) {

        this.context.authenticatedUser();

        final AppUser user = this.appUserRepository.findOne(userId);
        if (user == null || user.isDeleted()) { throw new UserNotFoundException(userId); }

        final Collection<RoleData> availableRoles = this.roleReadPlatformService.retrieveAll();

        final Collection<RoleData> selectedUserRoles = new ArrayList<>();
        final Set<Role> userRoles = user.getRoles();
        for (final Role role : userRoles) {
            selectedUserRoles.add(role.toData());
        }

        availableRoles.removeAll(selectedUserRoles);

        

        AppUserData retUser = AppUserData.instance(user.getId(), user.getUsername(), user.getEmail()
               , userId, user.getFirstname(), user.getLastname(), null, availableRoles, selectedUserRoles, 
                user.getPasswordNeverExpires(), user.isSelfServiceUser());
        
        if(retUser.isSelfServiceUser()){
        	for(AppUserClientMapping clientMap : user.getAppUserClientMappings()){
        		
        		
        	}
        }
        
        return retUser; 
    }

    private static final class AppUserMapper implements RowMapper<AppUserData> {

        private final RoleReadPlatformService roleReadPlatformService;

        public AppUserMapper(final RoleReadPlatformService roleReadPlatformService  ) {
            this.roleReadPlatformService = roleReadPlatformService;
        }

        @Override
        public AppUserData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {

            final Long id = rs.getLong("id");
            final String username = rs.getString("username");
            final String firstname = rs.getString("firstname");
            final String lastname = rs.getString("lastname");
            final String email = rs.getString("email");
            final Long officeId = JdbcSupport.getLong(rs, "officeId");
            final String officeName = rs.getString("officeName");
            final Long staffId = JdbcSupport.getLong(rs, "staffId");
            final Boolean passwordNeverExpire = rs.getBoolean("passwordNeverExpires");
            final Boolean isSelfServiceUser = rs.getBoolean("isSelfServiceUser");
            final Collection<RoleData> selectedRoles = this.roleReadPlatformService.retrieveAppUserRoles(id);

           
            return AppUserData.instance(id, username, email, officeId, officeName, firstname, lastname, null, selectedRoles,
                    passwordNeverExpire, isSelfServiceUser);
        }

        public String schema() {
            return " u.id as id, u.username as username, u.firstname as firstname, u.lastname as lastname, u.email as email, u.password_never_expires as passwordNeverExpires, "
                    + " u.office_id as officeId, o.name as officeName, u.staff_id as staffId, u.is_self_service_user as isSelfServiceUser from m_appuser u "
                    + " join m_office o on o.id = u.office_id where o.hierarchy like ? and u.is_deleted=0 order by u.username";
        }

    }

    private static final class AppUserLookupMapper implements RowMapper<AppUserData> {

        @Override
        public AppUserData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {

            final Long id = rs.getLong("id");
            final String username = rs.getString("username");

            return AppUserData.dropdown(id, username);
        }

        public String schema() {
            return " u.id as id, u.username as username from m_appuser u "
                    + " join m_office o on o.id = u.office_id where o.hierarchy like ? and u.is_deleted=0 order by u.username";
        }
    }

    @Override
    public boolean isUsernameExist(String username) {
        String sql = "select count(*) from m_appuser where username = ?";
        Object[] params = new Object[] { username };
        Integer count = this.jdbcTemplate.queryForObject(sql, params, Integer.class);
        if (count == 0) { return false; }
        return true;
    }
}