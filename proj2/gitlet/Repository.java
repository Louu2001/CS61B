package gitlet;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

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
        currCommit = new Commit("initial commit", null, new ArrayList<>(), new HashMap<>());

        //存储提交对象,并返回
        return currCommit.save();
    }

    private static void initHEAD() {
        writeContents(HEAD_FILE, "refs/heads/master");
    }

    private static void initHeads(String commitId) {
        File masterBranch = join(HEADS_DIR, "master");
        writeContents(masterBranch, commitId); // 让 master 分支指向初始提交}
    }


    /* * add command funtion */
    public static void add(String fileName) {
        File file = join(CWD, fileName);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.out.println(0);
        }

        // 2. 读取文件内容并计算哈希值
        Blob blob = new Blob(file);
        String blobHash = blob.getHash();

        // 3. 保存 Blob
        blob.save();

        // 4. 加载当前暂存区
        Stage stage = Stage.load();

        // 5. 加载当前提交
        Commit currentCommit = readCurrCommmit();

        // 6. 如果文件未修改（即其哈希值与当前 commit 中相同），从暂存区移除
        if (currentCommit != null && blobHash.equals(currentCommit.getBlobHash(fileName))) {
            stage.removeFromAddition(fileName);
        } else {
            // 7. 向当前 commit 添加文件
            currentCommit.addFile(fileName, blobHash);

            // 8. 将文件添加到暂存区
            stage.addToAddition(fileName, blobHash);
        }

        // 9. 保存暂存区
        stage.save();
    }


    /* * commit command funtion */
    public static void commit(String message) {
        // 1. 读取暂存区
        Stage stage = Stage.load();
        currCommit = readCurrCommmit();

        // 2. 如果暂存区为空，则拒绝提交
        if (stage.getAdditionStage().isEmpty() && stage.getRemovalStage().isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }


        // 3. 继承当前提交的 `trackedFiles`（文件 -> blob哈希）
        Map<String, String> newTrackedFiles = new HashMap<>();
        if (currCommit != null) {
            newTrackedFiles.putAll(currCommit.getTrackedFiles()); // 继承之前的提交
        }

        // 创建一个新的 Tree
        Tree tree = new Tree();
        for (String fileName : newTrackedFiles.keySet()) {
            tree.addEntry(fileName, newTrackedFiles.get(fileName)); // 添加文件
        }
        tree.save(); // 存到 .gitlet/objects/trees/
        String treeId = tree.getId(); // 获取这个 Tree 的 ID


        // 4. 处理暂存的文件
        // 记录新的 blob
        newTrackedFiles.putAll(stage.getAdditionStage());

        // 5. 处理删除的文件
        for (String fileName : stage.getRemovalStage()) {
            newTrackedFiles.remove(fileName); // 从 trackedFiles 中移除
        }

        // 6. 创建新的 commit
        List<String> parentIds = (currCommit == null) ? new ArrayList<>() : Arrays.asList(currCommit.getCommitId());
        Commit newCommit = new Commit(message, treeId, parentIds, newTrackedFiles);

        // 7. 保存 commit
        String commitId = newCommit.save();

        // 8. 更新 HEAD 使其指向新 commit
        String currBranch = readCurrBranch(); // 读取当前分支路径
        File headFile = join(GITLET_DIR, currBranch);
        writeContents(headFile, commitId);

        // 9. 更新当前 commit
        currCommit = newCommit;

        // 10. 清空暂存区
        stage.clear();
    }

    private static Commit readCurrCommmit() {
        String currCommmitID = readCurrCommmitID();
        File CURR_COMMIT_FILE = join(COMMITS_DIR, currCommmitID);
        return readObject(CURR_COMMIT_FILE, Commit.class);
    }

    private static String readCurrCommmitID() {
        String currBranch = readCurrBranch();
        File HEADS_FILE = join(GITLET_DIR, currBranch);
        return readContentsAsString(HEADS_FILE);
    }

    private static String readCurrBranch() {
        return readContentsAsString(HEAD_FILE);
    }


    /* * rm command funtion */
    public static void rm(String fileName) {
        //1. 读取当前提交
        Commit currCommit = readCurrCommmit();

        //2. 读取暂存取
        Stage stage = Stage.load();

        boolean removed = false;

        // 3. 如果文件在暂存区（已 add 但未 commit），从暂存区删除
        if (stage.getAdditionStage().containsKey(fileName)) {
            stage.removeFromAddition(fileName);
            removed = true;
        }

        // 4. 如果文件在当前提交中，删除文件并标记为待删除
        if (currCommit != null && currCommit.getTrackedFiles().containsKey(fileName)) {
            //将文件添加到删除列表
            stage.markForRemoval(fileName);
            removed = true;

            // 从工作目录中删除文件
            File fileToRemove = join(CWD, fileName);
            if (fileToRemove.exists()) {
                fileToRemove.delete();
            }
        }

        // 5. 如果文件既不在暂存区也不在当前提交中，报错
        if (!removed) {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }

        // 6. 保存修改后的暂存区
        stage.save();
    }

    /* * log command function */
    public static void log() {
        String currentCommitId = readCurrCommmitID();

        // 遍历提交历史，沿着父提交链接向回查找
        Commit currCommit = readCommitById(currentCommitId);

        while (currCommit != null) {
            printCommitInfo(currCommit);

            // 移动到第一个父提交（如果存在）
            List<String> parentIds = currCommit.getParentCommitIds();
            if (parentIds.isEmpty()) {
                break; // 没有父提交，说明到达了初始提交
            }
            // 获取第一个父提交（忽略合并的第二个父提交）
            currCommit = readCommitById(parentIds.get(0));
        }
    }

    // 根据提交 ID 读取提交对象
    private static Commit readCommitById(String commitId) {
        File commitFile = join(COMMITS_DIR, commitId);
        return readObject(commitFile, Commit.class);
    }

    // 打印提交信息
    private static void printCommitInfo(Commit commit) {
        System.out.println("===");
        System.out.println("commit " + commit.getCommitId());

        Date commitDate = commit.getTimestamp();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z",Locale.ENGLISH);
        String formattedDate = dateFormat.format(commitDate);
        System.out.println("Date: " + formattedDate);

        System.out.println(commit.getMessage());

        // 如果是合并提交，打印父提交信息
        List<String> parentIds = commit.getParentCommitIds();
        if (parentIds.size() > 1) {
            String firstParent = parentIds.get(0).substring(0, 7);
            String secondParent = parentIds.get(1).substring(0, 7);
            System.out.println("Merge: " + firstParent + " " + secondParent);
        }

        System.out.println();
    }

    /* * global-log command function */
    public static void globalLog() {
        File commitsDir = Repository.COMMITS_DIR;

        for (File commitFile : commitsDir.listFiles()) {
            Commit commit = readObject(commitFile, Commit.class);

            System.out.println("===");  // 每个提交之间用 === 分隔
            System.out.println("commit " + commit.getCommitId());
            System.out.println("Date: " + commit.getFormattedTimestamp());
            System.out.println(commit.getMessage());
            System.out.println();
        }
    }

    /* * find command function */
    public static void findCommitByMessage(String message) {
        File commitsDir = Repository.COMMITS_DIR;
        boolean found = false;

        for (File commitFile : commitsDir.listFiles()) {
            Commit commit = readObject(commitFile, Commit.class);
            if (commit.getMessage().equals(message)) {
                System.out.println(commit.getCommitId());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Found no commit with that message.");
        }
    }
}


