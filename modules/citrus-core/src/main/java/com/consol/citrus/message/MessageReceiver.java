/*
 * Copyright 2006-2010 the original author or authors.
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

package com.consol.citrus.message;

import com.consol.citrus.endpoint.Endpoint;
import org.springframework.integration.Message;

import com.consol.citrus.TestActor;

/**
 * Message receiver interface declares message receiving methods.
 * @author Christoph Deppisch
 * @deprecated since Citrus 1.4, in favor of {@link com.consol.citrus.endpoint.Endpoint}
 */
@Deprecated
public interface MessageReceiver extends Endpoint {
    /**
     * Receive message.
     * @return
     */
    Message<?> receive();
    
    /**
     * Receive message with a given timeout.
     * @param timeout
     * @return
     */
    Message<?> receive(long timeout);
    
    /**
     * Receive message with a message selector string.
     * @param selector
     * @return
     */
    Message<?> receiveSelected(String selector);
    
    /**
     * Receive message with a message selector and a receive timeout.
     * @param selector
     * @param timeout
     * @return
     */
    Message<?> receiveSelected(String selector, long timeout);
    
    /**
     * Gets the receiving actor.
     * @return
     */
    TestActor getActor();

    /**
     * Gets the receiver name usually the Spring bean name.
     * @return
     */
    String getName();
}
