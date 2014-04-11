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

package com.consol.citrus.config.xml;

import com.consol.citrus.channel.SyncMessageChannelSender;
import com.consol.citrus.testng.AbstractBeanDefinitionParserTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @author Christoph Deppisch
 * @deprecated since Citrus 1.4
 */
@Deprecated
public class SyncMessageChannelSenderParserTest extends AbstractBeanDefinitionParserTest {

    @Test
    public void testSyncMessageChannelSenderParser() {
        Map<String, SyncMessageChannelSender> messageSenders = beanDefinitionContext.getBeansOfType(SyncMessageChannelSender.class);
        
        Assert.assertEquals(messageSenders.size(), 3);
        
        // 1st message sender
        SyncMessageChannelSender messageSender = messageSenders.get("syncMessageChannelSender1");
        Assert.assertEquals(messageSender.getChannelName(), "channelName");
        Assert.assertNull(messageSender.getChannel());
        Assert.assertEquals(messageSender.getReplyTimeout(), 5000L);
        Assert.assertNotNull(messageSender.getChannelResolver());
        Assert.assertNull(messageSender.getCorrelator());
        Assert.assertNotNull(messageSender.getReplyMessageHandler());
        
        // 2nd message sender
        messageSender = messageSenders.get("syncMessageChannelSender2");
        Assert.assertNull(messageSender.getChannelName());
        Assert.assertNotNull(messageSender.getChannel());
        Assert.assertEquals(messageSender.getReplyTimeout(), 10000L);
        Assert.assertNull(messageSender.getChannelResolver());
        Assert.assertNotNull(messageSender.getCorrelator());
        Assert.assertNotNull(messageSender.getReplyMessageHandler());
        
        // 3rd message sender
        messageSender = messageSenders.get("syncMessageChannelSender3");
        Assert.assertNull(messageSender.getChannelName());
        Assert.assertNull(messageSender.getChannel());
        Assert.assertNull(messageSender.getChannelResolver());
        Assert.assertNull(messageSender.getCorrelator());
        Assert.assertNotNull(messageSender.getReplyMessageHandler());
    }
}
