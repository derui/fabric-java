#!/bin/bash

sudo sh -c 'echo "deb [arch=amd64] http://apt-mo.trafficmanager.net/repos/servicefabric/ trusty main" > /etc/apt/sources.list.d/servicefabric.list'
sudo apt-key adv --keyserver apt-mo.trafficmanager.net --recv-keys 417A0893

sudo apt update
sudo apt upgrade -y

sudo locale-gen ja_JP.UTF-8
echo debconf servicefabric/accepted-all-eula select true | sudo debconf-set-selections
echo debconf servicefabric/accepted-all-eula seen true | sudo debconf-set-selections
echo debconf servicefabricsdkcommon/accepted-all-eula select true | sudo debconf-set-selections
echo debconf servicefabricsdkcommon/accepted-all-eula seen true | sudo debconf-set-selections
sudo DEBIAN_ERONTEND=noninteractive apt install -yq software-properties-common git-core servicefabricsdkcommon servicefabricsdkjava

sudo /opt/microsoft/sdk/servicefabric/common/sdkcommonsetup.sh

git clone https://github.com/Azure/azure-xplat-cli.git
pushd azure-xplat-cli
npm install

sudo ln -s $(pwd)/bin/azure /usr/bin/azure
popd

azure --completion >> ~/azure.completion.sh
echo 'source ~/azure.completion.sh' >> ~/.bash_profile

sudo /opt/microsoft/sdk/servicefabric/common/clustersetup/devclustersetup.sh
