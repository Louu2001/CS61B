package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Repository.COMMITS_DIR;
import static gitlet.Utils.*;

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Lou
 */

public class Commit implements Serializable, Dumpable {
    /**
     * TODO: add instance variables here.
     * <p>
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    // 提交日志消息
    private String logMessage;

    // 提交时间戳
    private Date timestamp;

    // 父提交的哈希值列表
    private List<String> parentCommitIds;

    // 树对象的哈希值（存储文件结构）
    private String treeId;

    // 存储文件名到 Blob 哈希值的映射
    private Map<String, String> trackedFiles;

    // **实现 Dumpable 接口的方法**
    @Override
    public void dump() {
        System.out.println("=== Commit ===");
        System.out.println("Commit ID: " + getCommitId());
        System.out.println("Message: " + logMessage);
        System.out.println("Timestamp: " + getFormattedTimestamp());
        System.out.println("Parent Commits: " + parentCommitIds);
        System.out.println("Tracked Files: " + trackedFiles);
        System.out.println("=================");
    }


    /* TODO: fill in the rest of this class. */
    public Commit(String logMessage, String treeId, List<String> parentCommitIds, Map<String, String> trackedFiles) {
        this.logMessage = logMessage;
        this.treeId = (treeId != null) ? treeId : "";  // 避免 treeId 为空指针
        this.parentCommitIds = (parentCommitIds != null) ? parentCommitIds : new ArrayList<>();
        this.timestamp = this.parentCommitIds.isEmpty() ? new Date(0) : new Date();  // 只在空 parent 时设定特殊时间
        this.trackedFiles = (trackedFiles != null) ? trackedFiles : new java.util.HashMap<>();
    }


    // 计算 commit 的唯一 SHA-1 哈希值
    public String getCommitId() {
        return sha1(logMessage, getFormattedTimestamp(), String.join(",", parentCommitIds), treeId);
    }


    // 保存 commit 到 .gitlet/objects/commits 目录
    public String save() {
        String commitId = getCommitId();
        File commitFile = join(COMMITS_DIR, commitId);
        if (!commitFile.exists()) { // 避免重复存储
            writeObject(commitFile, this);
        }
        return commitId;
    }


    // 获取格式化的时间字符串
    public String getFormattedTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getDefault()); // 设置时区为本地时区
        return format.format(timestamp);
    }

    // 获取某个文件的 Blob 哈希值
    public String getBlobHash(String fileName) {
        return trackedFiles.get(fileName);  // 如果文件在该 commit 中，则返回其 Blob 哈希值
    }

    // 向 commit 中添加文件
    public void addFile(String fileName, String blobHash) {
        trackedFiles.put(fileName, blobHash);
    }

    // 获取所有被跟踪的文件及其 Blob 哈希值
    public Map<String, String> getTrackedFiles() {
        return trackedFiles;
    }

    // 获取该 commit 的父提交哈希值列表
    public List<String> getParentCommitIds() {
        return parentCommitIds;
    }

    // 获取提交的消息
    public String getMessage() {
        return logMessage;
    }

    // 获取提交的时间戳
    public Date getTimestamp() {
        return timestamp;
    }
}
