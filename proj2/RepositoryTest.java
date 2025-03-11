import gitlet.Repository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class RepositoryTest {

    @Test
    public void testInit() {
        // 初始化仓库
        Repository.init();

        // 检查 .gitlet 目录是否存在
        File gitletDir = new File(".gitlet");
        assertTrue(gitletDir.exists(), ".gitlet directory should exist");

        // 检查 HEAD 文件是否存在
        File headFile = new File(".gitlet/HEAD");
        assertTrue(headFile.exists(), "HEAD file should exist");

        // 检查 master 分支文件是否存在
        File masterBranch = new File(".gitlet/refs/heads/master");
        assertTrue(masterBranch.exists(), "master branch file should exist");

        // 检查初始提交是否存在
//        String commitId = Utils.readContentsAsString(masterBranch);
//        File commitFile = new File(".gitlet/objects/commits/" + commitId);
//        assertTrue(commitFile.exists(), "Initial commit file should exist");
    }

//    @Test
//    public void testInitCommit() {
//        // 创建初始提交
//        String commitId = Repository.initCommit();
//
//        // 检查 commitId 是否不为空
//        assertNotNull(commitId, "Commit ID should not be null");
//
//        // 检查提交文件是否存在
//        File commitFile = new File(".gitlet/objects/commits/" + commitId);
//        assertTrue(commitFile.exists(), "Commit file should exist");
//    }
}