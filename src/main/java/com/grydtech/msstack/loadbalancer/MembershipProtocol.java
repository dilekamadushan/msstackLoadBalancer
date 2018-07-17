package com.grydtech.msstack.loadbalancer;

import java.util.List;

public abstract class MembershipProtocol {
    
    private static MembershipProtocol instance;

    public static MembershipProtocol getInstance() {
        return instance;
    }
    
    public abstract List<Member> getMembers(String serviceName);
    
    public abstract void setConnectionString(String connectionString);

    public abstract void start();

    public abstract void stop();

}
