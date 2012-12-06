package org.mifosplatform.useradministration.serialization;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.serialization.AbstractFromApiJsonDeserializer;
import org.mifosplatform.infrastructure.core.serialization.CommandSerializer;
import org.mifosplatform.infrastructure.core.serialization.FromApiJsonDeserializer;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.useradministration.command.PermissionsCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.reflect.TypeToken;

/**
 * Implementation of {@link FromApiJsonDeserializer} for
 * {@link PermissionsCommand}'s.
 */
@Component
public final class PermissionsCommandFromApiJsonDeserializer extends AbstractFromApiJsonDeserializer<PermissionsCommand> {

    /**
     * The parameters supported for this command.
     */
    private final Set<String> supportedParameters = new HashSet<String>(Arrays.asList("permissions"));
    private final FromJsonHelper fromApiJsonHelper;

    @Autowired
    public PermissionsCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper,
            final CommandSerializer commandSerializerService) {
        super(commandSerializerService);
        this.fromApiJsonHelper = fromApiJsonHelper;
    }

    @Override
    public PermissionsCommand commandFromApiJson(@SuppressWarnings("unused") final Long resourceId, final String json) {
        
        if (StringUtils.isBlank(json)) { throw new InvalidJsonException(); }
        
        final Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();
        fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
        
        return fromApiJsonHelper.fromJson(json, PermissionsCommand.class);
    }
}