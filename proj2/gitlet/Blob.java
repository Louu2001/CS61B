package gitlet;

import java.io.File;
import java.io.Serializable;
import static gitlet.Repository.BLOBS_DIR;
import static gitlet.Utils.*;

/** Represents a blob in Gitlet. */
public class Blob implements Serializable {
    private final String fileName;  // 文件名
    private final String content;   // 文件内容
    private final String hash;      // Blob 的 SHA-1 哈希值

    /** 创建一个 Blob 对象（基于文件内容） */
    public Blob(File file) {
        this.fileName = file.getName();
        this.content = readContentsAsString(file);  // 读取文件内容
        this.hash = sha1(fileName, content);  // 计算 SHA-1 哈希
    }

    /** 获取 Blob 的哈希值 */
    public String getHash() {
        return hash;
    }

    /** 保存 Blob 到 .gitlet/objects/blobs/ 目录 */
    public void save() {
        File blobFile = join(BLOBS_DIR, hash); // 以 SHA-1 作为文件名
        if (!blobFile.exists()) {  // 避免重复写入
            writeObject(blobFile, this);
        }
    }

    /** 读取 Blob 对象（根据哈希值） */
    public static Blob fromFile(String hash) {
        File blobFile = join(BLOBS_DIR, hash);
        if (!blobFile.exists()) {
            throw new IllegalArgumentException("Blob not found: " + hash);
        }
        return readObject(blobFile, Blob.class);
    }

    /** 获取 Blob 的原始文件内容 */
    public String getContent() {
        return content;
    }

    /** 获取 Blob 关联的文件名 */
    public String getFileName() {
        return fileName;
    }
}
