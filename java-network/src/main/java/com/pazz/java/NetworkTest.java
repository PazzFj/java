package com.pazz.java;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * @author: 彭坚
 * @create: 2019/4/25 16:22
 * @description:
 */
public class NetworkTest {

    public static void main(String[] args) throws Exception {

        // Traversal Network interface to get the first non-loopback and non-private address
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        ArrayList<String> ipv4Result = new ArrayList<String>();
        ArrayList<String> ipv6Result = new ArrayList<String>();
        while (enumeration.hasMoreElements()) {
            final NetworkInterface networkInterface = enumeration.nextElement();
            final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
            while (en.hasMoreElements()) {
                final InetAddress address = en.nextElement();
                if (!address.isLoopbackAddress()) {
                    if (address instanceof Inet6Address) {
                        ipv6Result.add(normalizeHostAddress(address));
                    } else {
                        ipv4Result.add(normalizeHostAddress(address));
                    }
                }
            }
        }
        // prefer ipv4
        if (!ipv4Result.isEmpty()) {
            for (String ip : ipv4Result) {
                if (ip.startsWith("127.0") || ip.startsWith("192.168")) {
                    continue;
                }

                System.out.println(ip);
            }

            System.out.println(ipv4Result.get(ipv4Result.size() - 1));
        } else if (!ipv6Result.isEmpty()) {
            System.out.println(ipv6Result.get(0));
        }
        //If failed to find,fall back to localhost
        final InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(normalizeHostAddress(localHost));
    }

    public static String normalizeHostAddress(final InetAddress localHost) {
        if (localHost instanceof Inet6Address) {
            return "[" + localHost.getHostAddress() + "]";
        } else {
            return localHost.getHostAddress();
        }
    }

}
