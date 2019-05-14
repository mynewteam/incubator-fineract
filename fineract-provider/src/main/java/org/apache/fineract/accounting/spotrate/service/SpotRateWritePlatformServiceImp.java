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
package org.apache.fineract.accounting.spotrate.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.accounting.spotrate.command.SpotRateCommand;
import org.apache.fineract.accounting.spotrate.domain.SpotRateRepository;
import org.apache.fineract.accounting.spotrate.domain.SpotRate;
import org.apache.fineract.accounting.spotrate.serialization.SpotRateSerialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpotRateWritePlatformServiceImp implements SpotRateWritePlatformService
{
	private final SpotRateSerialization fromApiJsonDeserializer ;
	private final SpotRateRepository spotrateRespository;
	
	@Autowired
	public SpotRateWritePlatformServiceImp(final SpotRateSerialization fromApiJsonDeserializer, final SpotRateRepository spotrateRespository)
	{
		this.fromApiJsonDeserializer = fromApiJsonDeserializer;
		this.spotrateRespository = spotrateRespository;
	}

	@Transactional
	@Override
	public CommandProcessingResult createSpotRate(final JsonCommand command)
	{
		try
		{
			final SpotRateCommand spotRateCommand = this.fromApiJsonDeserializer.commandFromApiJson(command.json());
			spotRateCommand.validateForCreate();
			final SpotRate spotrate = SpotRate.fromJson(command);

			this.spotrateRespository.save(spotrate);

			return new CommandProcessingResultBuilder().withCommandId(command.commandId())
					.withEntityId(spotrate.getId()).build();
		} catch (final DataIntegrityViolationException dve)
		{
			return CommandProcessingResult.empty();
		}
	}
}