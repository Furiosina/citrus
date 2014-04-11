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

package com.consol.citrus.ws.config.xml;

import com.consol.citrus.config.xml.ReplyMessageReceiverParser;
import com.consol.citrus.ws.message.SoapReplyMessageReceiver;

/**
 * Parser for reply message receiver component in Citrus ws namespace.
 * 
 * @author Christoph Deppisch
 * @deprecated since Citrus 1.4
 */
@Deprecated
public class SoapReplyMessageReceiverParser extends ReplyMessageReceiverParser<SoapReplyMessageReceiver> {

    public SoapReplyMessageReceiverParser() {
        super(SoapReplyMessageReceiver.class);
    }
}
