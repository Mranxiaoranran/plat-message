package message.io.json;

import com.alibaba.fastjson.JSONReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * 从文件中读取JSON格式的数据，并转换成对象
 */
public class JsonReader<T> {
    /**
     * 从文件读取JSON对象
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public T read(File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new RuntimeException("文件不存在，无法读取文件");
        }
        FileReader fileReader = new FileReader(file);
        JSONReader reader = new JSONReader(fileReader);
        reader.startObject();
        return (T) reader.readObject();
    }


}
