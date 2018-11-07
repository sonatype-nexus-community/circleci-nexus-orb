## CircleCI Org for the Nexus Platform

#### Orb Information
This Orb is published to `sonatype/nexus-platform-orb`. See the [orb page](https://circleci.com/orbs/registry/orb/sonatype/nexus-platform-orb) for the latest version.

See the [demo](https://github.com/sonatype-nexus-community-circleci/circleci-nexus-demo) on the Sonatype Community site for a working example.

#### How to use the Nexus Orb in your config.yml
This `.circleci/config.yml` is copied from CircleCI's examples here: https://circleci.com/docs/2.0/language-java/

```
version: 2 # use CircleCI 2.0
jobs: # a collection of steps
  build: # runs not using Workflows must have a `build` job as entry point
    
    working_directory: ~/circleci-demo-java-spring # directory where steps will run

    docker: # run the steps with Docker
      - image: circleci/openjdk:8-jdk-browsers # ...with this image as the primary container; this is where all `steps` will run

    steps: # a collection of executable commands

      - checkout # check out source code to working directory

      - restore_cache: # restore the saved cache after the first run or if `pom.xml` has changed
          key: circleci-demo-java-spring-{{ checksum "pom.xml" }}
      
      - run: mvn dependency:go-offline # gets the project dependencies
      
      - save_cache: # saves the project dependencies
          paths:
            - ~/.m2
          key: circleci-demo-java-spring-{{ checksum "pom.xml" }}
      
      - run: mvn package # run the actual tests
      
      - store_test_results: # uploads the test metadata from the `target/surefire-reports` directory so that it can show up in the CircleCI dashboard. 
          path: target/surefire-reports
      
      - store_artifacts: # store the uberjar as an artifact
          path: target/demo-java-spring-0.0.1-SNAPSHOT.jar
      # See https://circleci.com/docs/2.0/deployment-integrations/ for deploy examples  
```

To add the CircleCi Nexus Repository Manager Orb add the first 2 lines of the following example to the beginning of 
your `.circleci/config.yml` and update the CircleCI `version` to at least `2.1`.

```
orbs:
  nexus-orb: sonatype/nexus-platform-orb@1.0.1

version: 2.1 # use CircleCI 2.1
jobs: # a collection of steps
  build: # runs not using Workflows must have a `build` job as entry point
    
    working_directory: ~/circleci-demo-java-spring # directory where steps will run

    docker: # run the steps with Docker
      - image: circleci/openjdk:8-jdk-browsers # ...with this image as the primary container; this is where all `steps` will run
   ...
   ...
```

After we have imported the Orb into our build we can now call Nexus RM. To do this add the
following lines at the end of your `.circleci/config.yml`

```
   ...
   ...
      - store_artifacts: # store the uberjar as an artifact
          path: target/demo-java-spring-0.0.1-SNAPSHOT.jar
      # See https://circleci.com/docs/2.0/deployment-integrations/ for deploy examples 
      
      - nexus-orb/install

      - nexus-orb/publish:
          filename: "target/demo-java-spring-0.0.1-SNAPSHOT.jar"
          attributes: "-CgroupId=com.example -CartifactId=myapp -Cversion=1.3 -Aextension=jar"
          username: "admin"
          password: "admin123"
          serverurl: "http://nexus.example.com/"
```

##### nexus-orb/install
This will install groovy and the Nexus client into your build system. You only need to run
this once per build even if you publish multiple artifacts.

##### nexus-orb/publish
This will upload your artifacts to Nexus Repository Manager.
* **filename**: path to the file to publish. This is typically the same value as the `path` used in
the `store_artifacts` step in the code example above.
* **attributes**: List of component and asset attributes. Component attributes are prefixed with `-C` and asset attributes
 with `-A`. This is passed directly to Nexus Repository Manger so many additional 
 attributes to the ones used in the example above can be used as well.

#### CircleCi Context support
`username`, `password`, and possibly `serverurl` should not be hardcoded in your build files.
If you create a CircleCi context with the environment variables below you can remove them from
your `.circleci/config.yml` file.
* **username:** `NEXUS_RM_USERNAME`
* **password:** `NEXUS_RM_PASSWORD`
* **serverurl:** `NEXUS_RM_SERVERURL`
