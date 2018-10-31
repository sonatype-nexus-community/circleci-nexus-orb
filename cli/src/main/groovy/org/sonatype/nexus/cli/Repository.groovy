package org.sonatype.nexus.cli

import com.sonatype.nexus.api.common.Authentication
import com.sonatype.nexus.api.common.ServerConfig
import com.sonatype.nexus.api.repository.v3.RepositoryManagerV3ClientBuilder

import groovy.cli.OptionField
import groovy.cli.commons.CliBuilder

/**
 * Copyright (c) 2016-present Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */

@OptionField() Boolean help
@OptionField() String serveruri
@OptionField() String username
@OptionField() String password

def cli = new CliBuilder()
cli.parseFromInstance(this, args)
cli.usage()

def serverConfig = new ServerConfig(URI.create(serveruri), new Authentication(username, password))
def client = new RepositoryManagerV3ClientBuilder().withServerConfig(serverConfig).build()
