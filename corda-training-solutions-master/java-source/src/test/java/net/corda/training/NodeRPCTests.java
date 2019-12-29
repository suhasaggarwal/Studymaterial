package net.corda.training;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.corda.core.concurrent.CordaFuture;
import net.corda.core.contracts.Command;
import net.corda.core.contracts.TransactionVerificationException;
import net.corda.core.flows.FlowLogic;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.transactions.SignedTransaction;
import net.corda.finance.*;
import net.corda.core.node.NodeInfo;
import net.corda.testing.driver.DriverParameters;
import net.corda.testing.driver.NodeHandle;
import net.corda.testing.driver.NodeParameters;
import net.corda.testing.driver.VerifierType;
import net.corda.testing.node.*;
import net.corda.core.identity.Party;
import net.corda.core.crypto.SecureHash;
import net.corda.core.flows.*;
import net.corda.core.transactions.TransactionBuilder;

import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.concurrent.Future;
import java.util.*;

import net.corda.training.flow.IOUSettleFlow;
import org.junit.*;
import org.junit.rules.ExpectedException;
import net.corda.client.rpc.CordaRPCClient;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.utilities.NetworkHostAndPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.corda.testing.driver.Driver.driver;
import static org.junit.Assert.*;
import static org.hamcrest.core.IsInstanceOf.*;
import net.corda.client.rpc.CordaRPCClient;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.utilities.NetworkHostAndPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static net.corda.core.utilities.NetworkHostAndPort.parse;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.corda.core.identity.CordaX500Name;
import net.corda.testing.node.MockNetwork;
import net.corda.testing.node.MockNetworkParameters;
import net.corda.testing.node.StartedMockNode;
import org.junit.After;
import org.junit.Before;

import static java.util.Collections.singletonList;
import static net.corda.testing.node.TestCordapp.findCordapp;
import java.security.PublicKey;

/**
 * Practical exercise instructions Flows part 1.
 * Uncomment the unit tests and use the hints + unit test body to complete the FLows such that the unit tests pass.
 */
public class NodeRPCTests {


    CordaFuture<NodeHandle> partyAFuture;
    @Before
    public void setup() {

        final User user = new User("user1", "test", ImmutableSet.of("ALL"));
        driver(new DriverParameters()
                .withIsDebug(true)
                .withWaitForAllNodesToFinish(true)
                .withCordappsForAllNodes(Arrays.asList(TestCordapp.findCordapp("net.corda.finance.contracts")))
                .withNotarySpecs(Arrays.asList(new NotarySpec(new CordaX500Name("Notary", "London","GB"), true,  Arrays.asList(user), VerifierType.InMemory, null))), dsl -> {
            CordaFuture<NodeHandle> partyAFuture = dsl.startNode(new NodeParameters()
                    .withProvidedName(new CordaX500Name("ParticipantA", "London", "GB"))
                    .withRpcUsers(ImmutableList.of(user)));
            CordaFuture<NodeHandle> partyBFuture = dsl.startNode(new NodeParameters()
                    .withProvidedName(new CordaX500Name("ParticipantB", "New York", "US"))
                    .withRpcUsers(ImmutableList.of(user)));
            //   CordaFuture<NodeHandle> partyCFuture = dsl.startNode(new NodeParameters()
            //          .withProvidedName(new CordaX500Name("ParticipantC", "Paris", "FR"))
            //          .withRpcUsers(ImmutableList.of(user)));




            return null;
        });
    }


    @Test
    public void dummyTest() throws ExecutionException, InterruptedException {
        try {
            System.out.println(partyAFuture.get().getRpc().networkMapSnapshot());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}