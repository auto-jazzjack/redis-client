SHELL := /bin/bash
PATH := ./work/redis-git/src:${PATH}
ROOT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))
STUNNEL_BIN := $(shell which stunnel)
BREW_BIN := $(shell which brew)
YUM_BIN := $(shell which yum)
APT_BIN := $(shell which apt-get)
PROFILE ?= ci
REDIS ?= unstable
define REDIS_CLUSTER_CONFIG1
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 myself,master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
define REDIS_CLUSTER_CONFIG2
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 myself,master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
define REDIS_CLUSTER_CONFIG3
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 myself,slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
define REDIS_CLUSTER_CONFIG4
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 myself,slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
define REDIS_CLUSTER_CONFIG5
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 myself,slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
define REDIS_CLUSTER_CONFIG6
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 myself,slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
define REDIS_CLUSTER_CONFIG7
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 myself,master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
define REDIS_CLUSTER_CONFIG8
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 myself,slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
define REDIS_CLUSTER_CONFIG9
c2043458aa5646cee429fdd5e3c18220dddf2ce5 127.0.0.1:6380 master - 1434887920102 1434887920002 0 connected 12000-16383
2c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6382 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
3c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6383 slave c2043458aa5646cee429fdd5e3c18220dddf2ce5 1434887920102 1434887920002 2 connected
27f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6379 master - 0 0 1 connected 6000-11999
2c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6384 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
1c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6381 slave 27f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
37f88788f03a86296b7d860152f4ae24ee59c8c9 127.0.0.1:6385 master - 0 0 1 connected 0-5999
4c07344ffa94ede5ea57a2367f190af6144c1adb 127.0.0.1:6386 slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 2 connected
3c541b6daf98719769e6aacf338a7d81f108a180 127.0.0.1:6387 myself,slave 37f88788f03a86296b7d860152f4ae24ee59c8c9 1434887920102 1434887920002 3 connected
vars currentEpoch 3 lastVoteEpoch 0
endef
##########
# Cluster
##########	
.PRECIOUS: work/cluster-node-%.conf
work/cluster-node-%.conf:
	@mkdir -p $(@D)
	@echo port $* >> $@
	@echo daemonize yes >> $@
	@echo pidfile $(shell pwd)/work/cluster-node-$*.pid >> $@
	@echo logfile $(shell pwd)/work/cluster-node-$*.log >> $@
	@echo save \"\" >> $@
	@echo appendonly no >> $@
	@echo client-output-buffer-limit pubsub 256k 128k 5 >> $@
	@echo unixsocket $(ROOT_DIR)/work/socket-$* >> $@
	@echo cluster-enabled yes >> $@
	@echo cluster-node-timeout 150 >> $@
	@echo cluster-config-file $(shell pwd)/work/cluster-node-config-$*.conf >> $@
work/cluster-node-%.pid: work/cluster-node-%.conf work/redis-git/src/redis-server
	work/redis-git/src/redis-server $< || true
cluster-start: work/cluster-node-6379.pid work/cluster-node-6380.pid work/cluster-node-6381.pid work/cluster-node-6382.pid work/cluster-node-6383.pid work/cluster-node-6384.pid work/cluster-node-6385.pid work/cluster-node-6386.pid work/cluster-node-6387.pid
export REDIS_CLUSTER_CONFIG1
export REDIS_CLUSTER_CONFIG2
export REDIS_CLUSTER_CONFIG3
export REDIS_CLUSTER_CONFIG4
export REDIS_CLUSTER_CONFIG5
export REDIS_CLUSTER_CONFIG6
export REDIS_CLUSTER_CONFIG7
export REDIS_CLUSTER_CONFIG8
export REDIS_CLUSTER_CONFIG9
start: cleanup
	@echo "$$REDIS_CLUSTER_CONFIG1" > work/cluster-node-config-6379.conf
	@echo "$$REDIS_CLUSTER_CONFIG2" > work/cluster-node-config-6380.conf
	@echo "$$REDIS_CLUSTER_CONFIG3" > work/cluster-node-config-6381.conf
	@echo "$$REDIS_CLUSTER_CONFIG4" > work/cluster-node-config-6382.conf
	@echo "$$REDIS_CLUSTER_CONFIG5" > work/cluster-node-config-6383.conf
	@echo "$$REDIS_CLUSTER_CONFIG6" > work/cluster-node-config-6384.conf
	@echo "$$REDIS_CLUSTER_CONFIG7" > work/cluster-node-config-6385.conf
	@echo "$$REDIS_CLUSTER_CONFIG8" > work/cluster-node-config-6386.conf
	@echo "$$REDIS_CLUSTER_CONFIG9" > work/cluster-node-config-6387.conf
	$(MAKE) cluster-start
cleanup: stop
	@mkdir -p work
	rm -f work/cluster-node*.conf 2>/dev/null
	rm -f work/*.rdb work/*.aof work/*.conf work/*.log 2>/dev/null
	rm -f *.aof
	rm -f *.rdb
	rm -f work/socket-*
##########
# SSL Keys
#  - remove Java keystore as becomes stale
##########
work/keystore.jks:
	@mkdir -p $(@D)
	- rm -f work/*.jks
	- rm -Rf work/ca
	src/test/bash/create_certificates.sh
ssl-keys: work/keystore.jks
stop:
	pkill stunnel || true
	pkill redis-server && sleep 1 || true
	pkill redis-sentinel && sleep 1 || true
test-coverage: start
	mvn -B -DskipITs=false clean compile verify jacoco:report -P$(PROFILE)
	$(MAKE) stop
test: start
	mvn -B -DskipITs=false clean compile verify -P$(PROFILE)
	$(MAKE) stop
prepare: stop
ifndef STUNNEL_BIN
ifeq ($(shell uname -s),Linux)
ifdef APT_BIN
	sudo apt-get install -y stunnel
else
ifdef YUM_BIN
	sudo yum install stunnel
else
	@@echo "Cannot install stunnel using yum/apt-get"
	@exit 1
endif
endif
endif
ifeq ($(shell uname -s),Darwin)
ifndef BREW_BIN
	@@echo "Cannot install stunnel because missing brew.sh"
	@exit 1
endif
	brew install stunnel
endif
endif
work/redis-git/src/redis-cli work/redis-git/src/redis-server:
	[ -d "work/redis-git" ] && cd work/redis-git && git reset --hard || \
	git clone https://github.com/antirez/redis.git work/redis-git
	cd work/redis-git && git checkout -q $(REDIS) && git pull origin $(REDIS)
	$(MAKE) -C work/redis-git clean
	$(MAKE) -C work/redis-git -j4
clean:
	rm -Rf work/
	rm -Rf target/
release:
	mvn release:clean
	mvn release:prepare
	mvn release:perform
	ls target/checkout/target/*-bin.zip | xargs gpg -b -a
	ls target/checkout/target/*-bin.tar.gz | xargs gpg -b
