/*
 * ============LICENSE_START=======================================================
 * ONAP : APPC
 * ================================================================================
 * Copyright (C) 2019 Ericsson
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ============LICENSE_END=========================================================
 */

package org.onap.appc.lockmanager.impl.sql;

import java.sql.Connection;
import org.junit.Test;
import org.mockito.Mockito;
import org.onap.appc.dao.util.api.JdbcConnectionFactory;
import org.onap.appc.lockmanager.impl.sql.optimistic.MySqlLockManager;

public class JdbcLockManagerTest {

    @Test
    public void testOpenDbConnection() {
        JdbcLockManager manager = new MySqlLockManager();
        JdbcConnectionFactory connectionFactory = Mockito.mock(JdbcConnectionFactory.class);
        manager.setConnectionFactory(connectionFactory);
        manager.openDbConnection();
        Mockito.verify(connectionFactory).openDbConnection();
    }

    @Test
    public void testCloseDbConnection() {
        JdbcLockManager manager = new MySqlLockManager();
        JdbcConnectionFactory connectionFactory = Mockito.mock(JdbcConnectionFactory.class);
        manager.setConnectionFactory(connectionFactory);
        Connection connection = Mockito.mock(Connection.class);
        manager.closeDbConnection(connection);
        Mockito.verify(connectionFactory).closeDbConnection(connection);
    }
}
