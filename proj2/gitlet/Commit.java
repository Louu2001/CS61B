package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.List;
import java.util.Locale;

import static gitlet.Repository.COMMITS_DIR;
import static gitlet.Utils.*;

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Lou
 */

public class Commit implements Serializable {
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


    /* TODO: fill in the rest of this class. */
    public Commit(String logMessage, String treeId, List<String> parentCommitIds) {
        this.logMessage = logMessage;
        this.treeId = (treeId != null) ? treeId : "";  // 避免 treeId 为空指针
        this.parentCommitIds = (parentCommitIds != null) ? parentCommitIds : new ArrayList<>();
        this.timestamp = this.parentCommitIds.isEmpty() ? new Date(0) : new Date();  // 只在空 parent 时设定特殊时间
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
        return format.format(timestamp);
    }
}
