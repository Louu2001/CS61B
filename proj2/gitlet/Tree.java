package gitlet;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Tree implements Serializable {
    private static Map<String, String> fileTree; // fileName -> blobHash

    public Tree() {
        fileTree = new HashMap<>();
    }

    // 添加文件到树
    public void addFile(String fileName, String blobHash) {
        fileTree.put(fileName, blobHash);
    }

    // 删除文件
    public void removeFile(String fileName) {
        fileTree.remove(fileName);
    }

    // 获取文件的 blobHash
    public String getBlob(String fileName) {
        return fileTree.get(fileName);
    }

    // 判断是否包含文件
    public boolean containsFile(String fileName) {
        return fileTree.containsKey(fileName);
    }

    // 获取所有文件映射
    public Map<String, String> getFileTree() {
        return fileTree;
    }
}
