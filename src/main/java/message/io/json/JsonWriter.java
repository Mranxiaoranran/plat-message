package message.io.json;

import com.alibaba.fastjson.JSONWriter;

import java.io.*;
import java.util.*;

/**
 * 向文件中写入JSON格式的数据
 */
public class JsonWriter<T> {
    /**
     * 写入文件, 写入文件为单个JSON对象
     */
    public  void write(File file, T obj) throws IOException {
        if (file.exists()) {
            //写入文件存在，则首先删除文件
            file.delete();
        }
        //写入的文件不存在，则首先创建文件，直接写入文件即可
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.startObject();
        jsonWriter.writeValue(obj);
        jsonWriter.endObject();
        //关闭流
        jsonWriter.close();
        writer.close();
    }


}
