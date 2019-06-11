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
package org.apache.fineract.accounting.exchange.service;

import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResultBuilder;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.office.domain.OfficeRepositoryWrapper;
import org.apache.fineract.organisation.office.domain.OrganisationCurrencyRepositoryWrapper;
import org.apache.fineract.useradministration.domain.AppUser;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.fineract.accounting.exchange.api.ExchangeJsonInputParams;
import org.apache.fineract.accounting.exchange.command.ExchangeCommand;
import org.apache.fineract.accounting.exchange.serialization.ExchangeSerialization;
import org.apache.fineract.accounting.glaccount.domain.GLAccount;
import org.apache.fineract.accounting.glaccount.domain.GLAccountRepository;
import org.apache.fineract.accounting.glaccount.service.GLAccountReadPlatformService;
import org.apache.fineract.accounting.journalentry.domain.JournalEntry;
import org.apache.fineract.accounting.journalentry.domain.JournalEntryRepository;
import org.apache.fineract.accounting.journalentry.domain.JournalEntryType;
import org.apache.fineract.accounting.journalentry.exception.JournalEntryInvalidException;
import org.apache.fineract.accounting.journalentry.exception.JournalEntryInvalidException.GL_JOURNAL_ENTRY_INVALID_REASON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ExchangeWritePlatformServiceImp implements ExchangeWritePlatformService
{
	private final ExchangeSerialization fromApiJsonDeserializer ;
	private final GLAccountRepository glAccountRepository;
	private final OfficeRepositoryWrapper officeRepositoryWrapper;
	private final GLAccountReadPlatformService glAccountReadPlatformService;
    private final JournalEntryRepository glJournalEntryRepository;
    private final PlatformSecurityContext context;
    private final OrganisationCurrencyRepositoryWrapper organisationCurrencyRepository;
	
	@Autowired
	public ExchangeWritePlatformServiceImp(final ExchangeSerialization fromApiJsonDeserializer,
			final JournalEntryRepository glJournalEntryRepository, final OrganisationCurrencyRepositoryWrapper organisationCurrencyRepository,
			final OfficeRepositoryWrapper officeRepositoryWrapper, final GLAccountReadPlatformService glAccountReadPlatformService,
			final PlatformSecurityContext context, final GLAccountRepository glAccountRepository)
	{
		this.fromApiJsonDeserializer = fromApiJsonDeserializer;
		this.glJournalEntryRepository = glJournalEntryRepository;
		this.organisationCurrencyRepository = organisationCurrencyRepository;
		this.officeRepositoryWrapper = officeRepositoryWrapper;
		this.context = context;
		this.glAccountReadPlatformService = glAccountReadPlatformService;
		this.glAccountRepository = glAccountRepository;
	}

	@Override
	public CommandProcessingResult doExchange(final JsonCommand command)
	{
		try
		{
			final ExchangeCommand ExchangeCommand = this.fromApiJsonDeserializer.commandFromApiJson(command.json());
			ExchangeCommand.validateForCreate();
			
            String currencyCode = null;
            BigDecimal amount = null;
            GLAccount glAccount = null;
            String transactionId = null;
            String comments = "";
            
            // check office is valid
            final Long officeId = command.longValueOfParameterNamed(ExchangeJsonInputParams.OFFICE_ID.getValue());
            final Office office = this.officeRepositoryWrapper.findOneWithNotFoundDetection(officeId);
            
            final String buyingcurrencyCode = command.stringValueOfParameterNamed(ExchangeJsonInputParams.BUYCURRENCY_CODE.getValue());
            final String sellingcurrencyCode = command.stringValueOfParameterNamed(ExchangeJsonInputParams.SELLCURRENCY_CODE.getValue());
            final String spotrate = command.stringValueOfParameterNamed(ExchangeJsonInputParams.SPOTRATE.getValue());

            final BigDecimal buyingamount = command.bigDecimalValueOfParameterNamed(ExchangeJsonInputParams.BUYAMOUNT.getValue());
            final BigDecimal sellingamount = command.bigDecimalValueOfParameterNamed(ExchangeJsonInputParams.SELLAMOUNT.getValue());
            Long glAccountId = null;

            /** Set a transaction Id and save these Journal entries **/
            final Date transactionDate = command.DateValueOfParameterNamed(ExchangeJsonInputParams.TRANSACTION_DATE.getValue());
            
            
            if(buyingcurrencyCode == "KHR")
            {
            	/**
            	 * USD
            	 */
            	amount = sellingamount;
            	currencyCode = sellingcurrencyCode;
            	/** Validate current code is appropriate **/
                this.organisationCurrencyRepository.findOneWithNotFoundDetection(currencyCode);
                
            	//Debit
            	transactionId = generateTransactionId(officeId);
            	
            	glAccountId = glAccountReadPlatformService.findGLAccountbyGLCode("296602");
            	
            	glAccount = this.glAccountRepository.findOne(glAccountId);
            	comments = "Sell dollar: " + amount +", with spotrate: "+ spotrate;
            	validateGLAccountForTransaction(glAccount);
            	
            	saveAllDebitOrCreditEntries(ExchangeCommand, office, currencyCode, transactionDate,
                        glAccount, amount, transactionId, comments, JournalEntryType.DEBIT);
            	//Credit
            	glAccountId = glAccountReadPlatformService.findGLAccountbyGLCode("111102-1-0");
            	
            	glAccount = this.glAccountRepository.findOne(glAccountId);
            	comments = "Sell dollar: " + amount +", with spotrate: "+ spotrate;
            	validateGLAccountForTransaction(glAccount);
            	
            	saveAllDebitOrCreditEntries(ExchangeCommand, office, currencyCode, transactionDate,
                		glAccount, amount, transactionId, comments, JournalEntryType.CREDIT);
            	
            	/**
            	 * KHR
            	 */
            	amount = buyingamount;
            	currencyCode = buyingcurrencyCode;
            	/** Validate current code is appropriate **/
                this.organisationCurrencyRepository.findOneWithNotFoundDetection(currencyCode);
                
            	//Debit
            	transactionId = generateTransactionId(officeId);
            	
            	glAccountId = glAccountReadPlatformService.findGLAccountbyGLCode("111101-1-0");
            	
            	glAccount = this.glAccountRepository.findOne(glAccountId);
            	comments = "Buy Riel: " + amount +", with spotrate: "+ spotrate;
            	validateGLAccountForTransaction(glAccount);
            	
            	saveAllDebitOrCreditEntries(ExchangeCommand, office, currencyCode, transactionDate,
                        glAccount, amount, transactionId, comments, JournalEntryType.DEBIT);
            	//Credit
            	glAccountId = glAccountReadPlatformService.findGLAccountbyGLCode("386101");
            	
            	glAccount = this.glAccountRepository.findOne(glAccountId);
            	comments = "Buy Riel: " + amount +", with spotrate: "+ spotrate;
            	validateGLAccountForTransaction(glAccount);
            	
            	saveAllDebitOrCreditEntries(ExchangeCommand, office, currencyCode, transactionDate,
                		glAccount, amount, transactionId, comments, JournalEntryType.CREDIT);
            }
            else
            {
            	/**
            	 * USD
            	 */
            	amount = buyingamount;
            	currencyCode = buyingcurrencyCode;
            	/** Validate current code is appropriate **/
                this.organisationCurrencyRepository.findOneWithNotFoundDetection(currencyCode);
                
            	//Debit
            	transactionId = generateTransactionId(officeId);
            	
            	glAccountId = glAccountReadPlatformService.findGLAccountbyGLCode("296602");
            	
            	glAccount = this.glAccountRepository.findOne(glAccountId);
            	comments = "Buy dollar: " + amount +", with spotrate: "+ spotrate;
            	validateGLAccountForTransaction(glAccount);
            	
            	saveAllDebitOrCreditEntries(ExchangeCommand, office, currencyCode, transactionDate,
                        glAccount, amount, transactionId, comments, JournalEntryType.CREDIT);
            	//Credit
            	glAccountId = glAccountReadPlatformService.findGLAccountbyGLCode("111102-1-0");
            	
            	glAccount = this.glAccountRepository.findOne(glAccountId);
            	comments = "Buy dollar: " + amount +", with spotrate: "+ spotrate;
            	validateGLAccountForTransaction(glAccount);
            	
            	saveAllDebitOrCreditEntries(ExchangeCommand, office, currencyCode, transactionDate,
                		glAccount, amount, transactionId, comments, JournalEntryType.DEBIT);
            	
            	/**
            	 * KHR
            	 */
            	amount = sellingamount;
            	currencyCode = sellingcurrencyCode;
            	
            	/** Validate current code is appropriate **/
                this.organisationCurrencyRepository.findOneWithNotFoundDetection(currencyCode);
                
            	//Debit
            	transactionId = generateTransactionId(officeId);
            	
            	glAccountId = glAccountReadPlatformService.findGLAccountbyGLCode("111101-1-0");
            	
            	glAccount = this.glAccountRepository.findOne(glAccountId);
            	comments = "Sell Reil: " +  amount + ", with spotrate: "+ spotrate;
            	validateGLAccountForTransaction(glAccount);
            	
            	saveAllDebitOrCreditEntries(ExchangeCommand, office, currencyCode, transactionDate,
                        glAccount, amount, transactionId, comments, JournalEntryType.CREDIT);
            	//Credit
            	glAccountId = glAccountReadPlatformService.findGLAccountbyGLCode("386101");
            	
            	glAccount = this.glAccountRepository.findOne(glAccountId);
            	comments = "Sell Reil: " +  amount + ", with spotrate: "+ spotrate;
            	validateGLAccountForTransaction(glAccount);
            	
            	saveAllDebitOrCreditEntries(ExchangeCommand, office, currencyCode, transactionDate,
                		glAccount, amount, transactionId, comments, JournalEntryType.DEBIT);
            }
            
            return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withOfficeId(officeId)
                    .withTransactionId(transactionId).build();
            
		} catch (final DataIntegrityViolationException dve)
		{
			return CommandProcessingResult.empty();
		}
	}
	
	private void saveAllDebitOrCreditEntries(final ExchangeCommand command, final Office office,
            final String currencyCode, final Date transactionDate, GLAccount glAccount, final BigDecimal amount, 
            final String transactionId, final String comments,final JournalEntryType type) {
        final boolean manualEntry = true;
        
        validateGLAccountForTransaction(glAccount);

        /** Validate current code is appropriate **/
        this.organisationCurrencyRepository.findOneWithNotFoundDetection(currencyCode);
        
        final JournalEntry glJournalEntry = JournalEntry.createNew(office, null, glAccount, currencyCode, transactionId,
                manualEntry, transactionDate, type, amount, comments, null, null, null,
                null, null, null, null);
        
        this.glJournalEntryRepository.saveAndFlush(glJournalEntry);
    }
	
	private void validateGLAccountForTransaction(final GLAccount creditOrDebitAccountHead) {
        /***
         * validate that the account allows manual adjustments and is not
         * disabled
         **/
        if (creditOrDebitAccountHead.isDisabled()) {
            throw new JournalEntryInvalidException(GL_JOURNAL_ENTRY_INVALID_REASON.GL_ACCOUNT_DISABLED, null,
                    creditOrDebitAccountHead.getName(), creditOrDebitAccountHead.getGlCode());
        } else if (!creditOrDebitAccountHead.isManualEntriesAllowed()) { throw new JournalEntryInvalidException(
                GL_JOURNAL_ENTRY_INVALID_REASON.GL_ACCOUNT_MANUAL_ENTRIES_NOT_PERMITTED, null, creditOrDebitAccountHead.getName(),
                creditOrDebitAccountHead.getGlCode()); }
    }
	
	private String generateTransactionId(final Long officeId) {
        final AppUser user = this.context.authenticatedUser();
        final Long time = System.currentTimeMillis();
        final String uniqueVal = String.valueOf(time) + user.getId() + officeId;
        final String transactionId = Long.toHexString(Long.parseLong(uniqueVal));
        return transactionId;
    }
}