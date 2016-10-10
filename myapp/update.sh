#!/bin/bash

VERSION=$1

azure servicefabric application package copy myapp fabric:ImageStore
azure servicefabric application type register myapp
azure servicefabric application upgrade start --application-name 'fabric:/myapp' --target-application-type-version "$VERSION" --rolling-upgrade-mode Monitored
