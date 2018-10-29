
## CircleCI Nexus Orb

#### Development

This Orb is not currently published. For development purposes create a repo with a
`.circleci/config.yml` with the following contents
```yaml
orbs:
  dev-orb:
    # PASTE CONTENTS OF orb.yml HERE see also:
    # <https://github.com/CircleCI-Public/config-preview-sdk/blob/master/docs/inline-orbs.md>

version: 2.1
workflows:
  main:
    jobs:
    - dev-orb/nexus-platform
```

Then commit and your changes on the project will be built automatically using your Orb.
When your change is ready update the `orb.yml` file in this project with your changes.
