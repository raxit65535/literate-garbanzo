## Getting Started

This guide should get you up and running with your own Network Pulse
cluster. Before beginning, you'll need to install and configure
[Pegasus][pegasus] to work with your AWS credentials.

To begin with, you'll need to create the 4 primary nodes in the
cluster.

```bash
peg up 0.ec2-setup/peg/quad/master.yml&
peg up 0.ec2-setup/peg/quad/workers.yml&
```

Wait a few minutes for these to spin up, then install a bunch of great pipeline tools!

```bash
eval `ssh-agent -s`
peg fetch literate-garbanzo
peg install literate-garbanzo ssh
peg install literate-garbanzo aws
peg install literate-garbanzo zookeeper
peg install literate-garbanzo kafka
peg install literate-garbanzo hadoop
peg install literate-garbanzo flink
```

To configure flink:

```bash
MDNS=$(./0.ec2-setup/getMasterPublicDNS.sh)
sed -i "s/jobmanager\.rpc\.address.*/jobmanager.rpc.address: $MDNS/" 0.ec2-setup/config/flink-conf.yaml

peg scp to-rem literate-garbanzo 1 0.ec2-setup/config/flink-conf.yaml /usr/local/flink/conf/
peg scp to-rem literate-garbanzo 2 0.ec2-setup/config/flink-conf.yaml /usr/local/flink/conf/
peg scp to-rem literate-garbanzo 3 0.ec2-setup/config/flink-conf.yaml /usr/local/flink/conf/
peg scp to-rem literate-garbanzo 4 0.ec2-setup/config/flink-conf.yaml /usr/local/flink/conf/

```

To configure kafka:

```bash
ZC=$(0.ec2-setup/kafkaZookeeperConnectStringBuilder.sh)
sed -i "s/zookeeper.connect=.*/zookeeper.connect=$ZC/" 0.ec2-setup/config/server.properties

peg scp to-rem literate-garbanzo 1 0.ec2-setup/config/server.properties /usr/local/kafka/config/
peg sshcmd-node literate-garbanzo 1 "sed -i 's/broker.id=.*/broker.id=0/' /usr/local/kafka/config/server.properties"

peg scp to-rem literate-garbanzo 2 0.ec2-setup/config/server.properties /usr/local/kafka/config/
peg sshcmd-node literate-garbanzo 2 "sed -i 's/broker.id=.*/broker.id=1/' /usr/local/kafka/config/server.properties"

peg scp to-rem literate-garbanzo 3 0.ec2-setup/config/server.properties /usr/local/kafka/config/
peg sshcmd-node literate-garbanzo 3 "sed -i 's/broker.id=.*/broker.id=2/' /usr/local/kafka/config/server.properties"

peg scp to-rem literate-garbanzo 4 0.ec2-setup/config/server.properties /usr/local/kafka/config/
peg sshcmd-node literate-garbanzo 4 "sed -i 's/broker.id=.*/broker.id=3/' /usr/local/kafka/config/server.properties"
```

To run pulse at a steady state, kafka is going to need more space. Spin up 4 extra magnetic volumes, 30GB or so, and attach them to each node, build the filesystem, configure them to mount at /tmp, and reboot.

```bash
peg sshcmd-cluster literate-garbanzo 'sudo mkfs.ext4 /dev/xvdf'
peg sshcmd-cluster literate-garbanzo 'sudo sh -c "echo \"/dev/xvdf /tmp ext4 rw,noexec,nosuid,nodev,nofail,nobootwait,commet=cloudconfig 0 2\" >> /etc/fstab"'
peg sshcmd-cluster literate-garbanzo 'sudo reboot'
peg sshcmd-cluster literate-garbanzo 'sudo chmod -R a=rwx,o+t /tmp'
```


We aren't going anywhere yet, but we can at least start the engine at this point.

```bash
peg service literate-garbanzo zookeeper start
peg service literate-garbanzo kafka start &
sleep 10
peg service literate-garbanzo flink start
```


A few more monitoring and library details before the code can run

```bash
peg sshcmd-cluster literate-garbanzo "sudo apt-get update; sudo apt-get install -y nmon openjdk-8-jdk"
```


## Installing rethink

RethinkDB is installed over a 3-node cluster.

```bash
peg up 0.ec2-setup/peg/rethink/rethink.yml
```

