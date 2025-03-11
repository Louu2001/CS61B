package gitlet;


import java.io.File;
import java.util.ArrayList;

import static gitlet.Utils.*;

// TODO: any imports you need here

/**
 * Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author Lou
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));

    /**
     * .gitlet/
     * ├── objects/
     * │   ├── blobs/
     * │   ├── trees/
     * │   └── commits/
     * ├── refs/
     * │   ├── heads/
     * │   ├── remotes/
     * │   └── tags/
     * ├── staging_area/
     * │   ├── staging_file_list
     * │   ├── staging_file_info
     * │   └── ...
     * ├── config
     * └── HEAD
     */
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    //    The objects directory
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    public static final File BLOBS_DIR = join(OBJECTS_DIR, "blobs");
    public static final File TREES_DIR = join(OBJECTS_DIR, "trees");
    public static final File COMMITS_DIR = join(OBJECTS_DIR, "commits");

    //    The refs directory
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    public static final File HEADS_DIR = join(REFS_DIR, "heads");
    public static final File REMOTES_DIR = join(REFS_DIR, "remotes");

    // The staging area directory.
    public static final File STAGING_AREA = join(GITLET_DIR, "staging_area");

    //The HEAD file
    public static final File HEAD_FILE = join(GITLET_DIR, "HEAD");

    //The config file
    public static final File CONFIG_FILE = join(GITLET_DIR, "config");

    // 存储当前提交对象
    public static Commit currCommit;




    /* TODO: fill in the rest of this class. */

    //实现 init 方法，创建 .gitlet/ 目录和必要的子目录。
    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        // 创建所有必要的目录

        GITLET_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        BLOBS_DIR.mkdir();
        TREES_DIR.mkdir();
        COMMITS_DIR.mkdir();
        REFS_DIR.mkdir();
        HEADS_DIR.mkdir();
        REMOTES_DIR.mkdir();
        STAGING_AREA.mkdir();

        // 初始化初始提交，并获取 commitId
        String commitId = initCommit();

        // 初始化 HEAD 和 heads 目录
        initHEAD();
        initHeads(commitId);


    }

    private static String initCommit() {
        // 创建 initial commit（无父提交，treeId 为空）
        currCommit = new Commit("initial commit", null, new ArrayList<>());

        //计算 commit ID 并存储提交对象
        String commitId = currCommit.save();

        return commitId;
    }

    private static void initHEAD() {
        writeContents(HEAD_FILE, "refs/heads/master");
    }

    private static void initHeads(String commitId) {
        File masterBranch = join(HEADS_DIR, "master");
        System.out.println("Creating master branch at: " + masterBranch.getAbsolutePath());
        writeContents(masterBranch, commitId); // 让 master 分支指向初始提交
        System.out.println("Master branch created with commit ID: " + commitId);
    }

}
