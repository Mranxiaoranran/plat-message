package message.session;

import message.client.ClientDo;

import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

public abstract class StoreBas {

    //客户信息
    public static final ConcurrentSkipListMap<String, ClientDo> CLIENTS = new ConcurrentSkipListMap<>();

    //客户与连接关系信息
    public static final ConcurrentSkipListMap<UUID, String> CONNECTIONS = new ConcurrentSkipListMap<>();

}
