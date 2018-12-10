package message.session;

import message.client.ClientDo;

import java.util.concurrent.ConcurrentSkipListMap;

public abstract class StoreBas {

    //客户端信息
    public static final ConcurrentSkipListMap<String, ClientDo> CLIENTS = new ConcurrentSkipListMap<>();


}
