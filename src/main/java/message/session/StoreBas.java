package message.session;

import message.client.ClientDO;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public abstract class StoreBas {
    /**
     * 业务参数clientId作为唯一key  value为一些基本信息
     */
    public static final ConcurrentSkipListMap<String, ClientDO> CLIENTS = new ConcurrentSkipListMap<>();



}
