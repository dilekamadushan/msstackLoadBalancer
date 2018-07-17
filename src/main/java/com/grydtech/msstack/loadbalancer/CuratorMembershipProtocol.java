package com.grydtech.msstack.loadbalancer;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CuratorMembershipProtocol extends MembershipProtocol {

    private static final Logger LOGGER = Logger.getLogger(CuratorMembershipProtocol.class.toGenericString());
    private static final String BASE_PATH = "/services/";
    private CuratorFramework curatorFrameworkClient;
    private String connectionString;
   
    @Override
    public List<Member> getMembers(String serviceName){
        try {
            String path = BASE_PATH+serviceName;
            List<String> memberPaths =  curatorFrameworkClient.getChildren().forPath(path);
            ArrayList<Member> members = new ArrayList<>();
            for(String memPath : memberPaths ){
                String[] attributes = memPath.split(":");
                Member mem = new Member()
                        .setName(memPath)
                        .setHost(attributes[0])
                        .setPort(Integer.valueOf(attributes[1]));
                members.add(mem);
            }
            return members;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

   

    @Override
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public void start() {
        curatorFrameworkClient = CuratorFrameworkFactory.newClient(
                connectionString,
                new RetryNTimes(5, 1000)
        );
        curatorFrameworkClient.start();
    }

    @Override
    public void stop() {
        curatorFrameworkClient.close();
    }
}
