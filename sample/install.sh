#!/bin/bash

azure servicefabric application package copy sample fabric:ImageStore
azure servicefabric application type register sample
azure servicefabric application create fabric:/sample  sampleType 1.0.0

