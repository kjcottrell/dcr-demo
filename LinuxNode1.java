/*
 * Copyright 2019 GridGain Systems, Inc. and Contributors.
 *
 * Licensed under the GridGain Community Edition License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gridgain.com/products/software/community-edition/gridgain-community-edition-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package z.test.homeofficecluster;



import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.*;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.*;
 


import org.apache.ignite.spi.communication.tcp.TcpCommunicationSpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;

import java.util.*;




/**
 * Starts up an empty node with example compute configuration.
 */
public class LinuxNode1 {
    
    public static void main(String[] args) throws IgniteException {
     	
    	IgniteConfiguration cfg = new IgniteConfiguration();
    	cfg.setIgniteInstanceName("LinuxNode-" + System.currentTimeMillis());
    	
    	TcpDiscoverySpi spi = new TcpDiscoverySpi();

    	TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

    	// Set initial IP addresses.
    	// Note that you can optionally specify a port or a port range.
    	// 192.168.1.78 is Ken's windows laptop
    	ipFinder.setAddresses(Arrays.asList("127.0.0.1", "192.168.1.78"));

    	spi.setIpFinder(ipFinder);


    	// Override default discovery SPI.
    	cfg.setDiscoverySpi(spi);
    	
 //   	Ignite ignite = Ignition.start("examples/config/example-ignite.xml");
    	Ignite ignite = Ignition.start(cfg);
        IgniteCluster cluster = ignite.cluster();
        
        System.out.println("NodeID / InstanceName/WorkDirectory/ConsistentID/USer Attributes={" + cluster.forLocal().node().id() +
        						"/ " +  cfg.getWorkDirectory() + "/ " +
        							cfg.getIgniteInstanceName() + "/ " + cfg.getConsistentId() + "/ " + cfg.getUserAttributes() + "}");


        
        System.out.println("cluster info = " + cluster.toString());
        System.out.println("cluster server nodes = " + cluster.forServers().nodes());
        System.out.println("cluster kernel = " + cluster.ignite());
         
       
        
        for (ClusterNode node : cluster.nodes()) {
        	System.out.println("cluster.node id  =  " + node.id()
            	+ "Operating system architecture: " + node.attribute("os.arch")
            	+ "Operating system version: " + node.attribute("os.version"));
        }
         
    }
    
   
}
    
    