After a few minutes, the cluster should be up. The following is
modified from the [RethingDB
Installation](https://rethinkdb.com/docs/install/ubuntu/) instructions
for Ubuntu.

```bash
peg sshcmd-cluster literate-garbanzo-rethink "source /etc/lsb-release && echo \"deb http://download.rethinkdb.com/apt \$DISTRIB_CODENAME main\" | sudo tee /etc/apt/sources.list.d/rethinkdb.list && \
wget -qO- https://download.rethinkdb.com/apt/pubkey.gpg | sudo apt-key add - && \
sudo apt-get update && sudo apt-get install -y rethinkdb"
```

Now that rethink is installed, configure it:

```bash
peg scp to-rem literate-garbanzo-rethink 1 0.ec2-setup/peg/rethink/rethink.pulse.conf /home/ubuntu/
peg scp to-rem literate-garbanzo-rethink 2 0.ec2-setup/peg/rethink/rethink.pulse.conf /home/ubuntu/
peg scp to-rem literate-garbanzo-rethink 3 0.ec2-setup/peg/rethink/rethink.pulse.conf /home/ubuntu/
peg sshcmd-cluster literate-garbanzo-rethink "sudo mv rethink.pulse.conf /etc/rethinkdb/instances.d/"
```

And run it

```bash
peg sshcmd-cluster literate-garbanzo-rethink "sudo /etc/init.d/rethinkdb start"
```


## Install rethink proxies on Flink servers

RethinkDB Proxies are designed to make the cluster more efficient by
intelligently handling connections and the routing of messages.

```bash
peg sshcmd-cluster literate-garbanzo "source /etc/lsb-release && echo \"deb http://download.rethinkdb.com/apt \$DISTRIB_CODENAME main\" | sudo tee /etc/apt/sources.list.d/rethinkdb.list && \
wget -qO- https://download.rethinkdb.com/apt/pubkey.gpg | sudo apt-key add - && \
sudo apt-get update && sudo apt-get install -y rethinkdb"
```

```bash
peg scp to-rem literate-garbanzo 1 0.ec2-setup/peg/rethink/rethink.pulse.conf /home/ubuntu/
peg scp to-rem literate-garbanzo 2 0.ec2-setup/peg/rethink/rethink.pulse.conf /home/ubuntu/
peg scp to-rem literate-garbanzo 3 0.ec2-setup/peg/rethink/rethink.pulse.conf /home/ubuntu/
peg scp to-rem literate-garbanzo 4 0.ec2-setup/peg/rethink/rethink.pulse.conf /home/ubuntu/
peg sshcmd-cluster literate-garbanzo "sudo mv rethink.pulse.conf /etc/rethinkdb/instances.d/"
```

To start:
```bash
peg sshcmd-cluster literate-garbanzo "sudo rethinkdb proxy --config-file /etc/rethinkdb/instances.d/rethink.pulse.conf --log-file /tmp/rethink.log &"
```


## Spinning up producers

These two nodes provide the pump for the firehose.

```bash
peg up 0.ec2-setup/peg/producers/producers.yml
```

When these are up, and the EIPs are associated:

```bash
peg fetch literate-garbanzo-producers
peg install literate-garbanzo-producers ssh
peg install literate-garbanzo-producers aws
```

The firehose code will be installed on these nodes soon.


## Loading up Network Pulse code

First, you must teach each component about each other

```bash
FLINK_CONNECT=$(./0.ec2-setup/flinkConnectionStringBuilder.sh)
sed -i "s/bootstrap\.servers=.*/bootstrap.servers=$FLINK_CONNECT/" 2.venturi/src/main/resources/kafka.properties
sed -i "s/bootstrap\.servers=.*/bootstrap.servers=$FLINK_CONNECT/" 1.mock-firehose/src/main/resources/kafka.properties
sed -i "s/bootstrap\.servers=.*/bootstrap.servers=$FLINK_CONNECT/" 3.flinkCC/src/main/resources/flink.properties

ZC=$(0.ec2-setup/kafkaZookeeperConnectStringBuilder.sh)
sed -i "s/zookeeper\.connect=.*/zookeeper.connect=$ZC/" 2.venturi/src/main/resources/kafka.properties
sed -i "s/zookeeper\.connect=.*/zookeeper.connect=$ZC/" 3.flinkCC/src/main/resources/flink.properties

```

Then build

```bash
./1.mock-firehose/build.sh
./2.venturi/build.sh
./3.flinkCC/build.sh
```

Presuming each sub-project is built, the following should get the files in place:

```bash
./3.flinkCC/deployJar.sh && ./3.flinkCC/deployProps.sh
./2.venturi/deploy.sh
./1.mock-firehose/deploy.sh
peg sshcmd-node literate-garbanzo 4 "sudo pip install virtualenv; mkdir ~/flasky; cd flasky; virtualenv ."

FLINK_CONNECT=$(./0.ec2-setup/flinkConnectionStringBuilder.sh)
sed -i "s/bootstrap_servers='[^']*'/bootstrap_servers='$FLINK_CONNECT'/" 4.ui-server/uiserver/__init__.py

./4.ui-server/buildAndDeploy.sh

cd 5.kafkaRethink
mvn clean compile package
peg scp to-rem literate-garbanzo 1 target/kafkaRethink-0.0.1-jar-with-dependencies.jar /home/ubuntu
peg scp to-rem literate-garbanzo 1 runRethinkSink.sh /home/ubuntu
```

Create kafka topics with appropriate settings for this application

```bash
peg scp to-rem literate-garbanzo 1 0.ec2-setup/kafka-create-pulse-topics.sh /home/ubuntu/
peg sshcmd-node literate-garbanzo 1 "~/kafka-create-pulse-topics.sh"

```

Start services (this could take a minute):

```bash
./bin/service_start.sh
```

In four shells, connected to each node, run the following in screen sessions (or via nohup)
```
# on node 1
./runFlinkCC.sh&
./runRethinkSink.sh

# on node 2
./runVenturi.sh

# on node 3
#./runFirehose.sh

# on node 4
cd flasky
. bin/activate
./run.py

# to start the firehose
./bin/producers_start.sh

# to stop the firehose
./bin/producers_stop.sh
```


[pegasus]: https://github.com/insightdatascience/pegasus
