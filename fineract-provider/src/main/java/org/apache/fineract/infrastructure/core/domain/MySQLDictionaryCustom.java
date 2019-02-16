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
package org.apache.fineract.infrastructure.core.domain;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.openjpa.jdbc.sql.BooleanRepresentationFactory;
import org.apache.openjpa.jdbc.sql.MySQLDictionary;
import org.apache.openjpa.jdbc.sql.OracleDictionary;

public class MySQLDictionaryCustom extends OracleDictionary {

	public MySQLDictionaryCustom() {
		super();
		this.supportsSubselect = true;
		this.booleanRepresentation = BooleanRepresentationFactory.INT_10;
		this.supportsGetGeneratedKeys = true;
		this.allowsAliasInBulkClause = true;
		this.useWildCardForCount = true;
		this.maxColumnNameLength=128;
		this.maxTableNameLength=128;
	}

	@Override
	public void connectedConfiguration(Connection conn) throws SQLException {
		super.connectedConfiguration(conn);
		this.supportsSubselect = true;
		this.supportsGetGeneratedKeys = true;
		this.allowsAliasInBulkClause = true;
		this.useWildCardForCount = true;
		this.maxColumnNameLength = 128;
		this.maxTableNameLength=128;
	}
}
