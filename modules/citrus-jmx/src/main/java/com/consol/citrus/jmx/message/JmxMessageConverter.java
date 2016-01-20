/*
 * Copyright 2006-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.consol.citrus.jmx.message;

import com.consol.citrus.jmx.endpoint.JmxEndpointConfiguration;
import com.consol.citrus.jmx.model.ManagedBeanInvocation;
import com.consol.citrus.jmx.model.OperationParam;
import com.consol.citrus.message.*;
import org.springframework.util.StringUtils;
import org.springframework.xml.transform.StringResult;

import javax.xml.transform.Source;

/**
 * @author Christoph Deppisch
 * @since 2.5
 */
public class JmxMessageConverter implements MessageConverter<ManagedBeanInvocation, JmxEndpointConfiguration> {

    @Override
    public ManagedBeanInvocation convertOutbound(Message internalMessage, JmxEndpointConfiguration endpointConfiguration) {
        ManagedBeanInvocation serviceInvocation = getServiceInvocation(internalMessage, endpointConfiguration);
        convertOutbound(serviceInvocation, internalMessage, endpointConfiguration);
        return serviceInvocation;
    }

    @Override
    public void convertOutbound(ManagedBeanInvocation mBeanInvocation, Message internalMessage, JmxEndpointConfiguration endpointConfiguration) {
        if (internalMessage.getHeader(JmxMessageHeaders.JMX_MBEAN) != null) {
            mBeanInvocation.setMbean(internalMessage.getHeader(JmxMessageHeaders.JMX_MBEAN).toString());
        }

        if (internalMessage.getHeader(JmxMessageHeaders.JMX_OPERATION) != null) {
            mBeanInvocation.setOperation(internalMessage.getHeader(JmxMessageHeaders.JMX_OPERATION).toString());
        }

        if (internalMessage.getHeader(JmxMessageHeaders.JMX_OPERATION_PARAMS) != null) {
            String[] params = StringUtils.commaDelimitedListToStringArray(internalMessage.getHeader(JmxMessageHeaders.JMX_OPERATION_PARAMS).toString());
            for (String param : params) {
                OperationParam operationParam = new OperationParam();
                operationParam.setType(String.class.getName());
                operationParam.setValue(param);
                mBeanInvocation.getParameter().getParameter().add(operationParam);
            }
        }

        if (internalMessage.getHeader(JmxMessageHeaders.JMX_ATTRIBUTE) != null) {
            ManagedBeanInvocation.Attribute attribute = new ManagedBeanInvocation.Attribute();
            attribute.setName(internalMessage.getHeader(JmxMessageHeaders.JMX_ATTRIBUTE).toString());

            if (internalMessage.getHeader(JmxMessageHeaders.JMX_ATTRIBUTE_VALUE) != null) {
                attribute.setValue(internalMessage.getHeader(JmxMessageHeaders.JMX_ATTRIBUTE_VALUE).toString());
            }

            if (internalMessage.getHeader(JmxMessageHeaders.JMX_ATTRIBUTE_TYPE) != null) {
                attribute.setType(internalMessage.getHeader(JmxMessageHeaders.JMX_ATTRIBUTE_TYPE).toString());
            }

            mBeanInvocation.setAttribute(attribute);
        }


        if (internalMessage.getHeader(JmxMessageHeaders.JMX_OBJECT_DOMAIN) != null) {
            mBeanInvocation.setObjectDomain(internalMessage.getHeader(JmxMessageHeaders.JMX_OBJECT_DOMAIN).toString());
        }

        if (internalMessage.getHeader(JmxMessageHeaders.JMX_OBJECT_NAME) != null) {
            mBeanInvocation.setObjectName(internalMessage.getHeader(JmxMessageHeaders.JMX_OBJECT_NAME).toString());
        }
    }

    @Override
    public Message convertInbound(ManagedBeanInvocation mBeanInvocation, JmxEndpointConfiguration endpointConfiguration) {
        StringResult payload = new StringResult();
        endpointConfiguration.getMarshaller().marshal(mBeanInvocation, payload);

        Message inbound = new DefaultMessage(payload.toString());

        if (mBeanInvocation.getMbean() != null) {
            inbound.setHeader(JmxMessageHeaders.JMX_MBEAN, mBeanInvocation.getMbean());
        }

        if (mBeanInvocation.getObjectDomain() != null) {
            inbound.setHeader(JmxMessageHeaders.JMX_OBJECT_DOMAIN, mBeanInvocation.getObjectDomain());
            inbound.setHeader(JmxMessageHeaders.JMX_OBJECT_NAME, mBeanInvocation.getObjectName());
        }

        return inbound;
    }

    /**
     * Reads Citrus internal RMI message model object from message payload. Either payload is actually a service invocation object or
     * XML payload String is unmarshalled to proper object representation.
     *
     * @param message
     * @param endpointConfiguration
     * @return
     */
    private ManagedBeanInvocation getServiceInvocation(Message message, JmxEndpointConfiguration endpointConfiguration) {
        Object payload = message.getPayload();

        ManagedBeanInvocation serviceInvocation = null;
        if (payload != null) {
            if (payload instanceof ManagedBeanInvocation) {
                serviceInvocation = (ManagedBeanInvocation) payload;
            } else if (payload != null && StringUtils.hasText(message.getPayload(String.class))) {
                serviceInvocation = (ManagedBeanInvocation) endpointConfiguration.getMarshaller()
                        .unmarshal(message.getPayload(Source.class));
            } else {
                serviceInvocation = new ManagedBeanInvocation();
            }
        }

        return serviceInvocation;
    }
}
