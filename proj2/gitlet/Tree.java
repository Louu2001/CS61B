package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import static gitlet.Utils.*;

public class Tree implements Serializable, Dumpable {
    private final Map<String, String> fileTree; // 文件名 -> blobHash 或 treeHash（如果是目录）

    public Tree() {
        fileTree = new HashMap<>();
    }

    @Override
    public void dump() {
        System.out.println("=== Tree ===");
        System.out.println("Tree ID: " + getId());
        System.out.println("File Tree:");
        for (Map.Entry<String, String> entry : fileTree.entrySet()) {
            System.out.println("File: " + entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("============");
    }

    // 添加文件或子目录
    public void addEntry(String name, String hash) {
        fileTree.put(name, hash);
    }

    // 删除文件或子目录
    public void removeEntry(String name) {
        fileTree.remove(name);
    }

    // 获取文件或目录的 Hash
    public String getHash(String name) {
        return fileTree.get(name);
    }

    // 判断是否包含文件或目录
    public boolean containsEntry(String name) {
        return fileTree.containsKey(name);
    }

    // 获取所有条目
    public Map<String, String> getFileTree() {
        return fileTree;
    }

    // 计算当前 Tree 的 ID（SHA-1）
    public String getId() {
        return sha1(fileTree.toString());
    }

    // 保存 Tree 到 .gitlet/objects/trees/
    public void save() {
        File treeFile = join(Repository.TREES_DIR, getId());
        writeObject(treeFile, this);
    }
}
