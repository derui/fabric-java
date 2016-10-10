#!/bin/bash

azure servicefabric application delete fabric:/myapp
azure servicefabric application type unregister myappType 1.0.0
