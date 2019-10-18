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

import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

import java.util.Arrays;

import org.apache.ignite.Ignite;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.examples.model.Address;
import org.apache.ignite.examples.model.Organization;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.cluster.*;

/**
 * Demonstrates how to use Ignite thin client for basic put/get cache operations.
 * <p>
 * Prerequisites:
 * <ul>
 * <li>An Ignite server node must be running on local host.</li>
 * </ul>
 * </p>
 */
public class WindowsClientPut100K {
    /** Entry point. */
    public static void main(String[] args) {
        
        
       
    try {
    		
    	Ignition.setClientMode(true);
    	

    	IgniteConfiguration cfg = new IgniteConfiguration();
    	cfg.setIgniteInstanceName("WindowsClient-" + System.currentTimeMillis());
    	
    	TcpDiscoverySpi spi = new TcpDiscoverySpi();

    	TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();

    	// Set initial IP addresses.
    	// Note that you can optionally specify a port or a port range.
    	ipFinder.setAddresses(Arrays.asList("127.0.0.1", "192.168.1.67"));

    	spi.setIpFinder(ipFinder);


    	// Override default discovery SPI.
    	cfg.setDiscoverySpi(spi);
    	
    	Ignite ignite = Ignition.start(cfg); 
    	
       
       	System.out.println(); System.out.println(">>> Thick  client Put 100K records started.");

         final String CACHE_NAME = "Kens100K";
               
         CacheConfiguration <Integer, Address> cacheCfg = new CacheConfiguration <> ();
         cacheCfg.setBackups(1);
         cacheCfg.setName(CACHE_NAME);
         
         IgniteCache<Integer, Address> cache = ignite.getOrCreateCache(cacheCfg);
            
         System.out.format(">>> Created cache [%s].\n", CACHE_NAME);

         Integer key = 1;
         for (; key < 100000; key++) {
            
            	String address = "ABCDEFGHIJKLMNOPQRST" + key;
            	Address val = new Address(address , 11111);

            	cache.put(key, val);

            	if (key % 10000 == 0)
            		System.out.format(">>> Saved " + key + " /100000 in the cache.\n", val);
            
            
            // Address cachedVal = cache.get(key);
         }
             
         System.out.format(">>> Loaded " + key + " records... \n");
       
         
    }
    catch (ClientException e) {
            System.err.println(e.getMessage());
    }
    catch (Exception e) {
            System.err.format("Unexpected failure: %s\n", e);
    }
   }
}
