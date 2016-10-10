# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "bento/ubuntu-16.04"

  # config.vm.network "forwarded_port", guest: 80, host: 8080
  config.vm.network "forwarded_port", guest: 19080, host: 19080

  config.vm.provider "virtualbox" do |vb|
  #
     vb.memory = "2048"
  end

  config.vm.provision "shell", :path => "provision.sh", :privileged => false
end