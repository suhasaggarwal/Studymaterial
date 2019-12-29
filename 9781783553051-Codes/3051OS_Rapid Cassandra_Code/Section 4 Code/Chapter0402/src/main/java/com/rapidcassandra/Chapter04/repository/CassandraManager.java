package com.rapidcassandra.Chapter04.repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;

public class CassandraManager {

	private Cluster cluster;
	private Session session;
	private String keyspace;

	public Session getSession() {
		return session;
	}

	public String getKeyspace() {
		return keyspace;
	}

	public void setKeyspace(String keyspace) {
		this.keyspace = keyspace;
	}

	// connect to a Cassandra cluster and a keyspace
	public void connect(final String node, final int port, final String keyspace) {
		this.cluster = Cluster.builder().addContactPoint(node).withPort(port)
				.build();
		session = cluster.connect(keyspace);
	}

	// connect to a Cassandra cluster and a keyspace
	public void connectCluster(final int port, final String keyspace,
			final String dataCenter, final String... nodes) {
		this.cluster = Cluster
				.builder()
				.addContactPoints(nodes)
				.withLoadBalancingPolicy(
						new DCAwareRoundRobinPolicy(dataCenter)).withPort(port)
				.build();
		session = cluster.connect(keyspace);
	}

	// disconnect from Cassandra
	public void close() {
		cluster.close();
	}

}
