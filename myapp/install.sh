#!/bin/bash

azure servicefabric application package copy myapp fabric:ImageStore
azure servicefabric application type register myapp
azure servicefabric application create fabric:/myapp  myappType 1.0.0


